package Merchandising.MerchandiseService;

public class ExceededLengthException extends Exception{

    public ExceededLengthException(){
        super("lunghezza massima di superata");
    }
}
