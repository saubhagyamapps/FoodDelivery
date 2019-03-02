package app.food.delivery.model;

import java.util.List;

public class FoodListModel {


    /**
     * status : 0
     * TotalPages : 2
     * path : http://192.168.1.200/food_deliveryapp/public/food_images/
     * result : [{"food_id":45,"registermanagement_id":21,"food_name":"Bhel","description":"tasty","price":"120","images":"0301201915514270411.jpg,0301201915514270412.jpg,0301201915514270413.jpeg","category_name":"italian"},{"food_id":44,"registermanagement_id":22,"food_name":"Idli","description":"testy","price":"80","images":"0301201915514198161.jpg","category_name":"south indian"},{"food_id":43,"registermanagement_id":3,"food_name":"Masala Dosa","description":"food","price":"40","images":"0228201915513520471.jpg,0228201915513520472.jpg,0228201915513520473.jpg","category_name":"south indian"},{"food_id":42,"registermanagement_id":14,"food_name":"Manchurian","description":"food","price":"20","images":"0228201915513519801.jpg,0228201915513519802.jpg,0228201915513519803.jpg","category_name":"Chineez"},{"food_id":41,"registermanagement_id":15,"food_name":"Vada Pav","description":"food","price":"20","images":"0228201915513518551.jpg,0228201915513518552.jpg,0228201915513518553.jpg","category_name":"fastfood"},{"food_id":40,"registermanagement_id":13,"food_name":"veg kolhapuri","description":"food","price":"80","images":"0228201915513517841.JPG,0228201915513517842.jpg,0228201915513517843.jpg","category_name":"panjabi"},{"food_id":39,"registermanagement_id":12,"food_name":"Sandwich","description":"food","price":"150","images":"0228201915513517671.png,0228201915513517672.jpg,0228201915513517673.jpg","category_name":"fastfood"},{"food_id":38,"registermanagement_id":15,"food_name":"Pizza","description":"food","price":"150","images":"0228201915513515761.jpg,0228201915513515762.jpg,0228201915513515763.jpg","category_name":"italian"},{"food_id":37,"registermanagement_id":11,"food_name":"Pani Puri","description":"food","price":"50","images":"0228201915513514551.jpg,0228201915513514552.jpg,0228201915513514553.jpg","category_name":"fastfood"},{"food_id":36,"registermanagement_id":15,"food_name":"Noodles","description":"food","price":"50","images":"0228201915513513941.jpg,0228201915513513942.jpg,0228201915513513943.jpg","category_name":"Chineez"}]
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
         * food_id : 45
         * registermanagement_id : 21
         * food_name : Bhel
         * description : tasty
         * price : 120
         * images : 0301201915514270411.jpg,0301201915514270412.jpg,0301201915514270413.jpeg
         * category_name : italian
         */

        private int food_id;
        private int registermanagement_id;
        private String food_name;
        private String description;
        private String price;
        private String images;
        private String category_name;

        public int getFood_id() {
            return food_id;
        }

        public void setFood_id(int food_id) {
            this.food_id = food_id;
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

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }
    }
}
