package com.blck_rbbt.nordCodes;

import com.blck_rbbt.nordCodes.DB.handlers.DBHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NordCodesApplication {
	private static final DBHandler CONNECTION = new DBHandler();
	
	public static void main(String[] args) {
		CONNECTION.createDB();
		SpringApplication.run(NordCodesApplication.class, args);
	}

}
