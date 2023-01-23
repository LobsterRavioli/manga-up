package Merchandising.MerchandiseService;

public class WrongRangeException extends Exception{

    public WrongRangeException(){
        super("Range inserito errato");
    }
}
