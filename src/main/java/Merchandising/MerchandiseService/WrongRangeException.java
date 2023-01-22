package Merchandising.MerchandiseService.dao_layer.exceptions;

public class WrongRangeException extends Exception{

    public WrongRangeException(){
        super("Range inserito errato");
    }
}
