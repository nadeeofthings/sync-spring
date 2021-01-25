package com.stardust.sync.core;

public class Constants {
	
	public static final String CALIBRI_REGULAR = "src/main/webapp/resources/Fonts/Calibri Regular.ttf";
		public static final String CALIBRI_BOLD = "src/main/webapp/resources/Fonts/Calibri Bold.ttf";
		public static final String CALIBRI_ITALIC = "src/main/webapp/resources/Fonts/Calibri Italic.ttf";

    public static final String  CONFIG_KEY_REFRESH_RATE_CONFIG  = "ConfigRefreshRate";
    public static final String  CONFIG_KEY_PEAK_START_CONFIG   = "PeakStart";
    public static final String  CONFIG_KEY_PEAK_END_CONFIG   = "PeakEnd";
    
    public static final String  CONFIG_KEY_T_AND_C   = "TAndC";
    public static final String  CONFIG_KEY_EMERGENCY_CONTACT   = "EmergencyContact";
    public static final String  CONFIG_KEY_BILLING_INQUIRIES   = "BillingInquiries";
    public static final String  CONFIG_KEY_DUE_DAYS_PERIOD   = "DueDaysPeriod";
    public static final String  CONFIG_KEY_NBTAX   = "NBTax";
    public static final String  CONFIG_KEY_VATAX   = "VATax";
    public static final String  CONFIG_KEY_SERVICE_CHARGE   = "ServiceCharge";
    public static final String  CONFIG_KEY_PEAK_RATE   = "PeakRate";
    public static final String  CONFIG_KEY_OFF_PEAK_RATE   = "OffPeakRate";
    public static final String  CONFIG_KEY_PENALTY   = "Penalty";
    public static final String  CONFIG_KEY_DISCOUNT   = "Discount";
    
    
    public static final String  CONFIG_KEY_REFRESH_RATE_CONFIG_DEFAULT  = "300";
    public static final String  CONFIG_KEY_PEAK_START_CONFIG_DEFAULT   = "8:00";
    public static final String  CONFIG_KEY_PEAK_END_CONFIG_DEFAULT   = "17:00";
    
    public static final int ALERT_STATUS_PENDING = 1;
    public static final int ALERT_STATUS_ACK = 0;
    
    public static final int ALERT_WARN = 2;
    public static final int ALERT_DANGER = 3;
	public static final int ALERT_INFO = 1;
	public static final int ALERT_SUCCESS = 4;
	
	public static final int FLAG_ENABLED = 1;
	public static final int FLAG_DISABLED = 2;
	public static final int FLAG_DELETED = 3;

}
