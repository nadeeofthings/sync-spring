package com.stardust.sync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
public class SyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(SyncApplication.class, args);
	}

}

@Configuration
@EnableScheduling
class SchedulingConfiguration {}