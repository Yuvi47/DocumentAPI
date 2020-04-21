package com.app.utils;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class NameGenerator {

	private final Random RANDOM = new SecureRandom();
	private static final int SIZE = 11;
	private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public String generaterFileName() {

		return generaterRandomString(SIZE);
	}

	private String generaterRandomString(int lenght) {
		StringBuilder stringBuilder = new StringBuilder(lenght);
		for (int i = 0; i < lenght; i++) {

			stringBuilder.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}
		return new String(stringBuilder);
	}

}
