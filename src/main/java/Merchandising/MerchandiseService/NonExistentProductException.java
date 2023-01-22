package Merchandising.MerchandiseService;

public class NonExistentProductException extends Exception{

    public NonExistentProductException(){
        super("Prodotto inserito per la modifica non è esistente");
    }

}
