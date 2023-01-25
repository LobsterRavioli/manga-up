package Merchandising.MerchandiseService;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class Manga{


    public Manga(String isbn, String publisher, String binding, String language, String volume, int pages, Date exitDate, int id, String name, String description, double price, double height, double length, double weight, int quantity, String interior, String imagePath, Collection collection, ProductState state, String storyMaker, Genre genre) {
        this.isbn = isbn;
        this.publisher = publisher;
        this.binding = binding;
        this.language = language;
        this.volume = volume;
        this.pages = pages;
        this.exitDate = exitDate;
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.height = height;
        this.length = length;
        this.weight = weight;
        this.quantity = quantity;
        this.interior = interior;
        this.imagePath = imagePath;
        this.collection = collection;
        this.state = state;
        this.storyMaker = storyMaker;
        this.genre = genre;
    }

    public Manga(int id){
        this.id=id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getInterior() {
        return interior;
    }

    public void setInterior(String interior) {
        this.interior = interior;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public ProductState getState() {
        return state;
    }

    public void setState(ProductState state) {
        this.state = state;
    }

    public String getStoryMaker() {
        return storyMaker;
    }

    public void setStoryMaker(String storyMaker) {
        this.storyMaker = storyMaker;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre g){
        genre=g;
    }


    private String isbn, publisher, binding, language,volume;
    private int pages;
    private Date exitDate;
    private int id;
    private String name, description;
    private double price, height, length, weight;

    private int quantity;
    private String interior;

    private String imagePath;

    private Collection collection;
    private ProductState state;

    private String storyMaker;

    private Genre genre;
}

