package com.wzp.pet.po;

public class Goods {
    private String images;
    private Integer price;
    private String describe;

    public String toString() {
        return "Goods{" +
                "images='" + images + '\'' +
                ", price=" + price +
                ", describe='" + describe + '\'' +
                '}';
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }


}
