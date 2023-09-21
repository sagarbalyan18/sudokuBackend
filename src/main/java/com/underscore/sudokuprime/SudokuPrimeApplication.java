package com.underscore.sudokuprime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SudokuPrimeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SudokuPrimeApplication.class, args);
    }

    @GetMapping("/user")
    public UserResponse getUser() {
        return new UserResponse("success",
				new UserResult(
						"Sagar",
						new Friend("saransh"),
						"341",
						"26",
						"sagarbalyan18@gmail.com",
						"noobie",
						"1",
						"yes",
						"2",
						"2"));
    }

    record Friend(String name) {
    }

    record UserResult(
			String name,
			Friend friends,
			String userId,
			String age,
			String email,
			String badge,
			String rank,
			String isTop100,
			String boardsSolved,
			String totalBoardPlayed
			) {
    }

    record UserResponse(String status, UserResult result) {
    }

}
