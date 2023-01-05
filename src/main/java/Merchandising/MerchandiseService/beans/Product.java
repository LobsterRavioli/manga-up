package Merchandising.MerchandiseService.beans;

import Order.DispatchService.beans.Collection;

import java.util.Objects;

public abstract class Product {

    public Product()
    {
        id += 1;
    }

    public Product(String name, String producer, String description, double price, double height, double length,
                   double weight, ProductState state, Collection collection)
    {
        id += 1;

        this.name = name;
        this.producer = producer;
        this.description = description;
        this.price = price;
        this.height = height;
        this.length = length;
        this.weight = weight;
        this.state = state;
        this.collection = collection;
    }

    public static int getId() {
        return id;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
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
        return Double.compare(product.price, price) == 0 && Double.compare(product.height, height) == 0 && Double.compare(product.length, length) == 0 && Double.compare(product.weight, weight) == 0 && Objects.equals(collection, product.collection) && Objects.equals(name, product.name) && Objects.equals(producer, product.producer) && Objects.equals(description, product.description) && state == product.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(collection, name, producer, description, price, height, length, weight, state);
    }

    public enum ProductState
    {
        NEW,
        USED
    }

    private static int id = 0;
    private Collection collection;
    private String name, producer, description;
    private double price, height, length, weight;
    private ProductState state;
}
