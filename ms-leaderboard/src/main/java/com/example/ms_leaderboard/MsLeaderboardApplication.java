package com.example.ms_leaderboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsLeaderboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsLeaderboardApplication.class, args);
	}

}
