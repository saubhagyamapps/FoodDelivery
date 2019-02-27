package app.food.delivery.model;

import java.util.List;

public class FoodListModel {

    /**
     * status : 0
     * TotalPages : 2
     * path : http://192.168.1.200/food_deliveryapp/public/food_images/
     * result : [{"id":1,"registermanagement_id":2,"food_name":"vadapav","description":"testy yummy","price":"30","images":"1551243636-vada-pav-23183573.jpg"},{"id":2,"registermanagement_id":4,"food_name":"pavbhaji","description":"testy","price":"100","images":"vada-pav-23183573.jpg"},{"id":3,"registermanagement_id":5,"food_name":"dabeli","description":"testy","price":"30","images":"Desert.jpg"},{"id":4,"registermanagement_id":5,"food_name":"dabeli","description":"testy","price":"30","images":"Desert.jpg"},{"id":5,"registermanagement_id":5,"food_name":"dabeli","description":"testy","price":"30","images":"E:\\xampp\\tmp\\php755D.tmp"},{"id":6,"registermanagement_id":5,"food_name":"dabeli","description":"testy","price":"30","images":"Lighthouse.jpg"},{"id":7,"registermanagement_id":5,"food_name":"dabeli","description":"testy","price":"30","images":"Jellyfish.jpg"},{"id":8,"registermanagement_id":5,"food_name":"dabeli","description":"testy","price":"30","images":"Koala.jpg"},{"id":9,"registermanagement_id":5,"food_name":"dabeli","description":"testy","price":"30","images":"Lighthouse.jpg"},{"id":10,"registermanagement_id":5,"food_name":"dabeli","description":"testy","price":"30","images":"Jellyfish.jpg"}]
     */

    private String status;
    private int TotalPages;
    private String path;
    private List<ResultBean> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalPages() {
        return TotalPages;
    }

    public void setTotalPages(int TotalPages) {
        this.TotalPages = TotalPages;
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
         * id : 1
         * registermanagement_id : 2
         * food_name : vadapav
         * description : testy yummy
         * price : 30
         * images : 1551243636-vada-pav-23183573.jpg
         */

        private String id;
        private int registermanagement_id;
        private String food_name;
        private String description;
        private String price;
        private String images;

        public String getId() {
            return id;
        }

        public void setId(String id) {
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
