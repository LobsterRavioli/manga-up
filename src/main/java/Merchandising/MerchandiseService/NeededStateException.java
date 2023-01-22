package Merchandising.MerchandiseService;

public class NeededStateException extends Exception{

    public NeededStateException(){
        super("Bisogna inserire uno stato");
    }

}
