package com.mbcdev.hackday2013.validation;

public interface EmailValidator {

    /**
     * Validate an email address according to some pattern.
     * 
     * @param email The email address to validate
     * @return true if this email address is valid, false if it is null or invalid
     */
    boolean isValid(CharSequence email);
}
