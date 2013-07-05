package com.mbcdev.hackday2013.validation.impl;

import com.mbcdev.hackday2013.validation.EmailValidator;

import android.util.Patterns;

/**
 * Uses the Patterns class in the Android SDK to validate email. This is
 * only available for API levels 8 and up.  This isn't an exhaustive email
 * validation but should suffice for most cases.
 * 
 * @author barryc
 */
public class AndroidEmailValidator implements EmailValidator {

    @Override
    public boolean isValid(CharSequence email) {
        if (email == null) {
            return false;
        }
        
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
