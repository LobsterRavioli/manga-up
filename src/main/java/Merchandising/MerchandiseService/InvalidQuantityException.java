package Merchandising.MerchandiseService;

public class InvalidQuantityException extends Exception{
    public InvalidQuantityException(){
        super("La quantità deve essere compresa tra 1 e 50");
    }
}
