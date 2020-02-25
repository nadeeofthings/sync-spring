package com.stardust.sync.timekeeper;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;

import com.stardust.sync.core.Constants;
import com.stardust.sync.service.ConfigurationService;
import com.stardust.sync.service.MeterService;
import com.stardust.sync.service.UserService;

@Service
public class SchedulerService implements SchedulingConfigurer {
	
	private static final Logger LOG = LoggerFactory.getLogger(SchedulerService.class);

    @Autowired
    ConfigurationService configurationService;
    
    @Autowired
    private MeterService meterService;
   
    @Bean
    public TaskScheduler poolScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        scheduler.setPoolSize(5);
        scheduler.initialize();
        return scheduler;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(poolScheduler());
        taskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                // Do not put @Scheduled annotation above this method, we don't need it anymore.
                configurationService.loadConfigurations();
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
            	Calendar nextExecutionTime = new GregorianCalendar();
                Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
                nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
                nextExecutionTime.add(Calendar.MILLISECOND, (Integer.parseInt(configurationService.getConfiguration(Constants.CONFIG_KEY_REFRESH_RATE_CONFIG).getConfigValue()))*1000);
                return nextExecutionTime.getTime();
            }
        });
        
        //Peak start reading
        taskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                // Do not put @Scheduled annotation above this method, we don't need it anymore.
                LOG.info("Peak time capture");
                meterService.captureReadings();
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
            	String PeakStart = configurationService.getConfiguration(Constants.CONFIG_KEY_PEAK_START_CONFIG).getConfigValue();
            	Calendar cal = Calendar.getInstance();
            	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
            	try {
					cal.setTime(sdf.parse(PeakStart));
				} catch (ParseException e) {
					LOG.error("Time parse error:"+e);
				}// all done
            	
            	int hours = cal.get(Calendar.HOUR_OF_DAY);
            	int minutes = cal.get(Calendar.MINUTE);
            	ZonedDateTime now = ZonedDateTime.now();
                ZonedDateTime nextRun = now.withHour(hours).withMinute(minutes).withSecond(0);
                if(now.compareTo(nextRun) > 0)
                    nextRun = nextRun.plusDays(1);
                
                return java.util.Date.from(nextRun.toInstant());
            }
        });
        
      //Peak end reading
        taskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
            	// Do not put @Scheduled annotation above this method, we don't need it anymore.
                LOG.info("Peak time end capture");
                meterService.captureReadings();
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
            	String PeakStart = configurationService.getConfiguration(Constants.CONFIG_KEY_PEAK_END_CONFIG).getConfigValue();
            	Calendar cal = Calendar.getInstance();
            	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
            	try {
					cal.setTime(sdf.parse(PeakStart));
				} catch (ParseException e) {
					LOG.error("Time parse error:"+e);
				}// all done
            	
            	int hours = cal.get(Calendar.HOUR_OF_DAY);
            	int minutes = cal.get(Calendar.MINUTE);
            	ZonedDateTime now = ZonedDateTime.now();
                ZonedDateTime nextRun = now.withHour(hours).withMinute(minutes).withSecond(0);
                if(now.compareTo(nextRun) > 0)
                    nextRun = nextRun.plusDays(1);
                
                return java.util.Date.from(nextRun.toInstant());
            }
        });
    }

}