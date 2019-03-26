package app.food.delivery.model;

import java.util.List;

public class CartViewModel {

    /**
     * status : 0
     * path : http://192.168.1.200/food_deliveryapp/public/food_images/
     * result : [{"food_name":"Masala Dosa","description":"food","price":"40","images":"0228201915513520471.jpg,0228201915513520472.jpg,0228201915513520473.jpg","quantity":1,"user_id":15},{"food_name":"Idli","description":"testy","price":"80","images":"0301201915514198161.jpg","quantity":1,"user_id":15}]
     */

    private String status;
    private String path;
    private List<ResultBean> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
         * food_name : Masala Dosa
         * description : food
         * price : 40
         * images : 0228201915513520471.jpg,0228201915513520472.jpg,0228201915513520473.jpg
         * quantity : 1
         * user_id : 15
         */

        private String food_name;
        private String category_name;
        private String description;
        private String price;
        private String images;
        private String quantity;
        private int user_id;
        private String total_price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        private String id;

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
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

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }
}
