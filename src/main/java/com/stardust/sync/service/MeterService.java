package com.stardust.sync.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.stardust.sync.core.Constants;
import com.stardust.sync.core.ModbusSingleton;
import com.stardust.sync.model.Meter;
import com.stardust.sync.model.MeterConfiguration;
import com.stardust.sync.model.MeterExtended;
import com.stardust.sync.repository.MeterConfigRepository;
import com.stardust.sync.repository.MeterRepository;

import de.re.easymodbus.modbusclient.ModbusClient;

@Service
public class MeterService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MeterService.class);

	@Autowired
	private MeterRepository meterRepository;
	
	@Autowired
    ConfigurationService configurationService;
	
	@Autowired
	AlertService alertService;
	
	@Autowired
	MeterConfigurationService meterConfigurationService;
	


	public List<Meter> byIdAndUnit(String id, String unit) {
		return meterRepository.findAllByIdAndUnitOrderByTimeStampDesc(id, unit);
	}

	public List<Meter> byIdAndUnitAndExt(String id, String unit, String ext) {
		return meterRepository.findAllByIdAndUnitAndExtOrderByTimeStampDesc(id, unit, ext);
	}

	public List<Meter> lastSevenbyIdAndUnitAndExtAndMeter(String id, String unit, String ext, int meter) {
		return meterRepository.findTop28ByIdAndUnitAndExtAndMeterOrderByTimeStampDesc(id, unit, ext, meter);
	}

	public List<Meter> byIdAndUnitAndExtAndMeter(String id, String unit, String ext, int meter) {
		return meterRepository.findAllByIdAndUnitAndExtAndMeterOrderByTimeStampDesc(id, unit, ext, meter);
	}
	
	public List<Meter> dailyReadingByExtBetweenStartAndEnd(String ext, Date start, Date end) {
		return meterRepository.findAllByExtAndTimeStampBetweenOrderByTimeStampAsc(ext, start, end);
	}
	
	public List<Meter> dailyReadingByIdAndUnitAndExtBetweenStartAndEnd(String id, String unit, String ext, int meter, Date start, Date end) {
		return meterRepository.findAllByIdAndUnitAndExtAndMeterAndTimeStampBetweenOrderByTimeStampAsc(id, unit, ext, meter, start, end);
	}
	
	

	public Map<String, Double> byExtBetweenStartAndEnd(String ext, Date start, Date end) {
		HashMap<String, Double> map = new HashMap<>();
		double total = 0;
		for (Meter meter : meterRepository.findAllByExtAndTimeStampBetweenOrderByTimeStampDesc(ext, start, end)) {
			total += meter.getValue();
		}
		map.put("total", total);
		return map;

	}
	
	public MeterExtended unitDailyUsageExtended(String id, String unit, String ext, int meter, Date date) {
		MeterExtended out;
		MeterExtended previousSuccessfulMeter= new MeterExtended(id, unit, meter, 0.0, 0.0, ext, date, false);
		Date start = date;
		// Adding Day minus 1 second to the start to get the end time
		Date end = new Date(start.getTime() + 86399000L);
		Meter firstMeterList = meterRepository.findTopByIdAndUnitAndExtAndMeterAndTimeStampBetweenOrderByTimeStampAsc(
				id, unit, ext, meter, start, end);

		// Adding 1 second to the start to get the new start time
		start = new Date(end.getTime() + 1000L);
		// Adding Day minus 1 second to the start to get the end time
		end = new Date(start.getTime() + 86399000L);
		Meter lastMeterList = meterRepository.findTopByIdAndUnitAndExtAndMeterAndTimeStampBetweenOrderByTimeStampAsc(id,
				unit, ext, meter, start, end);

		if (firstMeterList != null && lastMeterList != null) {
			out = new MeterExtended(id, unit, meter, firstMeterList.getValue(), lastMeterList.getValue() - firstMeterList.getValue(), ext, date, false);
			previousSuccessfulMeter = out;
		} else {
			previousSuccessfulMeter.setUseage(0.0);
			out = previousSuccessfulMeter;
		}
		return out;
	}

	public List<MeterExtended> listDailyUsageExtended(String id, String unit, String ext, int meter, Date date, int limit) {
		MeterExtended out;
		Meter previousSuccessfulMeter= null;
		Date start = date;
		
		// TODO Make this more efficient (limit db calls by half)
		// to set the day at 00:00:00HR we need to use a calender (Start date has to be
		// the begining of the day for the algorithem to work)
		// Create a Calendar instance
		// and set it to the date of interest

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		date = cal.getTime();
		List<MeterExtended> meters = new ArrayList<MeterExtended>();
		
		
		boolean lastReadingGood = false;
		for (int i = 0; i < limit; i++) {
			start = date;
			// Adding Day minus 1 second to the start to get the end time
			Meter dateEntered = meterRepository.findTopByIdAndUnitAndExtAndMeterAndTimeStampBetweenOrderByTimeStampAsc(
					id, unit, ext, meter, start, new Date(start.getTime() + 86399000L));
			
			//dateEnteredPlusOne = dateEntered != null ? dateEntered : null ;
			//lastReadingGood = dateEntered != null ? true : false;
			
			Meter dateEnteredPlusOne =  meterRepository.findTopByIdAndUnitAndExtAndMeterAndTimeStampBetweenOrderByTimeStampAsc(id,
					unit, ext, meter, new Date(start.getTime() + 86400000L), new Date(start.getTime() + 86400000L + 86399000L));
			
			
			

				if (dateEntered != null && dateEnteredPlusOne != null) {
					out = new MeterExtended(id, unit, meter, dateEntered.getValue(), dateEnteredPlusOne.getValue() - dateEntered.getValue(), ext, date, false);
					meters.add(out);
				} else if (dateEntered == null && dateEnteredPlusOne != null){
					//first datapoint missing found
					previousSuccessfulMeter = dateEnteredPlusOne;
					//out = new MeterExtended(id, unit, meter,-1,-1, ext, date, false);
				} else if (dateEntered != null && dateEnteredPlusOne == null && previousSuccessfulMeter != null) {
					out = new MeterExtended(id, unit, meter, dateEntered.getValue(), previousSuccessfulMeter.getValue() - dateEntered.getValue(), ext, new Date(previousSuccessfulMeter.getTimeStamp().getTime()-86400000L), false);
					meters.add(out);
				}else {
					//out = new MeterExtended(id, unit, meter,-1,-1 , ext, date, false);
				}
			
			date = new Date(date.getTime() - 86400000L);
		}
		return meters;
	}

	public Meter unitDailyUsage(String id, String unit, String ext, int meter, Date date) {
		Meter out;
		Date start = date;
		// Adding Day minus 1 second to the start to get the end time
		Date end = new Date(start.getTime() + 86399000L);
		Meter firstMeterList = meterRepository.findTopByIdAndUnitAndExtAndMeterAndTimeStampBetweenOrderByTimeStampAsc(
				id, unit, ext, meter, start, end);

		// Adding 1 second to the start to get the new start time
		start = new Date(end.getTime() + 1000L);
		// Adding Day minus 1 second to the start to get the end time
		end = new Date(start.getTime() + 86399000L);
		Meter lastMeterList = meterRepository.findTopByIdAndUnitAndExtAndMeterAndTimeStampBetweenOrderByTimeStampAsc(id,
				unit, ext, meter, start, end);

		if (firstMeterList != null && lastMeterList != null) {
			out = new Meter(id, unit, meter, lastMeterList.getValue() - firstMeterList.getValue(), ext, date, false);
		} else {
			out = new Meter(id, unit, meter, 0.0, ext, date, false);
		}
		return out;
	}

	public List<Meter> listDailyUsage(String id, String unit, String ext, int meter, Date date, int limit) {
		// TODO Make this more efficient (limit db calls by half)
		// to set the day at 00:00:00HR we need to use a calender (Start date has to be
		// the begining of the day for the algorithem to work)
		// Create a Calendar instance
		// and set it to the date of interest

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		date = cal.getTime();
		List<Meter> meters = new ArrayList<Meter>();
		for (int i = 0; i < limit; i++) {
			meters.add(unitDailyUsage(id, unit, ext, meter, date));
			date = new Date(date.getTime() - 86400000L);
		}
		return meters;
	}

	public Meter totalUseageOfAUnit(String id, String unit, String ext, int meter, Date start, Date end) {
		Meter out;
		// to set the day at 00:00:00HR we need to use a calender (Start date has to be
		// the begining of the day for the algorithem to work)
		// Create a Calendar instance
		// and set it to the date of interest

		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		start = cal.getTime();

		// we advance end date by one to get the latest reading on the end date
		cal.setTime(end);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.add(Calendar.DATE, 1);

		end = cal.getTime();

		// Adding Day minus 1 second to the start to get the end time
		Date startEnd = new Date(start.getTime() + 86399000L);
		Meter firstMeterList = meterRepository.findTopByIdAndUnitAndExtAndMeterAndTimeStampBetweenOrderByTimeStampAsc(
				id, unit, ext, meter, start, startEnd);

		// Adding Day minus 1 second to the start to get the end time
		Date endEnd = new Date(end.getTime() + 86399000L);
		Meter lastMeterList = meterRepository.findTopByIdAndUnitAndExtAndMeterAndTimeStampBetweenOrderByTimeStampAsc(id,
				unit, ext, meter, end, endEnd);
		if(lastMeterList == null) {
		// fetching the latest reading available
				lastMeterList = meterRepository.findTopByIdAndUnitAndExtAndMeterOrderByTimeStampDesc(id,
						unit, ext, meter);
		}
		if (firstMeterList != null && lastMeterList != null) {
			out = new Meter(id, unit, meter, lastMeterList.getValue() - firstMeterList.getValue(), ext, start, false);
		} else {
			out = new Meter(id, unit, meter, 0.0, ext, start, false);
		}
		return out;
	}

	public Meter peakUseageOfAUnit(String id, String unit, String ext, int meter, Date start, Date end) {
		double total = 0;
		long diffInMillies = Math.abs(end.getTime() - start.getTime());
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		Date currDate = start;
		Date nextDate = new Date(currDate.getTime() + (1000 * 60 * 60 * 24));
		for (int i = 0; i < diff; i++) {
			Meter firstMeterList = meterRepository
					.findTopByIdAndUnitAndExtAndMeterAndTimeStampBetweenOrderByTimeStampAsc(id, unit, ext, meter,
							currDate, nextDate);
			Meter lastMeterList = meterRepository
					.findTopByIdAndUnitAndExtAndMeterAndTimeStampBetweenOrderByTimeStampDesc(id, unit, ext, meter,
							currDate, nextDate);

			if (firstMeterList != null && lastMeterList != null) {
				total += (lastMeterList.getValue() - firstMeterList.getValue());
			}

			currDate = nextDate;
			nextDate = new Date(currDate.getTime() + (1000 * 60 * 60 * 24));
		}

		return new Meter(id, unit, meter, total, ext, start, false);
	}

	public Meter buildingDailyUsage(String ext, Date date) {
		Meter out = new Meter("Building daily useage", "0", 0, 0L, ext, date, false);
		List<MeterConfiguration> configs = meterConfigurationService.getAllMeterConfigurations();
		for(MeterConfiguration mConfiguration : configs) {
			if (mConfiguration.getExt().equals(ext)) {
				out.setValue(out.getValue()
						+ (unitDailyUsage(mConfiguration.getId(), mConfiguration.getUnit(), ext, mConfiguration.getMeter(), date)).getValue());
			}
		}
		return out;
	}

	public List<Meter> listBuildingDailyUsage(String ext, Date date, int limit) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		date = cal.getTime();

		List<Meter> meters = new ArrayList<Meter>();
		for (int i = 0; i < limit; i++) {
			meters.add(buildingDailyUsage(ext, date));
			date = new Date(date.getTime() - 86400000L);
		}
		return meters;
	}

	@SuppressWarnings("unchecked")
	public List<Meter> topThreeThisWeek(String ext) {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		//We dont have todays data so we take yesterday and go 7 days back
		cal.add(Calendar.DATE, -1);
		Date weekEnd = cal.getTime();
		cal.add(Calendar.DATE, -7);
		Date weekStart = cal.getTime();
		
		System.out.println(weekStart+">>>>>>>>>>>>>>>>>>>>>"+weekEnd);
		List<Meter> meters = new ArrayList<Meter>();
		List<MeterConfiguration> configs = meterConfigurationService.getAllMeterConfigurations();
		for(MeterConfiguration mConfiguration : configs) {
			if (mConfiguration.getExt().equals(ext)) {
				meters.add(totalUseageOfAUnit(mConfiguration.getId(), mConfiguration.getUnit(), ext, mConfiguration.getMeter(), weekStart, weekEnd));
			}
		}
		Collections.sort(meters);
		return meters;
	}

	public List<Meter> totalForMonth(int month) {
		List<Meter> meters = new ArrayList<Meter>();

		// Create a Calendar instance
		// and set it to the date of interest

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		// set month (index from 0)
		cal.set(Calendar.MONTH, month);
		// set DATE to 1, so first date of previous month
		cal.set(Calendar.DATE, 1);

		Date firstDateOfPreviousMonth = cal.getTime();

		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MONTH, month);
		// set actual maximum date of previous month
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		// read it
		Date lastDateOfPreviousMonth = cal.getTime();

		List<MeterConfiguration> configs = meterConfigurationService.getAllMeterConfigurations();
		for(MeterConfiguration mConfiguration : configs) {
			Meter firstMeterList = meterRepository
					.findTopByIdAndUnitAndExtAndMeterAndTimeStampBetweenOrderByTimeStampAsc(mConfiguration.getId(),
							mConfiguration.getUnit(), mConfiguration.getExt(), mConfiguration.getMeter(), firstDateOfPreviousMonth,
							lastDateOfPreviousMonth);
			Meter lastMeterList = meterRepository
					.findTopByIdAndUnitAndExtAndMeterAndTimeStampBetweenOrderByTimeStampDesc(mConfiguration.getId(),
							mConfiguration.getUnit(), mConfiguration.getExt(), mConfiguration.getMeter(), firstDateOfPreviousMonth,
							lastDateOfPreviousMonth);

			if (firstMeterList != null && lastMeterList != null) {
				meters.add(new Meter(mConfiguration.getId(), mConfiguration.getUnit(), mConfiguration.getMeter(),
						lastMeterList.getValue() - firstMeterList.getValue(), mConfiguration.getExt(), firstDateOfPreviousMonth,
						false));
				System.out.println(mConfiguration.getId() + "" + (lastMeterList.getValue() - firstMeterList.getValue()));

			} else {
				meters.add(new Meter(mConfiguration.getId(), mConfiguration.getUnit(), mConfiguration.getMeter(), 0.0, mConfiguration.getExt(),
						firstDateOfPreviousMonth, false));
			}
		}

		return meters;

	}

	public List<Meter> totalPrevMonth() {
		List<Meter> meters = new ArrayList<Meter>();

		// Create a Calendar instance
		// and set it to the date of interest

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		// add -1 month to current month
		cal.add(Calendar.MONTH, -1);
		// set DATE to 1, so first date of previous month
		cal.set(Calendar.DATE, 1);

		Date firstDateOfPreviousMonth = cal.getTime();

		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.add(Calendar.MONTH, -1);
		// set actual maximum date of previous month
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		// read it
		Date lastDateOfPreviousMonth = cal.getTime();

		List<MeterConfiguration> configs = meterConfigurationService.getAllMeterConfigurations();
		for(MeterConfiguration mConfiguration : configs) {
			Meter firstMeterList = meterRepository
					.findTopByIdAndUnitAndExtAndMeterAndTimeStampBetweenOrderByTimeStampAsc(mConfiguration.getId(),
							mConfiguration.getUnit(), mConfiguration.getExt(), mConfiguration.getMeter(), firstDateOfPreviousMonth,
							lastDateOfPreviousMonth);
			Meter lastMeterList = meterRepository
					.findTopByIdAndUnitAndExtAndMeterAndTimeStampBetweenOrderByTimeStampDesc(mConfiguration.getId(),
							mConfiguration.getUnit(), mConfiguration.getExt(), mConfiguration.getMeter(), firstDateOfPreviousMonth,
							lastDateOfPreviousMonth);

			if (firstMeterList != null && lastMeterList != null) {
				meters.add(new Meter(mConfiguration.getId(), mConfiguration.getUnit(), mConfiguration.getMeter(),
						lastMeterList.getValue() - firstMeterList.getValue(), mConfiguration.getExt(), firstDateOfPreviousMonth,
						false));
				System.out.println(mConfiguration.getId() + "" + (lastMeterList.getValue() - firstMeterList.getValue()));

			} else {
				meters.add(new Meter(mConfiguration.getId(), mConfiguration.getUnit(), mConfiguration.getMeter(), 0.0, mConfiguration.getExt(),
						firstDateOfPreviousMonth, false));
			}
		}

		return meters;

	}

	public List<Meter> totalCurrMonth() {
		List<Meter> meters = new ArrayList<Meter>();
		double total = 0;
		// Create a Calendar instance
		// and set it to the date of interest

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		// Set the day of the month to the first day of the month

		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));

		// Extract the Date from the Calendar instance

		Date firstDayOfThisMonth = cal.getTime();

		cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);

		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date lastDayOfThisMonth = cal.getTime();

		List<MeterConfiguration> configs = meterConfigurationService.getAllMeterConfigurations();
		for(MeterConfiguration mConfiguration : configs) {
			Meter firstMeterList = meterRepository
					.findTopByIdAndUnitAndExtAndMeterAndTimeStampBetweenOrderByTimeStampAsc(mConfiguration.getId(),
							mConfiguration.getUnit(), mConfiguration.getExt(), mConfiguration.getMeter(), firstDayOfThisMonth, lastDayOfThisMonth);
			Meter lastMeterList = meterRepository
					.findTopByIdAndUnitAndExtAndMeterAndTimeStampBetweenOrderByTimeStampDesc(mConfiguration.getId(),
							mConfiguration.getUnit(), mConfiguration.getExt(), mConfiguration.getMeter(), firstDayOfThisMonth, lastDayOfThisMonth);

			if (firstMeterList != null && lastMeterList != null) {
				meters.add(new Meter(mConfiguration.getId(), mConfiguration.getUnit(), mConfiguration.getMeter(),
						lastMeterList.getValue() - firstMeterList.getValue(), mConfiguration.getExt(), firstDayOfThisMonth,
						false));

			} else {
				meters.add(new Meter(mConfiguration.getId(), mConfiguration.getUnit(), mConfiguration.getMeter(), 0.0, mConfiguration.getExt(),
						firstDayOfThisMonth, false));
			}
		}
		return meters;

	}

	public void capturePeakReadings() {
		
		List<MeterConfiguration> configs = meterConfigurationService.getAllMeterConfigurations();
		List<Meter> peakReadings = new ArrayList<Meter>();
		for(MeterConfiguration mConfiguration : configs) {
			peakReadings.add(getPeakReading(mConfiguration));
		}
		meterRepository.saveAll(peakReadings);
	}

	public void captureOffPeakReadings() {

		List<MeterConfiguration> configs = meterConfigurationService.getAllMeterConfigurations();
		List<Meter> offPeakReadings = new ArrayList<Meter>();
		for(MeterConfiguration mConfiguration : configs) {
			offPeakReadings.add(getOffPeakReading(mConfiguration));
		}
		meterRepository.saveAll(offPeakReadings);

	}

	private Meter getOffPeakReading(MeterConfiguration mConfig) {

		String PeakStart = configurationService.getConfiguration(Constants.CONFIG_KEY_PEAK_START_CONFIG).getConfigValue();
    	Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    	try {
			cal.setTime(sdf.parse(PeakStart));
		} catch (ParseException e) {
			LOGGER.error("Time parse error:"+e);
		}// all done
    	
    	//This routine ensures the value read is greater than the previous value to avoid reading errors.
    	Meter prevReading = meterRepository.findTopByIdAndUnitAndExtAndMeterOrderByTimeStampDesc(mConfig.getId(), mConfig.getUnit(), mConfig.getExt(), mConfig.getMeter());
    	Double curReading = 0.0;
    	for(int i=0; i<5; i++) {
    		curReading = getValue(mConfig.getAddress());
    		if(prevReading.getValue()<curReading) {
    			return new Meter(mConfig.getId(), mConfig.getUnit(), 1, curReading, mConfig.getExt(), cal.getTime(), true);
    		}
    		
    	}
    	
    	alertService.error(mConfig.getId()+" floor, unit " + mConfig.getUnit().toString() +" "+ mConfig.getExt()+" meter read error. Last known value recorded in the DB");
    	return new Meter(mConfig.getId(), mConfig.getUnit(), 1, prevReading.getValue(), mConfig.getExt(), cal.getTime(), false);
		
	}

	private Meter getPeakReading(MeterConfiguration mConfig) {

			String PeakStart = configurationService.getConfiguration(Constants.CONFIG_KEY_PEAK_START_CONFIG).getConfigValue();
        	Calendar cal = Calendar.getInstance();
        	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        	try {
				cal.setTime(sdf.parse(PeakStart));
			} catch (ParseException e) {
				LOGGER.error("Time parse error:"+e);
			}// all done
        	
        	//This routine ensures the value read is greater than the previous value to avoid reading errors.
        	Meter prevReading = meterRepository.findTopByIdAndUnitAndExtAndMeterOrderByTimeStampDesc(mConfig.getId(), mConfig.getUnit(), mConfig.getExt(), mConfig.getMeter());
        	Double curReading = 0.0;
        	for(int i=0; i<5; i++) {
        		curReading = getValue(mConfig.getAddress());
        		if(prevReading.getValue()<curReading) {
        			return new Meter(mConfig.getId(), mConfig.getUnit(), 1, curReading, mConfig.getExt(), cal.getTime(), true);
        		}
        		
        	}
        	
        	alertService.error(mConfig.getId()+" floor, unit " + mConfig.getUnit().toString() +" "+ mConfig.getExt()+" meter read error. Last known value recorded in the DB");
        	return new Meter(mConfig.getId(), mConfig.getUnit(), 1, prevReading.getValue(), mConfig.getExt(), cal.getTime(), true);
				
		
	}

	public double getValue(int address) {

		double value = 0.0;

		try {
			ModbusClient modbusClient = ModbusSingleton.getInstance();
			value = (double) ModbusClient.ConvertRegistersToFloat(modbusClient.ReadHoldingRegisters(address - 1, 2));
			return value;

		} catch (Exception e) {
			System.out.println("Modbus read error " + e);
			return 0;
		}


	}

}
