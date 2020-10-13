package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Products {
    private SimpleStringProperty ProductName;
    private SimpleIntegerProperty Price;
    private SimpleStringProperty StoreName;

    public Products(String storename, String productName, int price) {
        this.ProductName = new SimpleStringProperty(productName);
        this.Price = new SimpleIntegerProperty(price);
        this.StoreName = new SimpleStringProperty(storename);
    }

    public String getProductName() {
        return ProductName.get();
    }

    public void setProductName(String productName) {
        ProductName = new SimpleStringProperty(productName);
    }

    public int getPrice() {
        return Price.get();
    }

    public void setPrice(int price) {
        Price = new SimpleIntegerProperty(price);
    }

    public String getStoreName() {
        return StoreName.get();
    }

    public void setStoreName(String storeName) {
        StoreName = new SimpleStringProperty(storeName);
    }
}