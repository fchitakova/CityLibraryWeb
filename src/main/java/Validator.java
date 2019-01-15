package main.java;


public class Validator {
	public static boolean checkInputTextValidity(String input) {

		return input.matches(Constants.VALID_NAMETEXT_REGEX);
	}
}
