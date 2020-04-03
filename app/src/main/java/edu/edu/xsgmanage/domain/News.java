package edu.edu.xsgmanage.domain;

public class News {

    private String model;
    private int pk;
    private Fields fields;
    public static class Fields {
        private String add_time;
        private String sender;
        private String receive;
        private String message;
        private String read;
        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
        public String getAdd_time() {
            return add_time;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }
        public String getSender() {
            return sender;
        }

        public void setReceive(String receive) {
            this.receive = receive;
        }
        public String getReceive() {
            return receive;
        }

        public void setMessage(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }

        public void setRead(String read) {
            this.read = read;
        }
        public String getRead() {
            return read;
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
