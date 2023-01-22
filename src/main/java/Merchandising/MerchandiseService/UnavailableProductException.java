package Merchandising.MerchandiseService.dao_layer.exceptions;

public class UnavailableProductException extends Exception{

    public UnavailableProductException(){
        super("Prodotto non esistente");
    }

}
