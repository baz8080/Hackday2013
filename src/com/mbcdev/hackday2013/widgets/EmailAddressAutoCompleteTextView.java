package com.mbcdev.hackday2013.widgets;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.mbcdev.hackday2013.R;
import com.mbcdev.hackday2013.validation.EmailValidator;
import com.mbcdev.hackday2013.validation.impl.AndroidEmailValidator;

/**
 * An EditText that will enumerate the users' known accounts and set up an
 * auto-complete adapter filled with their email addresses. This requires the
 * {@link Manifest.permission#GET_ACCOUNTS GET_ACCOUNTS} permission or else it will 
 * function in the exact same way as a standard {@link AutoCompleteTextView}, albeit 
 * with built-in setError() for validation.
 * 
 * @author barryc
 */
public class EmailAddressAutoCompleteTextView extends AutoCompleteTextView {

    private EmailValidator mEmailValidator;
    
    public EmailAddressAutoCompleteTextView(Context context) {
        this(context, null);
    }
    
    public EmailAddressAutoCompleteTextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.autoCompleteTextViewStyle);
    }
    
    public EmailAddressAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        
        initialise();
    }
    
    /**
     * Attempt to get the users' accounts and add all of the ones whose names 
     * are email addresses to an auto-complete adapter for this view.
     */
    private void initialise() {
        
        if (PackageManager.PERMISSION_DENIED == 
                getContext().checkCallingOrSelfPermission(
                        Manifest.permission.GET_ACCOUNTS) || isInEditMode()) {

            Log.e(EmailAddressAutoCompleteTextView.class.getCanonicalName(), 
                    "You must declare the GET_ACCOUNTS permission to use this view");
            return;
        }
        
        AccountManager accountManager = AccountManager.get(getContext());
        
        List<String> emailAddresses = new ArrayList<String>();
        
        mEmailValidator = new AndroidEmailValidator();

        for (Account account: accountManager.getAccounts()) {
            if (mEmailValidator.isValid(account.name)) {
                if (!emailAddresses.contains(account.name)) {
                    emailAddresses.add(account.name);
                }
            }
        }
        
        if (emailAddresses.size() > 0) {
            
            ArrayAdapter<String> emailAddressAdapter = 
                    new ArrayAdapter<String>(
                            getContext(),
                            android.R.layout.simple_dropdown_item_1line, 
                            emailAddresses);
            
            setAdapter(emailAddressAdapter);
        }
        
        addTextChangedListener(new TextChangedAdapter() {
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    if (mEmailValidator.isValid(s)) {
                        setError(null);
                    } else {
                        setError(getContext().getString(R.string.error_invalid_email));
                    }
                }
            }
        });
    }
}
