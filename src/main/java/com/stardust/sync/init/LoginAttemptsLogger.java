package com.stardust.sync.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.stardust.sync.service.ActivityService;

@Component
public class LoginAttemptsLogger {
	
	@Autowired
	ActivityService activityService;

    @EventListener
    public void auditEventHappened(InteractiveAuthenticationSuccessEvent auditApplicationEvent) {
        
        
        activityService.log("Logged in", auditApplicationEvent.getAuthentication().getName());

    }
}
