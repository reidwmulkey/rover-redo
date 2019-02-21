package com.rover.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AlphabetUtil {

	public static final Set<String> lowerCaseAlphabet = new HashSet<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g",
			"h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"));

	public static Set<String> getDistinctLowerCaseCharactersInString(String s) {

		Set<String> distinctLowerCaseCharacters = new HashSet<>();

		Arrays.stream(s.split("")).forEach(c -> {
			if (lowerCaseAlphabet.contains(c) && !distinctLowerCaseCharacters.contains(c)) {
				distinctLowerCaseCharacters.add(c);
			}
		});

		return distinctLowerCaseCharacters;
	}
}
