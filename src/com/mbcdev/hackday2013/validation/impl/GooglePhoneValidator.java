package com.mbcdev.hackday2013.validation.impl;

import java.util.Locale;

import android.util.Log;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.mbcdev.hackday2013.validation.PhoneValidator;

public class GooglePhoneValidator implements PhoneValidator {

    private String mCountryCode;
    private PhoneNumber mPhoneNumber;

    private static final PhoneNumberUtil PHONE_UTIL = PhoneNumberUtil.getInstance();
    
    /**
     * Create an instance of the phone validator for the given
     * ISO 3166-2 country code. 
     * 
     * @param countryCode
     */
    public GooglePhoneValidator(final String countryCode) {
        
        if (countryCode == null || 2 != countryCode.length()) {
            throw new IllegalStateException("Invalid country code specified, validation cannot continue");
        } else {
            this.mCountryCode = countryCode.toUpperCase(Locale.UK);
        }
        
        this.mCountryCode = countryCode;
    }
    
    @Override
    public boolean isValid(CharSequence phoneNumber) {
        
        if (phoneNumber == null) {
            return false;
        }
        
        try {
            mPhoneNumber = 
                    PHONE_UTIL.parse(phoneNumber.toString(), this.mCountryCode);
        } catch (NumberParseException e) {
            Log.e(GooglePhoneValidator.class.getName(), "Failed to parse phone number", e);
        }
        
        return mPhoneNumber == null ? false : PHONE_UTIL.isValidNumber(mPhoneNumber);
    }
    

}
