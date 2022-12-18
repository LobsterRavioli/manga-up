package Merchandising;

import java.util.HashMap;

public class Magazzino {

    private String nome;
    private String descrizione;

    HashMap<Prodotto,Integer> prodotti;

    public Magazzino(String nome, String descrizione,HashMap<Prodotto,Integer> prodotti) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.prodotti=prodotti;
    }

    public HashMap<Prodotto, Integer> getProdotti() {
        return prodotti;
    }

    public void setProdotti(HashMap<Prodotto, Integer> prodotti) {
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
