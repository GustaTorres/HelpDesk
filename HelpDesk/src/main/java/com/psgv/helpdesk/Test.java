package com.psgv.helpdesk;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Test {

	public static void main(String[] args) {
		boolean checkpw = BCrypt.checkpw("123456", "$2a$10$eUX021zVyMi2NkvUdb7GtOEVC7/OXtK8qLwJzsPGWsJwq1XhVE8Ja");
		System.out.println(checkpw);
	}

}
