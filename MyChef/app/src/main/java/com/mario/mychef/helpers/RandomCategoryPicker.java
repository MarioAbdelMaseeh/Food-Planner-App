package com.mario.mychef.helpers;

import java.util.Random;

public class RandomCategoryPicker {
    private static final String[] CATEGORIES = {
            "Beef", "Breakfast", "Chicken", "Dessert", "Goat", "Lamb", "Miscellaneous",
            "Pasta", "Pork", "Seafood", "Side", "Starter", "Vegan", "Vegetarian"
    };

    public static String getRandomCategory() {
        Random random = new Random();
        return CATEGORIES[random.nextInt(CATEGORIES.length)];
    }
}
