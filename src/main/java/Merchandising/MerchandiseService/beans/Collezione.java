package Merchandising.MerchandiseService.beans;

import java.util.ArrayList;

public class Collezione {

    private String titolo;
    private String descrizione;

    private ArrayList<Product> prodotti;

    public Collezione(String titolo,String descrizione){
        this.titolo = titolo;
        this.descrizione=descrizione;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
