package co.kr.pcninc.data.marketcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MarketCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketCoreApplication.class, args);
	}

}
