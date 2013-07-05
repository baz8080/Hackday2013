package com.mbcdev.hackday2013.widgets;

import android.text.Editable;
import android.text.TextWatcher;

public abstract class TextChangedAdapter implements TextWatcher {

    @Override
    public void afterTextChanged(Editable s) {}

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
}
