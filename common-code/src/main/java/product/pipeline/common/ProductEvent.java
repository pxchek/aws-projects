package product.pipeline.common;

import java.util.StringJoiner;

public class ProductEvent {
    public String sku;
    public String name;
    public Integer quantity;
    public String brand;
    public Integer clearance;

    public ProductEvent() {
    }

    public ProductEvent(String sku, String name, Integer quantity, String brand, Integer clearance) {
        this.sku = sku;
        this.name = name;
        this.quantity = quantity;
        this.brand = brand;
        this.clearance = clearance;
    }

    @Override
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


