package edu.edu.xsgmanage.domain;

public class Course {
    private String model;
    private String pk;
    private Fields fields;
    public static class Fields {
        private String add_time;
        private String course_name;
        private String semester;
        private String course_teacher;
        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
        public String getAdd_time() {
            return add_time;
        }

        public void setCourse_name(String course_name) {
            this.course_name = course_name;
        }
        public String getCourse_name() {
            return course_name;
        }

        public void setSemester(String semester) {
            this.semester = semester;
        }
        public String getSemester() {
            return semester;
        }

        public void setCourse_teacher(String course_teacher) {
            this.course_teacher = course_teacher;
        }
        public String getCourse_teacher() {
            return course_teacher;
        }

    }
    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getPk() {
        return pk;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

    public Fields getFields() {
        return fields;
    }

}

