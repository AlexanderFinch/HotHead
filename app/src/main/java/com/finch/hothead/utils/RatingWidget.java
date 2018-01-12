package com.finch.hothead.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.finch.hothead.R;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Created by finchrat on 11/6/2016.
 */
public class RatingWidget {

    public static void setRatingStars(View v, String rating, String ratingCount) {
        TextView ratingCountView = (TextView) v.findViewById(R.id.widget_rating_count);
        ratingCountView.setText(ratingCount);

        Double drating = new BigDecimal((rating == null) ? "0" : rating).round(new MathContext(2)).doubleValue();
        TextView ratingView = (TextView) v.findViewById(R.id.widget_rating);
        ratingView.setText(drating.toString());

        ImageView button1 = (ImageView) v.findViewById(R.id.widget_rating_1);
        ImageView button2 = (ImageView) v.findViewById(R.id.widget_rating_2);
        ImageView button3 = (ImageView) v.findViewById(R.id.widget_rating_3);
        ImageView button4 = (ImageView) v.findViewById(R.id.widget_rating_4);
        ImageView button5 = (ImageView) v.findViewById(R.id.widget_rating_5);
        int idOn = v.getResources().getIdentifier("android:drawable/btn_star_big_on", null, null);
        int idOff = v.getResources().getIdentifier("android:drawable/btn_star_big_off", null, null);
        if (drating <= 0.1) {
            button1.setImageResource(idOff);
            button2.setImageResource(idOff);
            button3.setImageResource(idOff);
            button4.setImageResource(idOff);
            button5.setImageResource(idOff);
        } else if (drating <= 1) {
            button1.setImageResource(idOn);
            button2.setImageResource(idOff);
            button3.setImageResource(idOff);
            button4.setImageResource(idOff);
            button5.setImageResource(idOff);
        } else if (drating <= 2) {
            button1.setImageResource(idOn);
            button2.setImageResource(idOn);
            button3.setImageResource(idOff);
            button4.setImageResource(idOff);
            button5.setImageResource(idOff);
        } else if (drating <= 3) {
            button1.setImageResource(idOn);
            button2.setImageResource(idOn);
            button3.setImageResource(idOn);
            button4.setImageResource(idOff);
            button5.setImageResource(idOff);
        } else if (drating <= 4) {
            button1.setImageResource(idOn);
            button2.setImageResource(idOn);
            button3.setImageResource(idOn);
            button4.setImageResource(idOn);
            button5.setImageResource(idOff);
        } else if (drating <= 5) {
            button1.setImageResource(idOn);
            button2.setImageResource(idOn);
            button3.setImageResource(idOn);
            button4.setImageResource(idOn);
            button5.setImageResource(idOn);
        }
    }
}
