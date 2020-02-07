package com.starduct.sync.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.starduct.sync.model.Meter;

import de.re.easymodbus.modbusclient.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserRestController {

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
    public List<Meter> getCities(String id, String unit) {

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
       
       if (id == "Ground") {
    	   meters.add(new Meter(id,unit,getValue(address),"kWh"));
    	   meters.add(new Meter(id,unit,getValue(address+1),"BTU"));
    	   meters.add(new Meter(id,unit,getValue(address+2),"BTU"));
    	   meters.add(new Meter(id,unit,getValue(address+3),"BTU"));
    	   
       }else {
    	   meters.add(new Meter(id,unit,getValue(address),"kWh"));
    	   meters.add(new Meter(id,unit,getValue(address+1),"BTU"));
       }
       
       //meters.add(getValue(id, unit));
			return meters;
        }else {
		return null;}
    }
	
	private double getValue (int address) {
		ModbusClient modbusClient = new ModbusClient("192.168.1.10",502);
		double value=0.0;
		
		
			try{
			modbusClient.Connect();
			value = (double) ModbusClient.ConvertRegistersToFloat(modbusClient.ReadHoldingRegisters(address-1, 2));

			}catch (Exception e){
				System.out.println("Modbus read error "+e);
			}finally {
				try {
					modbusClient.Disconnect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			return value;	
		
	}
}


