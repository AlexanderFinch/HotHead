package com.finch.hothead.utils;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by finchrat on 12/21/2016.
 */
public abstract class TextValidator implements TextWatcher{

    public abstract void validate(String text);

    @Override
    final public void afterTextChanged(Editable s) {
        validate(s.toString());
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Don't care */ }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }
}
