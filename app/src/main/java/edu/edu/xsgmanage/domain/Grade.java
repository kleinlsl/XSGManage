package edu.edu.xsgmanage.domain;

public class Grade {
    private String model;
    private int pk;
    private Fields fields;
    private Course course_info;
    private User user_info;

    public Course getCourse_info() {
        return course_info;
    }

    public void setCourse_info(Course course_info) {
        this.course_info = course_info;
    }

    public User getUser_info() {
        return user_info;
    }

    public void setUser_info(User user_info) {
        this.user_info = user_info;
    }

    public static class Fields {
        private String add_time;
        private String user_name;
        private String course_id;
        private String grade_value;
        private String grade_complain;
        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
        public String getAdd_time() {
            return add_time;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }
        public String getUser_name() {
            return user_name;
        }

        public void setCourse_id(String course_id) {
            this.course_id = course_id;
        }
        public String getCourse_id() {
            return course_id;
        }

        public void setGrade_value(String grade_value) {
            this.grade_value = grade_value;
        }
        public String getGrade_value() {
            return grade_value;
        }

        public void setGrade_complain(String grade_complain) {
            this.grade_complain = grade_complain;
        }
        public String getGrade_complain() {
            return grade_complain;
        }
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getModel() {
        return model;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }
    public int getPk() {
        return pk;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }
    public Fields getFields() {
        return fields;
    }

}
