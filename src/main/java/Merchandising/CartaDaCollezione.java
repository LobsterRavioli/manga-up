package Merchandising;

public class CartaDaCollezione extends Prodotto{

    private String formato;
    private String materiale;
    private String rarita;

    private static final int MAX_Q=0;//Constant

    public CartaDaCollezione(long id,String sku,String nome,String marchio_Produttore,double prezzo,double peso,double altezza,double larghezza,ProductState stato,String descrizione,String formato,String materiale, String rarita){
        super(id,sku,nome,marchio_Produttore,prezzo,peso,altezza,larghezza,stato,descrizione);
        this.formato=formato;
        this.materiale=materiale;
        this.rarita=rarita;
    }

    public int getMax_q(){
        return CartaDaCollezione.MAX_Q;
    }
    public String getMateriale() {
        return materiale;
    }

    public void setMateriale(String materiale) {
        this.materiale = materiale;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getRarita() {
        return rarita;
    }

    public void setRarita(String rarita) {
        this.rarita = rarita;
    }
}
