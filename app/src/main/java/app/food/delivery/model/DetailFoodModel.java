package app.food.delivery.model;

import java.util.List;

public class DetailFoodModel {


    /**
     * status : 0
     * message : Food Avalible
     * path : http://192.168.1.200/food_deliveryapp/public/food_images/
     * result : [{"food_id":45,"registermanagement_id":21,"food_name":"Bhel","description":"tasty","price":"120","quantity":1,"images":"0301201915514270411.jpg,0301201915514270412.jpg,0301201915514270413.jpeg","category_name":"italian"}]
     */

    private String status;
    private String message;
    private String path;
    private List<ResultBean> result;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * food_id : 45
         * registermanagement_id : 21
         * food_name : Bhel
         * description : tasty
         * price : 120
         * quantity : 1
         * images : 0301201915514270411.jpg,0301201915514270412.jpg,0301201915514270413.jpeg
         * category_name : italian
         */


        private String food_name;
        private String description;
        private String price;
        private int quantity;
        private String images;
        private String category_name;


        public String getFood_name() {
            return food_name;
        }

        public void setFood_name(String food_name) {
            this.food_name = food_name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }
    }
}
