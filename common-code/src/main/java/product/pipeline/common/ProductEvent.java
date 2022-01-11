package product.pipeline.common;

import java.util.StringJoiner;

public class ProductEvent {
    public String sku;
    public String name;
    public int quantity;
    public String brand;
    public int clearance;

    public ProductEvent(String sku, String name, int quantity, String brand, int clearance) {
        this.sku = sku;
        this.name = name;
        this.quantity = quantity;
        this.brand = brand;
        this.clearance = clearance;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getClearance() {
        return clearance;
    }

    public void setClearance(int clearance) {
        this.clearance = clearance;
    }

    public String toString() {
        return new StringJoiner(", ", ProductEvent.class.getSimpleName() + "[", "]")
                .add("sku='" + sku + "'")
                .add("name='" + name + "'")
                .add("quantity=" + quantity)
                .add("brand='" + brand + "'")
                .add("clearance=" + clearance)
                .toString();
    }
}


