package sd.dtos;


public class ExtractedRecipeDTO {

    private  int id;

        private String title;

        private String image;

    public ExtractedRecipeDTO(String title, String image) {
        this.title = title;
        this.image = image;
    }

    public ExtractedRecipeDTO(int id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    public ExtractedRecipeDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


}
