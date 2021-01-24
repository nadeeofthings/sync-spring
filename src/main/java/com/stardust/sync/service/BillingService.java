package com.stardust.sync.service;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stardust.sync.core.Constants;
import com.stardust.sync.model.Billing;
import com.stardust.sync.model.BillingProperties;
import com.stardust.sync.model.Meter;
import com.stardust.sync.model.MeterConfiguration;
import com.stardust.sync.repository.BillingRepository;
import com.stardust.sync.util.GeneratePdfBill;

@Service
public class BillingService {
	private static final Logger         LOGGER  = LoggerFactory.getLogger(ConfigurationService.class);
    
    @Autowired
    private BillingRepository            billingRepository;
    
    @Autowired
    private MeterService            meterService;
    
    @Autowired
	private GeneratePdfBill generatePdfBill;
    
	@Autowired
	BillingService billingService;
	
	@Autowired
	MeterConfigurationService meterConfigurationService;
	
	@Autowired
	BillingPropertiesService billingPropertiesService;
	
	@Autowired
    private AlertService alertService;
    
    public void generateBill(String efl, String efr, String ero, String epp, String edi, String eof, String eto, String erp, String epe, String eadj, String ext) {
    	
    	try {
    		
	    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date fromDate = format.parse (efr);
			Date toDate = format.parse (eto);  
	        
	        Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			
			cal.add(Calendar.MONTH, 2);
			
	    	Billing billPrevious;
	    	Meter totalUseageOfAUnit, peakUseageOfAUnit;
	    	if(efl.equalsIgnoreCase("Ground")) {
	    	    billPrevious = getLatestBill(efl,  "1", Integer.parseInt(eof));
	    	    totalUseageOfAUnit = meterService.totalUseageOfAUnit(efl, "1", ext, Integer.parseInt(eof), fromDate, toDate);
	    	    peakUseageOfAUnit = meterService.peakUseageOfAUnit(efl, "1", ext, Integer.parseInt(eof), fromDate, toDate);
	    	}else {
				billPrevious = getLatestBill(efl, eof, ((ext.equalsIgnoreCase("kWh")) ? 1 : 2));
				totalUseageOfAUnit = meterService.totalUseageOfAUnit(efl, eof, ext, ((ext.equalsIgnoreCase("kWh")) ? 1 : 2), fromDate, toDate);
	    	    peakUseageOfAUnit = meterService.peakUseageOfAUnit(efl, eof, ext, ((ext.equalsIgnoreCase("kWh")) ? 1 : 2), fromDate, toDate);
	    	}
	    	
			HashMap<String, String> billProps = billingPropertiesService.getMandatoryBillingProperties(ext);
	        
	        BigDecimal peakUseage = BigDecimal.valueOf(peakUseageOfAUnit.getValue());
	        BigDecimal offPeakUseage = BigDecimal.valueOf(totalUseageOfAUnit.getValue()-peakUseageOfAUnit.getValue());
	        BigDecimal peakRate = new BigDecimal(erp);
	        BigDecimal offPeakRate = new BigDecimal(ero);		
            BigDecimal peakBill = peakRate.multiply(peakUseage);
            BigDecimal offPeakBill = offPeakRate.multiply(offPeakUseage);
            
            if(ext.equalsIgnoreCase("BTU")) {
            	peakBill = peakBill.divide(new BigDecimal("10000"));
            	offPeakBill = offPeakBill.divide(new BigDecimal("10000"));
	        }
            
            BigDecimal totalUseage = peakBill.add(offPeakBill);
            BigDecimal totalBill = totalUseage.add(new BigDecimal(billProps.get(Constants.CONFIG_KEY_SERVICE_CHARGE)));
            
            BigDecimal discount = (totalBill.multiply(new BigDecimal(edi))).divide(new BigDecimal("100.00"));
            BigDecimal penalties = (totalBill.multiply(new BigDecimal(epe))).divide(new BigDecimal("100.00"));
            
            BigDecimal adjustmentsDiscounts = discount.add(new BigDecimal(eadj));
            BigDecimal currentBill = totalBill.subtract(adjustmentsDiscounts).add(penalties);
            BigDecimal nbTax = (currentBill.multiply(new BigDecimal(billProps.get(Constants.CONFIG_KEY_NBTAX)))).divide(new BigDecimal("100.00"));
            BigDecimal vaTax = (currentBill.multiply(new BigDecimal(billProps.get(Constants.CONFIG_KEY_VATAX)))).divide(new BigDecimal("100.00"));	
            
            BigDecimal totalPayableForThisBillingPeriod = currentBill.add(nbTax).add(vaTax);
            BigDecimal totalPayable = ((billPrevious != null) ? billPrevious.getBalance() : new BigDecimal("0.00"))
					            		.subtract(new BigDecimal(epp))
					            		.add(totalPayableForThisBillingPeriod);
            
            

            Billing bill;
	    	if(efl.equalsIgnoreCase("Ground")) {
	    		bill = new Billing(efl,  "1", Integer.parseInt(eof), ext, fromDate, toDate, new Date(), peakUseage, 
		        		offPeakUseage, peakRate, offPeakRate, new BigDecimal(epe), new BigDecimal(edi), new BigDecimal(eadj), 
		        		totalPayable, new BigDecimal(epp), Constants.FLAG_ENABLED);
	    	}else {
	    		bill = new Billing(efl, eof, ((ext.equalsIgnoreCase("kWh")) ? 1 : 2), ext, fromDate, toDate, new Date(), peakUseage, 
		        		offPeakUseage, peakRate, offPeakRate, new BigDecimal(epe), new BigDecimal(edi), new BigDecimal(eadj), 
		        		totalPayable, new BigDecimal(epp), Constants.FLAG_ENABLED);
	    	}
	         
	        
	        saveBill(bill);
	        
	        alertService.success(efl+" floor "+(efl.equalsIgnoreCase("Ground")?"Meter ":"Office ")+eof+" bill generated successfully");
	        
    	} catch (ParseException e) {
    		alertService.error(efl+" floor "+(efl.equalsIgnoreCase("Ground")?"Meter ":"Office ")+eof+" bill generation failed");
    		LOGGER.error(efl+"Error generating bill" + e);
		}
		   
    }
    
    public ByteArrayInputStream generatePDFBill(long billId) {
    	
    	Billing bill = getBillByBillId(billId);
    	if(bill!=null) {
    	MeterConfiguration config = meterConfigurationService.getMeterConfigurationByIdAndUnitAndMeterAndExt(bill.getId(), bill.getUnit(), bill.getMeter(), bill.getExt());
    	
		HashMap<String, String> billProps = billingPropertiesService.getMandatoryBillingProperties(bill.getExt());
		
    	return generatePdfBill.generate(bill.getExt(), config, billProps, bill);
    	}
    	return null;
    }
    
    public void deleteBill(long billId) {
    	Billing bill = billingRepository.findTopByBillIdAndEnable(billId, Constants.FLAG_ENABLED);
    	bill.setEnable(Constants.FLAG_DELETED);
    	saveBill(bill);
    	alertService.warn(bill.getId()+" floor "+(bill.getId().equalsIgnoreCase("Ground")?"Meter ":"Office ")+bill.getUnit()+" bill deleted successfully");
    }
    
    public List<Map<String, String>> getDisabledDates(String id, String unit, String ext){
    	List<Billing> bills;
    	List<Map<String, String>> list= new ArrayList<Map<String, String>>();
    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    	if(id.equalsIgnoreCase("Ground")) {
    		bills = getAllBillsByIdAndUnitAndMeterAndExt(id,  "1", Integer.parseInt(unit), ext);
    	}else {
    		bills = getAllBillsByIdAndUnitAndMeterAndExt(id, unit, ((ext.equalsIgnoreCase("kWh")) ? 1 : 2), ext);
    	}
    	for(Billing bill :bills) {
    		Map<String, String> disabledDates = new HashMap<String, String>();
    		disabledDates.put("from", format.format(bill.getFromDate()));
    		disabledDates.put("to", format.format(bill.getToDate()));
    		list.add(disabledDates);
    	}
    	return list;
    }
    
    public void saveBill(Billing bill) {
    	billingRepository.save(bill);
    };
    
    public List<Billing> getAllBillsByIdAndUnitAndMeterAndExt (String id, String unit, int meter, String ext) {
		return billingRepository.findAllByIdAndUnitAndMeterAndExtAndEnableOrderByTimestampDesc(id, unit, meter, ext, Constants.FLAG_ENABLED);
    }
    
    public Billing getLatestBill (String id, String unit, int meter) {
		return billingRepository.findTopByIdAndUnitAndMeterAndEnableOrderByTimestampDesc(id, unit, meter, Constants.FLAG_ENABLED);
    }
    
    public Billing getBillByBillId (long billId) {
		return billingRepository.findTopByBillIdAndEnable(billId, Constants.FLAG_ENABLED);
    }
    
    public List<Billing> getAllbillingLogs (String ext) {
		return billingRepository.findAllByExtAndEnableOrderByTimestampDesc(ext, Constants.FLAG_ENABLED);
    }
    
    public long getLatestInvoiceNumber() {
    	return billingRepository.findTopByEnableOrderByBillIdDesc(Constants.FLAG_ENABLED).getBillId();
    };
}
