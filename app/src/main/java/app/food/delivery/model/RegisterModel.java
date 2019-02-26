package app.food.delivery.model;

public class RegisterModel {


        /**
     * status : 0
     * message : signup successful
     * id : 11
     */

    private String status;
    private String message;
    private int id;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
