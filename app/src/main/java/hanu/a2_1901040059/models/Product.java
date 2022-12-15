package hanu.a2_1901040059.models;

public class Product {
    public Product() {
    }
    private int id;
    private String thumbnail;
    private int price;
    private int quantity;
    private String name;

    public Product(int id, String imgUrl, String name, int price) {
        this.id = id;
        this.thumbnail = imgUrl;
        this.name = name;
        this.price = price;
    }

    public Product(int id, String imgUrl, String name, int price, int quantity) {
        this.thumbnail = imgUrl;
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }





    public String getImgUrl() {
        return thumbnail;
    }

    public void setImgUrl(String imgUrl) {
        this.thumbnail = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
