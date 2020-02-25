 package com.stardust.sync.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stardust.sync.core.ModbusSingleton;
import com.stardust.sync.model.Meter;
import com.stardust.sync.service.MeterService;

import de.re.easymodbus.modbusclient.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class UserRestController {
	
	@Autowired
	MeterService meterService;
	
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
	private final int S1= 1628;
	private final int S2 = 1630;
	private final int S3 = 1632;
	private final int S4 = 1634;

	
	@GetMapping(value = "rest/reading")
    public List<Meter> getReadings(String id, String unit) {

       List<Meter> meters = new ArrayList<Meter>();
       
       
       int address = 0;
		if(id != null && unit != null) {
			switch(id) {
			  case "Ground":
				address = G1;
			    break;
			  case "3rd":
				  switch(unit) {
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
				  switch(unit) {
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
				  switch(unit) {
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
				  switch(unit) {
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
    	   meters.add(new Meter(id,unit,1,getValue(address),"kWh", new Date()));
     	   meters.add(new Meter(id,unit,2,getValue(address+1),"BTU", new Date()));
     	   meters.add(new Meter(id,unit,3,getValue(address+2),"BTU", new Date()));
     	   meters.add(new Meter(id,unit,4,getValue(address+3),"BTU", new Date()));
    	   
       }else {
    	   meters.add(new Meter(id,unit,1,getValue(address),"kWh", new Date()));
     	   meters.add(new Meter(id,unit,2,getValue(address+1),"BTU", new Date()));
       }
       
       //meters.add(getValue(id, unit));
			return meters;
        }else {
		return null;}
    }
	
	private double getValue (int address) {
		
		double value=0.0;
		
		
			try{
			ModbusClient modbusClient = ModbusSingleton.getInstance();
			value = (double) ModbusClient.ConvertRegistersToFloat(modbusClient.ReadHoldingRegisters(address-1, 2));

			}catch (Exception e){
				System.out.println("Modbus read error "+e);
			}
			
			
			return value;	
		
	}
	
	@GetMapping(value = "rest/byId")
    public List<Meter> getReadingsById(String id, String unit) {
		return meterService.byIdAndUnit(id, unit);
		
	}
	
}


