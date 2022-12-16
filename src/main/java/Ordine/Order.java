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
    private Hashtable<Prodotto,Integer> prodotti;
    private Date data_ordine;
    private double prezzo_totale;
    public Order(long id,Date data_spedizione,Date data_consegna,OrderState orderState){
        this.data_spedizione=data_spedizione;
        this.data_consegna=data_consegna;
        this.orderState=orderState;
        this.data_ordine= (Date) Date.from(Instant.now());
        this.prezzo_totale = getTotal();
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

    public void setData_ordine(Date data_ordine) {
        this.data_ordine = data_ordine;
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
