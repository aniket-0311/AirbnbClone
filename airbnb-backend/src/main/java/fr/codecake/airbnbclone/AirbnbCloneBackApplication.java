package fr.codecake.airbnbclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AirbnbCloneBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirbnbCloneBackApplication.class, args);
	}

}
