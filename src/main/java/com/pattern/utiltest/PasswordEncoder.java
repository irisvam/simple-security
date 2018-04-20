package com.pattern.utiltest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

	public static void main(String[] args) {

		BCryptPasswordEncoder password = new BCryptPasswordEncoder();
		System.out.println(password.encode("123456"));
	}

}
