package Merchandising.MerchandiseService.beans;

import Order.DispatchService.beans.Collection;

public class CartaDaCollezione extends Product {

    private String formato;
    private String materiale;
    private String rarita;

    private static final int MAX_Q=0;//Constant

    public CartaDaCollezione(String name, String producer, String description, double price, double height, double length, double weight, ProductState state, Collection collection, String formato, String materiale, String rarita) {
        super(name, producer, description, price, height, length, weight, state, collection);
        this.formato = formato;
        this.materiale = materiale;
        this.rarita = rarita;
    }

    public int getMax_q(){
        return CartaDaCollezione.MAX_Q;
    }
    public String getMateriale() {
        return materiale;
    }

    public void setMateriale(String materiale) {
        this.materiale = materiale;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getRarita() {
        return rarita;
    }

    public void setRarita(String rarita) {
        this.rarita = rarita;
    }
}
