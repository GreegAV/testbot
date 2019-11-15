package com.obrttestbot;

import java.util.Objects;

public class Product {
    private String productName;
    private String productMeasure;
    private double productPrice;

    @Override
    public String toString() {
        return productName + " (" + productMeasure + ") " + productPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.productPrice, productPrice) == 0 &&
                Objects.equals(productName, product.productName) &&
                Objects.equals(productMeasure, product.productMeasure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, productMeasure, productPrice);
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductMeasure() {
        return productMeasure;
    }

    public void setProductMeasure(String productMeasure) {
        this.productMeasure = productMeasure;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public Product(String productName, String productMeasure, double productPrice) {
        this.productName = productName;
        this.productMeasure = productMeasure;
        this.productPrice = productPrice;
    }
}
