package com.stardust.sync.core;

import java.io.IOException;
import java.net.UnknownHostException;

import de.re.easymodbus.modbusclient.ModbusClient;

public class ModbusSingleton {
	
	private static String address = "127.0.0.1";
	private static int port = 502;
	private static ModbusClient modbusClient = null;
	
	private ModbusSingleton() {
    }
     
    public static ModbusClient getInstance() throws UnknownHostException, IOException {
        if(modbusClient == null) {
        	modbusClient =  new ModbusClient(address,port);
        	modbusClient.Connect();
        }
        return modbusClient;
    }
 
    // getters and setters

//public enum ModbusSingleton {
    
	/*
	 * INSTANCE("Initial class info");
	 * 
	 * private ModbusClient modbusClient;
	 * 
	 * private ModbusSingleton(String info) { this.modbusClient = new
	 * ModbusClient("127.0.0.1",502); try { this.modbusClient.Connect(); } catch
	 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); } }
	 * 
	 * public ModbusSingleton getInstance() { return INSTANCE; }
	 * 
	 * public ModbusClient getModbusClient() { return modbusClient; }
	 * 
	 * public void setModbusClient(ModbusClient modbusClient) { this.modbusClient =
	 * modbusClient; }
	 */
	
	
     
    
}
