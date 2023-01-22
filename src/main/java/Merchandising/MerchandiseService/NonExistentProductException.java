package Merchandising.MerchandiseService.dao_layer.exceptions;

public class NonExistentProductException extends Exception{

    public NonExistentProductException(){
        super("Prodotto inserito per la modifica non è esistente");
    }

}
