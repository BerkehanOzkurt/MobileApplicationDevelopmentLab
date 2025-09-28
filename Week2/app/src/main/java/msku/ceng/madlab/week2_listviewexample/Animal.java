package msku.ceng.madlab.week2_listviewexample;

public class Animal {
    private String type;
    private Integer picId;

    public Animal(String type, Integer picId) {
        this.type = type;
        this.picId = picId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }
}
