package app.food.delivery.model;

import java.util.List;

public class FoodCategoryModel {

    /**
     * status : 0
     * result : [{"id":1,"category_name":"panjabi"},{"id":2,"category_name":"Gujarati"},{"id":3,"category_name":"Chineez"},{"id":4,"category_name":"south indian"},{"id":5,"category_name":"italian"}]
     */

    private String status;
    private List<ResultBean> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
         * category_name : panjabi
         */

        private int id;
        private String category_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }
    }
}
