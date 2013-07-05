package com.mbcdev.hackday2013.widgets;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import com.mbcdev.hackday2013.R;

/**
 * EditText field that will perform validation based on attributes in the layout
 * XML. E.g.
 * 
 * <p>
 * mbcdev:validationRegex="^\\w{4,8}$"
 * mbcdev:validationError="@string/error_invalid_username"
 * </p>
 * 
 * @see R.styleable#ValidatingEditText_validationError
 * @see R.styleable#ValidatingEditText_validationRegex
 * 
 * @author barryc
 */
public class ValidatingEditText extends EditText {

    private Pattern mValidationPattern;
    private String mError;
    
    public ValidatingEditText(Context context) {
        this(context, null);
    }
    
    public ValidatingEditText(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }
    
    public ValidatingEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialise(context, attrs);
    }
    
    private void initialise(Context context, AttributeSet attrs) {
        TypedArray typedArray = 
                context.obtainStyledAttributes(attrs, R.styleable.ValidatingEditText);
        
        if (typedArray.hasValue(R.styleable.ValidatingEditText_validationRegex)) {
            
            String regex = 
                    typedArray.getString(
                            R.styleable.ValidatingEditText_validationRegex);
            
            if (regex != null) {
            
                if (typedArray.hasValue(R.styleable.ValidatingEditText_validationError)) {
                    this.mError =
                            typedArray.getString(
                                    R.styleable.ValidatingEditText_validationError);
                    
                    if (this.mError == null) {
                        this.mError = context.getString(R.string.error_generic);
                    }
                }
                
                try {
                    mValidationPattern = Pattern.compile(regex);
                } catch (PatternSyntaxException pe) {
                    Log.e(ValidatingEditText.class.getName(), pe.getMessage());
                }
                
                if (mValidationPattern != null) {
                    addTextChangedListener(new TextChangedAdapter() {
                        
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (s != null) {
                                if (mValidationPattern.matcher(s).matches()) {
                                    setError(null);
                                } else {
                                    setError(mError);
                                }
                            }
                        }
                    });
                }
            }
        }
        
        typedArray.recycle();
    }
}
