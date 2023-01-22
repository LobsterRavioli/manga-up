package Merchandising.MerchandiseService;

public class UnavailableProductException extends Exception{

    public UnavailableProductException(){
        super("Prodotto non esistente");
    }

}
