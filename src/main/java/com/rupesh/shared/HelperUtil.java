package com.rupesh.shared;

public final class HelperUtil {


    private HelperUtil() {
    }

    public static boolean isValidEmail(String email) {
        return email.contains("@");
    }

    public static String isValidUsername(String username) {

        // Check username length
        if (username.length() < 8 || username.length() > 16) {
            return "username must be at least 4 characters long and at least 16 characters.";
        }

        // Check for at least one uppercase letter
        if (!username.matches(".*[A-Z].*")) {
            return "username must contain at least one uppercase letter.";
        }

        // Check for at least one lowercase letter
        if (!username.matches(".*[a-z].*")) {
            return "username must contain at least one lowercase letter.";
        }

        // Check for at least one special character
        if (!username.matches(".*[@#$%^&+=!].*")) {
            return "username must contain at least one special character";
        }

        return "valid username";
    }

    public static String isValidPassword(String password, String firstName, String lastName, String email) {
        // Check password length
        if (password.length() < 8 || password.length() > 16) {
            return "password must be at least 8 characters long and at least 16 characters.";
        }

        // Check for at least one uppercase letter
        if (!password.matches(".*[A-Z].*")) {
            return "password must contain at least one uppercase letter.";
        }

        // Check for at least one lowercase letter
        if (!password.matches(".*[a-z].*")) {
            return "password must contain at least one lowercase letter.";
        }

        // Check for at least one special character
        if (!password.matches(".*[@#$%^&+=!].*")) {
            return "password must contain at least one special character";
        }

        // Check if the password is equal to the first name, last name, or email
        if (password.equalsIgnoreCase(firstName) || password.equalsIgnoreCase(lastName) || password.equalsIgnoreCase(email)) {
            return "password must not be equal to first name, last name, or email.";
        }

        return "valid password";
    }
}
