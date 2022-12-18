package Ordine;

import Merchandising.Prodotto;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import static java.time.Instant.now;

public class Order {

    private long id;
    private Date data_spedizione;
    private Date data_consegna;
    private OrderState orderState;
    private final Hashtable<Prodotto,Integer> prodotti;
    private final Date data_ordine;
    private final double prezzo_totale;
    private String trackId;

    private final Corriere corriere;
    public Order(long id,Date data_spedizione,Date data_consegna,OrderState orderState,Hashtable<Prodotto,Integer>prodotti,Corriere corriere){
        this.data_spedizione=data_spedizione;
        this.data_consegna=data_consegna;
        this.orderState=orderState;
        this.data_ordine= (Date) Date.from(Instant.now());
        this.prezzo_totale = getTotal();
        this.corriere=corriere;
        this.prodotti=prodotti;
    }


    public void setTrackId(String trackId){
        this.trackId=trackId;
    }

    public String getTrackId() {
        return trackId;
    }

    public Corriere getCorriere() {
        return corriere;
    }

    public Hashtable<Prodotto, Integer> getProdotti() {
        return prodotti;
    }

    public double getPrezzo_totale() {
        return prezzo_totale;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getData_spedizione() {
        return data_spedizione;
    }

    public void setData_spedizione(Date data_spedizione) {
        this.data_spedizione = data_spedizione;
    }

    public Date getData_consegna() {
        return data_consegna;
    }

    public void setData_consegna(Date data_consegna) {
        this.data_consegna = data_consegna;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public Date getData_ordine() {
        return data_ordine;
    }

    public double getTotal(){
        double totale = 0;
        List<Prodotto> prodottoList = (List<Prodotto>) prodotti.keySet();
        for(Prodotto p:prodottoList){
            Integer i = prodotti.get(p);
            totale = totale + p.getPrezzo()*i;
        }

        return totale;
    }
}
