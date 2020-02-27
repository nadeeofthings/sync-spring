package com.stardust.sync.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.stardust.sync.core.Constants;
import com.stardust.sync.core.ModbusSingleton;
import com.stardust.sync.model.Meter;
import com.stardust.sync.repository.MeterRepository;

import de.re.easymodbus.modbusclient.ModbusClient;

@Service
public class MeterService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MeterService.class);

	private final int G1 = 1600;
	private final int T1 = 1604;
	private final int T2 = 1606;
	private final int T3 = 1608;
	private final int T4 = 1610;
	private final int F1 = 1612;
	private final int F2 = 1614;
	private final int F3 = 1616;
	private final int F4 = 1618;
	private final int FI1 = 1620;
	private final int FI2 = 1622;
	private final int FI3 = 1624;
	private final int FI4 = 1626;
	private final int S1 = 1628;
	private final int S2 = 1630;
	private final int S3 = 1632;
	private final int S4 = 1634;

	@Autowired
	private MeterRepository meterRepository;

	private Map<String, Meter> MeterList;

	public List<Meter> byIdAndUnit(String id, String unit){
		return meterRepository.findAllByIdAndUnitOrderByTimeStampDesc(id, unit);
	}
	

	public List<Meter> byIdAndUnitAndExt(String id, String unit, String ext) {
		return meterRepository.findAllByIdAndUnitAndExtOrderByTimeStampDesc(id, unit, ext);
	}
	
	public List<Meter> byIdAndUnitAndExtAndMeter(String id, String unit, String ext, int meter) {
		return meterRepository.findAllByIdAndUnitAndExtAndMeterOrderByTimeStampDesc(id, unit, ext, meter);
	}
	
	public Map<String, Double> byExtBetweenStartAndEnd(String ext, Date start, Date end) {
		HashMap<String, Double> map = new HashMap<>();
		double total = 0;
		for(Meter meter : meterRepository.findAllByExtAndTimeStampBetweenOrderByTimeStampDesc(ext, start, end)) {
			total += meter.getValue();
		}
		map.put("total", total);
		return map;
		
		
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
		
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		// set actual maximum date of previous month
		cal.set(Calendar.DATE,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		//read it
		Date lastDateOfPreviousMonth = cal.getTime();
		
		for(Meter meter : Constants.meters) {
		Meter firstMeterList = meterRepository.findTopByIdAndUnitAndExtAndMeterAndTimeStampBetweenOrderByTimeStampAsc(
				meter.getId(),meter.getUnit(), meter.getExt(), meter.getMeter(), firstDateOfPreviousMonth, lastDateOfPreviousMonth);
		Meter lastMeterList = meterRepository.findTopByIdAndUnitAndExtAndMeterAndTimeStampBetweenOrderByTimeStampDesc(
				meter.getId(),meter.getUnit(), meter.getExt(), meter.getMeter(), firstDateOfPreviousMonth, lastDateOfPreviousMonth);
		
			if(firstMeterList!=null && lastMeterList!=null) {
				meters.add(new Meter(meter.getId(), meter.getUnit(), meter.getMeter(), lastMeterList.getValue()-firstMeterList.getValue(), meter.getExt(), firstDateOfPreviousMonth, false));		
			System.out.println(meter.getId()+""+(lastMeterList.getValue()-firstMeterList.getValue()));

			}else {
				meters.add(new Meter(meter.getId(), meter.getUnit(), meter.getMeter(), 0.0, meter.getExt(), firstDateOfPreviousMonth, false));		
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

		cal.set(Calendar.DAY_OF_MONTH, 
		   cal.getActualMinimum(Calendar.DAY_OF_MONTH));

		// Extract the Date from the Calendar instance 

		Date firstDayOfThisMonth = cal.getTime(); 
		
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		
		cal.set(Calendar.DAY_OF_MONTH, 
				   cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date lastDayOfThisMonth = cal.getTime(); 
		
		
		for(Meter meter : Constants.meters) {
		Meter firstMeterList = meterRepository.findTopByIdAndUnitAndExtAndMeterAndTimeStampBetweenOrderByTimeStampAsc(
				meter.getId(),meter.getUnit(), meter.getExt(), meter.getMeter(), firstDayOfThisMonth, lastDayOfThisMonth);
		Meter lastMeterList = meterRepository.findTopByIdAndUnitAndExtAndMeterAndTimeStampBetweenOrderByTimeStampDesc(
				meter.getId(),meter.getUnit(), meter.getExt(), meter.getMeter(), firstDayOfThisMonth, lastDayOfThisMonth);
		
			if(firstMeterList!=null && lastMeterList!=null) {
				meters.add(new Meter(meter.getId(), meter.getUnit(), meter.getMeter(), lastMeterList.getValue()-firstMeterList.getValue(), meter.getExt(), firstDayOfThisMonth, false));		

			}else {
				meters.add(new Meter(meter.getId(), meter.getUnit(), meter.getMeter(), 0.0, meter.getExt(), firstDayOfThisMonth, false));		
			}
		}
		return meters;
		
		
	}
	
	public void capturePeakReadings() {

		
		 meterRepository.saveAll(getPeakReadings("Ground","1"));
		 meterRepository.saveAll(getPeakReadings("3rd","1"));
		 meterRepository.saveAll(getPeakReadings("3rd","2"));
		 meterRepository.saveAll(getPeakReadings("3rd","3"));
		 meterRepository.saveAll(getPeakReadings("3rd","4"));
		 meterRepository.saveAll(getPeakReadings("4th","1"));
		 meterRepository.saveAll(getPeakReadings("4th","2"));
		 meterRepository.saveAll(getPeakReadings("4th","3"));
		 meterRepository.saveAll(getPeakReadings("4th","4"));
		 meterRepository.saveAll(getPeakReadings("5th","1"));
		 meterRepository.saveAll(getPeakReadings("5th","2"));
		 meterRepository.saveAll(getPeakReadings("5th","3"));
		 meterRepository.saveAll(getPeakReadings("5th","4"));
		 meterRepository.saveAll(getPeakReadings("6th","1"));
		 meterRepository.saveAll(getPeakReadings("6th","2"));
		 meterRepository.saveAll(getPeakReadings("6th","3"));
		 meterRepository.saveAll(getPeakReadings("6th","4"));
		 
		 

	}
	
	public void captureOffPeakReadings() {

		
		 meterRepository.saveAll(getOffPeakReadings("Ground","1"));
		 meterRepository.saveAll(getOffPeakReadings("3rd","1"));
		 meterRepository.saveAll(getOffPeakReadings("3rd","2"));
		 meterRepository.saveAll(getOffPeakReadings("3rd","3"));
		 meterRepository.saveAll(getOffPeakReadings("3rd","4"));
		 meterRepository.saveAll(getOffPeakReadings("4th","1"));
		 meterRepository.saveAll(getOffPeakReadings("4th","2"));
		 meterRepository.saveAll(getOffPeakReadings("4th","3"));
		 meterRepository.saveAll(getOffPeakReadings("4th","4"));
		 meterRepository.saveAll(getOffPeakReadings("5th","1"));
		 meterRepository.saveAll(getOffPeakReadings("5th","2"));
		 meterRepository.saveAll(getOffPeakReadings("5th","3"));
		 meterRepository.saveAll(getOffPeakReadings("5th","4"));
		 meterRepository.saveAll(getOffPeakReadings("6th","1"));
		 meterRepository.saveAll(getOffPeakReadings("6th","2"));
		 meterRepository.saveAll(getOffPeakReadings("6th","3"));
		 meterRepository.saveAll(getOffPeakReadings("6th","4"));
		 
		 

	}

	private List<Meter> getOffPeakReadings(String id, String unit) {

		List<Meter> meters = new ArrayList<Meter>();

		int address = 0;
		if (id != null && unit != null) {
			switch (id) {
			case "Ground":
				address = G1;
				break;
			case "3rd":
				switch (unit) {
				case "1":
					address = T1;
					break;
				case "2":
					address = T2;
					break;
				case "3":
					address = T3;
					break;
				case "4":
					address = T4;
					break;
				default:
					return null;
				}
				break;
			case "4th":
				switch (unit) {
				case "1":
					address = F1;
					break;
				case "2":
					address = F2;
					break;
				case "3":
					address = F3;
					break;
				case "4":
					address = F4;
					break;
				default:
					return null;
				}
				break;
			case "5th":
				switch (unit) {
				case "1":
					address = FI1;
					break;
				case "2":
					address = FI2;
					break;
				case "3":
					address = FI3;
					break;
				case "4":
					address = FI4;
					break;
				default:
					return null;
				}
				break;
			case "6th":
				switch (unit) {
				case "1":
					address = S1;
					break;
				case "2":
					address = S2;
					break;
				case "3":
					address = S3;
					break;
				case "4":
					address = S4;
					break;
				default:
					return null;
				}
				break;
			default:
				return null;
			}

			if (id.contentEquals("Ground")) {
				meters.add(new Meter(id, unit, 1, getValue(address), "kWh", new Date(),false));
				meters.add(new Meter(id, unit, 2, getValue(address + 1), "BTU", new Date(),false));
				meters.add(new Meter(id, unit, 3, getValue(address + 2), "BTU", new Date(),false));
				meters.add(new Meter(id, unit, 4, getValue(address + 3), "BTU", new Date(),false));

			} else {
				meters.add(new Meter(id, unit, 1, getValue(address), "kWh", new Date(),false));
				meters.add(new Meter(id, unit, 2, getValue(address + 1), "BTU", new Date(),false));
			}

			// meters.add(getValue(id, unit));
			return meters;
		} else {
			return null;
		}
	}

	private List<Meter> getPeakReadings(String id, String unit) {

		List<Meter> meters = new ArrayList<Meter>();

		int address = 0;
		if (id != null && unit != null) {
			switch (id) {
			case "Ground":
				address = G1;
				break;
			case "3rd":
				switch (unit) {
				case "1":
					address = T1;
					break;
				case "2":
					address = T2;
					break;
				case "3":
					address = T3;
					break;
				case "4":
					address = T4;
					break;
				default:
					return null;
				}
				break;
			case "4th":
				switch (unit) {
				case "1":
					address = F1;
					break;
				case "2":
					address = F2;
					break;
				case "3":
					address = F3;
					break;
				case "4":
					address = F4;
					break;
				default:
					return null;
				}
				break;
			case "5th":
				switch (unit) {
				case "1":
					address = FI1;
					break;
				case "2":
					address = FI2;
					break;
				case "3":
					address = FI3;
					break;
				case "4":
					address = FI4;
					break;
				default:
					return null;
				}
				break;
			case "6th":
				switch (unit) {
				case "1":
					address = S1;
					break;
				case "2":
					address = S2;
					break;
				case "3":
					address = S3;
					break;
				case "4":
					address = S4;
					break;
				default:
					return null;
				}
				break;
			default:
				return null;
			}

			if (id.contentEquals("Ground")) {
				meters.add(new Meter(id, unit, 1, getValue(address), "kWh", new Date(),true));
				meters.add(new Meter(id, unit, 2, getValue(address + 1), "BTU", new Date(),true));
				meters.add(new Meter(id, unit, 3, getValue(address + 2), "BTU", new Date(),true));
				meters.add(new Meter(id, unit, 4, getValue(address + 3), "BTU", new Date(),true));

			} else {
				meters.add(new Meter(id, unit, 1, getValue(address), "kWh", new Date(),true));
				meters.add(new Meter(id, unit, 2, getValue(address + 1), "BTU", new Date(),true));
			}

			// meters.add(getValue(id, unit));
			return meters;
		} else {
			return null;
		}
	}
	private double getValue(int address) {

		double value = 0.0;

		try {
			ModbusClient modbusClient = ModbusSingleton.getInstance();
			value = (double) ModbusClient.ConvertRegistersToFloat(modbusClient.ReadHoldingRegisters(address - 1, 2));

		} catch (Exception e) {
			System.out.println("Modbus read error " + e);
		}

		return value;

	}
}
