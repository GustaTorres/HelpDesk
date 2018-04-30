package com.psgv.helpdesk;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Test {

	public static void main(String[] args) {
		boolean checkpw = BCrypt.checkpw("123456", "$2a$10$4PLCnqoeHDM/qDN0HbzgeOM/gmmqdEsaVePeKKCmihxkPsKh9u7Ea");
		System.out.println(checkpw);
	}

}
