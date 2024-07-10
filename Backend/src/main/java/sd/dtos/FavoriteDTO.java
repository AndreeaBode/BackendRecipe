package sd.dtos;

public class FavoriteDTO {
       private int recipeId;
        private String title;
        private String image;
        private String name;

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FavoriteDTO{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}


