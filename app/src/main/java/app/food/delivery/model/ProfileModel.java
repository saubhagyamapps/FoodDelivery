package app.food.delivery.model;

public class ProfileModel {

    /**
     * status : 0
     * message : update successful
     * profile_image : http://192.168.1.200/food_deliveryapp/public/profile_image/1551426564.jpg
     */

    private String status;
    private String message;
    private String profile_image;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }
}
