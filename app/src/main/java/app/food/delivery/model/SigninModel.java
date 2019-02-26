package app.food.delivery.model;

import java.util.List;

public class SigninModel {

    /**
     * status : 0
     * messgae : login success
     * result : [{"username":"dummy45","email":"dummy45@gmail.com","mobile":"00000000","firebase_id":"5","device_id":"7"}]
     */

    private String status;
    private String messgae;
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
         * username : dummy45
         * email : dummy45@gmail.com
         * mobile : 00000000
         * firebase_id : 5
         * device_id : 7
         */

        private String username;
        private String email;
        private String mobile;
        private String firebase_id;
        private String device_id;

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
