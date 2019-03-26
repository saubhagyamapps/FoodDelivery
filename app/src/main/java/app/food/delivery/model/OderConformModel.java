package app.food.delivery.model;

public class OderConformModel {

    /**
     * data : {"title":"Order Recived.","is_background":false,"message":"You Recived an order.","image":""}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title : Order Recived.
         * is_background : false
         * message : You Recived an order.
         * image :
         */

        private String title;
        private boolean is_background;
        private String message;
        private String image;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isIs_background() {
            return is_background;
        }

        public void setIs_background(boolean is_background) {
            this.is_background = is_background;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
