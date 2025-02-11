package com.mario.mychef.models;

public class IngredientsAndMeasuresDTO {
    String ingredient;
    String measure;

    public IngredientsAndMeasuresDTO(String ingredient, String measure) {
        this.ingredient = ingredient;
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }
}
