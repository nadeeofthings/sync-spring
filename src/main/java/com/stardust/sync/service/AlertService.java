package com.stardust.sync.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.stardust.sync.core.Constants;
import com.stardust.sync.model.Activity;
import com.stardust.sync.model.Alert;
import com.stardust.sync.model.User;
import com.stardust.sync.repository.AlertRepository;

@Service
public class AlertService {
	private static final Logger         LOGGER  = LoggerFactory.getLogger(MeterConfigurationService.class);
	SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
	
	@Autowired
	private AlertRepository alertRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	ActivityService activityService;
	
	@Autowired
    private NotificationDispatcherService notificationDispatcherService;
	
	public void warn(String message) {
		Alert alert = new Alert(message, Constants.ALERT_WARN, new Date(), Constants.ALERT_STATUS_PENDING);
		//userService.updateAlertCount();
		notificationDispatcherService.dispatch(alert);
		alertRepository.save(alert);
	}
	public void error(String message) {
		Alert alert = new Alert(message, Constants.ALERT_DANGER, new Date(), Constants.ALERT_STATUS_PENDING);
		//userService.updateAlertCount();
		notificationDispatcherService.dispatch(alert);
		alertRepository.save(alert);
	}
	public void info(String message) {
		Alert alert = new Alert(message, Constants.ALERT_INFO, new Date(), Constants.ALERT_STATUS_ACK);
		//userService.updateAlertCount();
		notificationDispatcherService.dispatch(alert);
		alertRepository.save(alert);
	}
	public void success(String message) {
		Alert alert = new Alert(message, Constants.ALERT_SUCCESS, new Date(), Constants.ALERT_STATUS_ACK);
		//userService.updateAlertCount();
		notificationDispatcherService.dispatch(alert);
		alertRepository.save(alert);
	}
	
	public List<Alert> findAllByOderByTimeStamp() {
		return alertRepository.findAllByOrderByTimeStampDesc();
	}
	
	public List<Alert> findTop3OrderByTimeStampDesc (){
		return alertRepository.findTop3ByOrderByTimeStampDesc();
	}
	
	public List<Alert> findAll (){
		return alertRepository.findAll();
	}
	
	private Alert fildById(long id){
		return alertRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException());
	}
	
	public void acknowledgeAlert(Authentication authentication, long id) {
		Alert alrt = fildById(id);
		alrt.setStatus(Constants.ALERT_STATUS_ACK);
		activityService.log("Alert: \""+alrt.getMessage()+"\" on "+format.format(alrt.getTimeStamp())+" was acknowledged", authentication.getName());
		alertRepository.save(alrt);
	}
	public long getAlertCount() {
		return alertRepository.countByStatus(Constants.ALERT_STATUS_PENDING);
	}

}
