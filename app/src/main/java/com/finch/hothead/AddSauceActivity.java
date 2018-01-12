package com.finch.hothead;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.finch.hothead.db.tables.Sauce;

/**
 * Created by finchrat on 7/9/2016.
 */
public class AddSauceActivity extends AppCompatActivity{
    private EditText nameView;
    private EditText descView;
    private EditText companyView;
//    private Spinner shuView;
    private Spinner hotnessView;
    private EditText pepperView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sauce);

        nameView = (EditText) findViewById(R.id.add_sauce_name);
        descView = (EditText) findViewById(R.id.add_sauce_description);
        companyView = (EditText) findViewById(R.id.add_sauce_company);
//        shuView = (Spinner) findViewById(R.id.add_sauce_shu);
//        ArrayAdapter<String> shuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Sauce.Hotness.getShuScale());
//        shuView.setAdapter(shuAdapter);
        hotnessView = (Spinner) findViewById(R.id.add_sauce_hotness);
        ArrayAdapter<String> hotnessAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Sauce.Hotness.getHotnessScale());
        hotnessView.setAdapter(hotnessAdapter);
        pepperView = (EditText) findViewById(R.id.add_sauce_pepper);
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    public void addSauce(View view) {
        validate();
        String name = nameView.getText().toString();
        String desc = descView.getText().toString();
        String company = companyView.getText().toString();
//        String shu = shuView.getSelectedItem().toString();
        String hotness = hotnessView.getSelectedItem().toString();
        String pepper = pepperView.getText().toString();

        if(nameView.getError() == null &&
                descView.getError() == null &&
                companyView.getError() == null &&
//                shuViewNum.getError() == null &&
//                hotnessView.getError() == null &&
                pepperView.getError() == null) {
            Sauce sauce = new Sauce(name, desc, company, "0", hotness, pepper, "");
            sauce.save();
            finish();
        }
    }

    private void validate() {
        if (nameView.getText().toString().isEmpty()) {
            nameView.setError(getString(R.string.text_blank_warning));
        }
        if (descView.getText().toString().isEmpty()) {
            descView.setError(getString(R.string.text_blank_warning));
        }
        if (companyView.getText().toString().isEmpty()) {
            companyView.setError(getString(R.string.text_blank_warning));
        }
//        if (shuView.getText().toString().isEmpty()) {
//            shuView.setError(getString(R.string.text_blank_warning));
//        }
//        if (hotnessView.getSelectedItem().toString().isEmpty()) {
//            hotnessView(getString(R.string.text_blank_warning));
//        }
        if (pepperView.getText().toString().isEmpty()) {
            pepperView.setError(getString(R.string.text_blank_warning));
        }
    }
}
