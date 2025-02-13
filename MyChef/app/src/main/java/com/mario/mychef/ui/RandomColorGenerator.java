package com.mario.mychef.ui;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.mario.mychef.R;

import java.util.Random;

public class RandomColorGenerator {

    private static final int[] colorArray = new int[]{
            R.color.soft_blue,
            R.color.soft_purple,
            R.color.soft_yellow,
            R.color.soft_red,
            R.color.soft_orange,
            R.color.soft_green,
            R.color.soft_pink,
            R.color.soft_gray
    };

    public static int getRandomColor(Context context) {
        Random random = new Random();
        int index = random.nextInt(colorArray.length);
        return ContextCompat.getColor(context, colorArray[index]);
    }
}
