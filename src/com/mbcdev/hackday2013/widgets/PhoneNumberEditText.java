package com.mbcdev.hackday2013.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.EditText;

import com.mbcdev.hackday2013.R;
import com.mbcdev.hackday2013.validation.PhoneValidator;
import com.mbcdev.hackday2013.validation.impl.GooglePhoneValidator;

/**
 * This EditText will validate phone numbers. The expected country code can 
 * be supplied from xml. E.g.
 * <p>
 * <com.mbcdev.hackday2013.widgets.PhoneNumberEditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="@dimen/text_medium"
        mbcdev:countryCode="IE" />
   </p>
   
 * TODO: this would probably be more useful if it was a compound widget itself
 * which would find the country code for itself or have the user enter it.
 * 
 * @author barryc
 */
public class PhoneNumberEditText extends EditText {
    
    private PhoneValidator mPhoneValidator;
    
    public PhoneNumberEditText(Context context) {
        this(context, null);
    }
    
    public PhoneNumberEditText(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }
    
    public PhoneNumberEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setInputType(InputType.TYPE_CLASS_PHONE);
        initialise(context, attrs);
    }
    
    private void initialise(Context context, AttributeSet attrs) {
        
        TypedArray typedArray = 
                context.obtainStyledAttributes(attrs, R.styleable.PhoneNumberEditText);
        
        String countryCode = 
                typedArray.getString(R.styleable.PhoneNumberEditText_countryCode);
        
        mPhoneValidator = new GooglePhoneValidator(countryCode);
        
        addTextChangedListener(new TextChangedAdapter() {
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    if (mPhoneValidator.isValid(s)) {
                        setError(null);
                    } else {
                        setError(getContext().getString(R.string.error_invalid_phone));
                    }
                }
            }
        });
        
        typedArray.recycle();
    }
}
