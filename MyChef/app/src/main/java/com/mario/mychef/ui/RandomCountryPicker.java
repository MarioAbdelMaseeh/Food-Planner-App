package com.mario.mychef.ui;

import java.util.Random;

public class RandomCountryPicker {
    private static final String[] COUNTRIES = {
            "American", "British", "Canadian", "Chinese", "Croatian", "Dutch", "Egyptian",
            "Filipino", "French", "Greek", "Indian", "Irish", "Italian", "Jamaican",
            "Japanese", "Kenyan", "Malaysian", "Mexican", "Moroccan", "Norwegian",
            "Polish", "Portuguese", "Russian", "Spanish", "Thai", "Tunisian", "Turkish",
            "Ukrainian", "Uruguayan", "Vietnamese"
    };

    public static String getRandomCountry() {
        Random random = new Random();
        return COUNTRIES[random.nextInt(COUNTRIES.length)];
    }
}
