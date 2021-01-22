package com.stardust.sync.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.stardust.sync.model.Activity;
import com.stardust.sync.model.User;
import com.stardust.sync.repository.ActivityRepository;

@Service
public class ActivityService {

private static final Logger LOGGER  = LoggerFactory.getLogger(MeterConfigurationService.class);
	
	@Autowired
	private ActivityRepository activityRepository;
	
	@Autowired
	private UserService userService;
	
	public void log(String message, long userId) {
		Activity act = new Activity(message, new Date(), userId);
		activityRepository.save(act);
	}
	
	public void log(String message, String username) {
		User usr = userService.findByUsername(username);
		Activity act = new Activity(message, new Date(), usr.getId());
		activityRepository.save(act);
	}

	public List<Activity> getActivities(Authentication authentication) {
		if((authentication.getAuthorities().toString().contains("ROLE_SUPERADMIN")||authentication.getAuthorities().toString().contains("ROLE_ADMIN"))) {
			
			List<Activity> actList = activityRepository.findAll();
			for(Activity act : actList) {
				User usr = userService.findById(act.getUserId());
				act.setUser(usr);
			}
			return actList;
			
		}else {
			User usr = userService.findByUsername(authentication.getName());
			List<Activity> actList = activityRepository.findByUserId(usr.getId()); 
			for(Activity act : actList) {
				act.setUser(usr);
			}
			return actList;
		}
	}
}
