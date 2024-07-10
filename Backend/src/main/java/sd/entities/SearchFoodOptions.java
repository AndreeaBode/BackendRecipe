package sd.entities;

public class SearchFoodOptions {
    private String query;
    private String diet;
    private String intolerances;
    private String includeIngredients;
    private String excludeIngredients;
    private int maxReadyTime;
    private int minCarbs;
    private int maxCarbs;
    private int minProtein;
    private int maxProtein;
    private int minCalories;
    private int maxCalories;
    private int minFat;
    private int maxFat;
    private int minCholesterol;
    private int maxCholesterol;
    private int minSugar;
    private int maxSugar;

    public SearchFoodOptions() {

    }

    public SearchFoodOptions(String query, String diet, String intolerances, String includeIngredients, String excludeIngredients, int maxReadyTime, int minCarbs, int maxCarbs, int minProtein, int maxProtein, int minCalories, int maxCalories, int minFat, int maxFat, int minCholesterol, int maxCholesterol, int minSugar, int maxSugar) {
        this.query = query;
        this.diet = diet;
        this.intolerances = intolerances;
        this.includeIngredients = includeIngredients;
        this.excludeIngredients = excludeIngredients;
        this.maxReadyTime = maxReadyTime;
        this.minCarbs = minCarbs;
        this.maxCarbs = maxCarbs;
        this.minProtein = minProtein;
        this.maxProtein = maxProtein;
        this.minCalories = minCalories;
        this.maxCalories = maxCalories;
        this.minFat = minFat;
        this.maxFat = maxFat;
        this.minCholesterol = minCholesterol;
        this.maxCholesterol = maxCholesterol;
        this.minSugar = minSugar;
        this.maxSugar = maxSugar;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getIntolerances() {
        return intolerances;
    }

    public void setIntolerances(String intolerances) {
        this.intolerances = intolerances;
    }

    public String getIncludeIngredients() {
        return includeIngredients;
    }

    public void setIncludeIngredients(String includeIngredients) {
        this.includeIngredients = includeIngredients;
    }

    public String getExcludeIngredients() {
        return excludeIngredients;
    }

    public void setExcludeIngredients(String excludeIngredients) {
        this.excludeIngredients = excludeIngredients;
    }

    public int getMaxReadyTime() {
        return maxReadyTime;
    }

    public void setMaxReadyTime(int maxReadyTime) {
        this.maxReadyTime = maxReadyTime;
    }

    public int getMinCarbs() {
        return minCarbs;
    }

    public void setMinCarbs(int minCarbs) {
        this.minCarbs = minCarbs;
    }

    public int getMaxCarbs() {
        return maxCarbs;
    }

    public void setMaxCarbs(int maxCarbs) {
        this.maxCarbs = maxCarbs;
    }

    public int getMinProtein() {
        return minProtein;
    }

    public void setMinProtein(int minProtein) {
        this.minProtein = minProtein;
    }

    public int getMaxProtein() {
        return maxProtein;
    }

    public void setMaxProtein(int maxProtein) {
        this.maxProtein = maxProtein;
    }

    public int getMinCalories() {
        return minCalories;
    }

    public void setMinCalories(int minCalories) {
        this.minCalories = minCalories;
    }

    public int getMaxCalories() {
        return maxCalories;
    }

    public void setMaxCalories(int maxCalories) {
        this.maxCalories = maxCalories;
    }

    public int getMinFat() {
        return minFat;
    }

    public void setMinFat(int minFat) {
        this.minFat = minFat;
    }

    public int getMaxFat() {
        return maxFat;
    }

    public void setMaxFat(int maxFat) {
        this.maxFat = maxFat;
    }

    public int getMinCholesterol() {
        return minCholesterol;
    }

    public void setMinCholesterol(int minCholesterol) {
        this.minCholesterol = minCholesterol;
    }

    public int getMaxCholesterol() {
        return maxCholesterol;
    }

    public void setMaxCholesterol(int maxCholesterol) {
        this.maxCholesterol = maxCholesterol;
    }

    public int getMinSugar() {
        return minSugar;
    }

    public void setMinSugar(int minSugar) {
        this.minSugar = minSugar;
    }

    public int getMaxSugar() {
        return maxSugar;
    }

    public void setMaxSugar(int maxSugar) {
        this.maxSugar = maxSugar;
    }
}
