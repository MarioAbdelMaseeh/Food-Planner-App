package com.mario.mychef.models;

import java.util.List;

public class CategoriesResponse {

    private List<CategoriesDTO> categories;

    public List<CategoriesDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesDTO> categories) {
        this.categories = categories;
    }

    public static class CategoriesDTO {
        private String idCategory;
        private String strCategory;
        private String strCategoryThumb;
        private String strCategoryDescription;

        public String getIdCategory() {
            return idCategory;
        }

        public void setIdCategory(String idCategory) {
            this.idCategory = idCategory;
        }

        public String getStrCategory() {
            return strCategory;
        }

        public void setStrCategory(String strCategory) {
            this.strCategory = strCategory;
        }

        public String getStrCategoryThumb() {
            return strCategoryThumb;
        }

        public void setStrCategoryThumb(String strCategoryThumb) {
            this.strCategoryThumb = strCategoryThumb;
        }

        public String getStrCategoryDescription() {
            return strCategoryDescription;
        }

        public void setStrCategoryDescription(String strCategoryDescription) {
            this.strCategoryDescription = strCategoryDescription;
        }
    }
}
