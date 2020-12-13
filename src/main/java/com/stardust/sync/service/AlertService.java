package com.stardust.sync.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stardust.sync.core.Constants;
import com.stardust.sync.model.Alert;
import com.stardust.sync.model.User;
import com.stardust.sync.repository.AlertRepository;

@Service
public class AlertService {
	private static final Logger         LOGGER  = LoggerFactory.getLogger(MeterConfigurationService.class);
	
	@Autowired
	private AlertRepository alertRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private NotificationDispatcherService notificationDispatcherService;
	
	public void warn(String message) {
		Alert alert = new Alert(message, Constants.ALERT_WARN, new Date());
		userService.updateAlertCount();
		notificationDispatcherService.dispatch(alert);
		alertRepository.save(alert);
	}
	public void error(String message) {
		Alert alert = new Alert(message, Constants.ALERT_DANGER, new Date());
		userService.updateAlertCount();
		notificationDispatcherService.dispatch(alert);
		alertRepository.save(alert);
	}
	public void info(String message) {
		Alert alert = new Alert(message, Constants.ALERT_INFO, new Date());
		userService.updateAlertCount();
		notificationDispatcherService.dispatch(alert);
		alertRepository.save(alert);
	}
	public void success(String message) {
		Alert alert = new Alert(message, Constants.ALERT_SUCCESS, new Date());
		userService.updateAlertCount();
		notificationDispatcherService.dispatch(alert);
		alertRepository.save(alert);
	}
	
	public List<Alert> findTop3OrderByTimeStampDesc (){
		return alertRepository.findTop3ByOrderByTimeStampDesc();
	}
	
	public List<Alert> findAll (){
		return alertRepository.findAll();
	}
}
