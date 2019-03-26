package app.food.delivery.model;

import java.util.List;

public class SigninModel {


    /**
     * status : 0
     * messgae : login success
     * result : [{"username":"keshu","email":"keshuvodedara@gmail.com","mobile":"9624649521","firebase_id":"edpsPAjM0Ws:APA91bEMtUpN8zRTZXZpdNVMbIYilJhyb0ygzIVnUYC3EdaRvhEjBUqZFz6KZG_admX0XtnhIib5fALn4JjFns-GYa94NCW2Mgctf4yv1e14pUi2v3WJacCxnM5TtO2Y7SwjMg0GRkSC","device_id":"0f5ef9fc2b1342dd"}]
     */

    private String status;
    private String messgae;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String path;
    private List<ResultBean> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessgae() {
        return messgae;
    }

    public void setMessgae(String messgae) {
        this.messgae = messgae;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * username : keshu
         * email : keshuvodedara@gmail.com
         * mobile : 9624649521
         * firebase_id : edpsPAjM0Ws:APA91bEMtUpN8zRTZXZpdNVMbIYilJhyb0ygzIVnUYC3EdaRvhEjBUqZFz6KZG_admX0XtnhIib5fALn4JjFns-GYa94NCW2Mgctf4yv1e14pUi2v3WJacCxnM5TtO2Y7SwjMg0GRkSC
         * device_id : 0f5ef9fc2b1342dd
         */

        private String username;
        private String email;
        private String mobile;
        private String firebase_id;
        private String device_id;
        private String id;
        private String image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        private String gender;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        private String address;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getFirebase_id() {
            return firebase_id;
        }

        public void setFirebase_id(String firebase_id) {
            this.firebase_id = firebase_id;
        }

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }
    }
}
