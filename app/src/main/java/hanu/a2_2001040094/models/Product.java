package hanu.a2_2001040094.models;

public class Product {
    private int id;
    private String name;
    private String thumbnail;
    private String category;
    private int unitPrice;

    private int quantity;

    public Product(int id, String name, String thumbnail, String category, int unitPrice) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.category = category;
        this.unitPrice = unitPrice;
    }

    public Product(int id, String name, String thumbnail, String category, int unitPrice, int quantity) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.name = name;
        this.category = category;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }


}

