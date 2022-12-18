package Merchandising;

import java.util.ArrayList;

public class Prodotto {
    private long id;
    private String sku;
    private String nome;
    private String marchio_Produttore;
    private double prezzo;
    private double peso;
    private double altezza;
    private double larghezza;
    private ProductState stato;
    private String descrizione;


    public Prodotto(long id,String sku,String nome,String marchio_Produttore,double prezzo,double peso,double altezza,double larghezza,ProductState stato,String descrizione) {
        this.id=id;
        this.sku=sku;
        this.nome=nome;
        this.marchio_Produttore=marchio_Produttore;
        this.prezzo=prezzo;
        this.peso=peso;
        this.altezza=altezza;
        this.larghezza=larghezza;
        this.stato=stato;
        this.descrizione=descrizione;
    }



    private ArrayList<Collezione> collezioni;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarchio_Produttore() {
        return marchio_Produttore;
    }

    public void setMarchio_Produttore(String marchio_Produttore) {
        this.marchio_Produttore = marchio_Produttore;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltezza() {
        return altezza;
    }

    public void setAltezza(double altezza) {
        this.altezza = altezza;
    }

    public double getLarghezza() {
        return larghezza;
    }

    public void setLarghezza(double larghezza) {
        this.larghezza = larghezza;
    }

    public ProductState getStato() {
        return stato;
    }

    public void setStato(ProductState stato) {
        this.stato = stato;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
