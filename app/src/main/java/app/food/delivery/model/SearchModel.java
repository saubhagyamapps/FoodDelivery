package app.food.delivery.model;

public class SearchModel {
    String name;
    String image;
    String phone;
    String  id;
    String  food_name;
    String  images;
    String  price;

    public String getId() {
        return id;
    }

    public String getFood_name() {
        return food_name;
    }

    public String getImages() {
        return images;
    }

    public String getPrice() {
        return price;
    }

    public String getCategory_name() {
        return category_name;
    }

    String  category_name;

    public SearchModel() {
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getPhone() {
        return phone;
    }
}

