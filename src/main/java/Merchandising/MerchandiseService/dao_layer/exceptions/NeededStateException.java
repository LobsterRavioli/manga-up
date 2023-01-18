package Merchandising.MerchandiseService.dao_layer.exceptions;

public class NeededStateException extends Exception{

    public NeededStateException(){
        super("Bisogna inserire uno stato");
    }

}
