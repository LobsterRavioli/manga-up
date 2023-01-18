package Merchandising.MerchandiseService.dao_layer.exceptions;

public class ExceededLengthException extends Exception{

    public ExceededLengthException(){
        super("lunghezza massima di superata");
    }
}
