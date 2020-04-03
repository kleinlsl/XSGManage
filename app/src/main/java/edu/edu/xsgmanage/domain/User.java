package edu.edu.xsgmanage.domain;

public class User {
    public String model;
    public String pk;
    public Fields fields;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

    public static class Fields{
        public String add_time;
        public String user_pass;
        public String user_id;
        public String message_name;
        public String message_sex;
        public Integer message_age;
        public String message_phone;
        public String message_image;

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getUser_passs() {
            return user_pass;
        }

        public void setUser_passs(String user_passs) {
            this.user_pass = user_passs;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getMessage_name() {
            return message_name;
        }

        public void setMessage_name(String message_name) {
            this.message_name = message_name;
        }

        public String getMessage_sex() {
            return message_sex;
        }

        public void setMessage_sex(String message_sex) {
            this.message_sex = message_sex;
        }

        public Integer getMessage_age() {
            return message_age;
        }

        public void setMessage_age(Integer message_age) {
            this.message_age = message_age;
        }

        public String getMessage_phone() {
            return message_phone;
        }

        public void setMessage_phone(String message_phone) {
            this.message_phone = message_phone;
        }

        public String getMessage_image() {
            return message_image;
        }

        public void setMessage_image(String message_image) {
            this.message_image = message_image;
        }
    }
}
