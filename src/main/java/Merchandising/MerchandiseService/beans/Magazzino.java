package Merchandising.MerchandiseService.beans;

import java.util.HashMap;

public class Magazzino {

    private String nome;
    private String descrizione;

    HashMap<Product,Integer> prodotti;

    public Magazzino(String nome, String descrizione,HashMap<Product,Integer> prodotti) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.prodotti=prodotti;
    }

    public HashMap<Product, Integer> getProdotti() {
        return prodotti;
    }

    public void setProdotti(HashMap<Product, Integer> prodotti) {
        this.prodotti = prodotti;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
