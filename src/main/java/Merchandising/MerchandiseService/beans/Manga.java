package Merchandising.MerchandiseService.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Manga{


    public Manga(String isbn, String storyMaker, String binding, String cartoonist, String language, int volume, int pages, Date exitDate, List<String> type, int id, String name, String producer, String description, double price, double height, double length, double weight, ArrayList<Collection> collections, Product.ProductState state) {
        this.isbn = isbn;
        this.storyMaker = storyMaker;
        this.binding = binding;
        this.cartoonist = cartoonist;
        this.language = language;
        this.volume = volume;
        this.pages = pages;
        this.exitDate = exitDate;
        this.type = type;
        this.id = id;
        this.name = name;
        this.producer = producer;
        this.description = description;
        this.price = price;
        this.height = height;
        this.length = length;
        this.weight = weight;
        this.collections = collections;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public ArrayList<Collection> getCollections() {
        return collections;
    }

    public void setCollections(ArrayList<Collection> collections) {
        this.collections = collections;
    }

    public Product.ProductState getState() {
        return state;
    }

    public void setState(Product.ProductState state) {
        this.state = state;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getStoryMaker() {
        return storyMaker;
    }

    public void setStoryMaker(String storyMaker) {
        this.storyMaker = storyMaker;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getCartoonist() {
        return cartoonist;
    }

    public void setCartoonist(String cartoonist) {
        this.cartoonist = cartoonist;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Date getExitDate() {
        return exitDate;
    }

    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Manga)) return false;
        if (!super.equals(o)) return false;
        Manga manga = (Manga) o;
        return volume == manga.volume && pages == manga.pages && Objects.equals(isbn, manga.isbn) && Objects.equals(storyMaker, manga.storyMaker) && Objects.equals(binding, manga.binding) && Objects.equals(cartoonist, manga.cartoonist) && Objects.equals(language, manga.language) && Objects.equals(exitDate, manga.exitDate) && Objects.equals(type, manga.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isbn, storyMaker, binding, cartoonist, language, volume, pages, exitDate, type);
    }

    private String isbn, storyMaker, binding, cartoonist, language;
    private int volume, pages;
    private Date exitDate;
    private List<String> type;

    private int id;
    private String name, producer, description;
    private double price, height, length, weight;

    private ArrayList<Collection> collections;
    private Product.ProductState state;
}
