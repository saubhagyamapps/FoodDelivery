package app.food.delivery.model;

import java.util.List;

public class DetailFoodModel {


    /**
     * status : 0
     * message : Food Avalible
     * path : http://192.168.1.200/food_deliveryapp/public/food_images/
     * result : [{"id":23,"registermanagement_id":5,"food_name":"hadh","description":"lafhnasjnc","price":"23","images":"0227201915512680551.jpg,0227201915512680552.jpg,0227201915512680553.jpg"}]
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
         * id : 23
         * registermanagement_id : 5
         * food_name : hadh
         * description : lafhnasjnc
         * price : 23
         * images : 0227201915512680551.jpg,0227201915512680552.jpg,0227201915512680553.jpg
         */

        private int id;
        private int registermanagement_id;
        private String food_name;
        private String description;
        private String price;
        private String images;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRegistermanagement_id() {
            return registermanagement_id;
        }

        public void setRegistermanagement_id(int registermanagement_id) {
            this.registermanagement_id = registermanagement_id;
        }

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

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }
    }
}
