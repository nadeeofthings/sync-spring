package com.stardust.sync.init;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.stardust.sync.service.ActivityService;

@Component
public class LogoutAttemptsLogger implements ApplicationListener<SessionDestroyedEvent> {

	@Autowired
	ActivityService activityService;
	
    @Override
    public void onApplicationEvent(SessionDestroyedEvent event)
    {
        List<SecurityContext> lstSecurityContext = event.getSecurityContexts();
        UserDetails ud;
        for (SecurityContext securityContext : lstSecurityContext)
        {
            ud = (UserDetails) securityContext.getAuthentication().getPrincipal();
            activityService.log("Logged out", ud.getUsername());
            
            // ...
        }
    }

}
