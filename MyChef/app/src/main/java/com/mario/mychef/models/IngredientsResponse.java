package com.mario.mychef.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientsResponse {
    @SerializedName("meals")
    private List<IngredientDTO> ingredientDTOList;

    public List<IngredientDTO> getIngredient() {
        return ingredientDTOList;
    }

    public void setIngredients(List<IngredientDTO> ingredientDTOList) {
        this.ingredientDTOList = ingredientDTOList;
    }

    public static class IngredientDTO {
        private String idIngredient;
        private String strIngredient;
        private String strDescription;
        private String strType;

        public String getIdIngredient() {
            return idIngredient;
        }

        public void setIdIngredient(String idIngredient) {
            this.idIngredient = idIngredient;
        }

        public String getStrIngredient() {
            return strIngredient;
        }

        public void setStrIngredient(String strIngredient) {
            this.strIngredient = strIngredient;
        }

        public String getStrDescription() {
            return strDescription;
        }

        public void setStrDescription(String strDescription) {
            this.strDescription = strDescription;
        }

        public String getStrType() {
            return strType;
        }

        public void setStrType(String strType) {
            this.strType = strType;
        }
    }
}
