package Merchandising.MerchandiseService.beans;

import java.util.ArrayList;
import java.util.Objects;

public class Product {

    public Product(String name, String producer, String description, double price, double height, double length,
                   double weight, ProductState state)
    {

        this.name = name;
        this.producer = producer;
        this.description = description;
        this.price = price;
        this.height = height;
        this.length = length;
        this.weight = weight;
        this.state = state;
    }

    public static int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public ProductState getState() {
        return state;
    }

    public void setState(ProductState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 && Double.compare(product.height, height) == 0 && Double.compare(product.length, length) == 0 && Double.compare(product.weight, weight) == 0 && Objects.equals(collections, product.collections) && Objects.equals(name, product.name) && Objects.equals(producer, product.producer) && Objects.equals(description, product.description) && state == product.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, producer, description, price, height, length, weight, state);
    }

    public enum ProductState
    {
        NEW,
        USED
    }

    private int id;
    private String name, producer, description;
    private double price, height, length, weight;

    private ArrayList<Collection> collections;
    private ProductState state;
}
