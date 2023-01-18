package Merchandising.MerchandiseService.beans;

import com.sun.tools.javac.jvm.Gen;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class Manga{


    public Manga(String isbn, String brand, String binding, String language, String volume, int pages, Date exitDate, int id, String name, String description, double price, double height, double length, double weight, String collections,int quantity, Product.ProductState state,String interior,String imagePath) {
        this.isbn = isbn;
        this.brand = brand;
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
        this.collections = collections;
        this.quantity = quantity;
        this.state = state;
        this.interior = interior;
        this.imagePath = imagePath;
    }



    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getInterior() {
        return interior;
    }

    public void setInterior(String interior) {
        this.interior = interior;
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

    public String getCollections() {
        return collections;
    }

    public void setCollections(String collections) {
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Manga)) return false;
        if (!super.equals(o)) return false;
        Manga manga = (Manga) o;
        return volume == manga.volume && pages == manga.pages && Objects.equals(isbn, manga.isbn) && Objects.equals(binding, manga.binding) && Objects.equals(language, manga.language) && Objects.equals(exitDate, manga.exitDate);
    }

    public void setAutore(ArrayList<Autore> a){
        autore=a;
    }

    public void setGenre(ArrayList<Genre> g){
        genre=g;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isbn, binding, language, volume, pages, exitDate);
    }

    private String isbn, brand, binding, language,volume;
    private int pages;
    private Date exitDate;
    private int id;
    private String name, description;
    private double price, height, length, weight;

    private int quantity;

    private String interior;

    private String imagePath;

    private String collections;
    private Product.ProductState state;

    private ArrayList<Autore> autore;

    private ArrayList<Genre> genre;
}
