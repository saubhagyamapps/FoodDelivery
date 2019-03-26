package app.food.delivery.model;

public class AddToCartModel {


    /**
     * status : 0
     * message : successful add to cart
     */

    private String status;
    private String Total;
    private String message;

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

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
}
