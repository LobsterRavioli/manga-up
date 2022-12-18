package Merchandising;

public class ActionFigures extends Prodotto{

    private String materiale;
    private boolean assemblaggio_necessario;

    private static final int MAX_Q=0;
    public ActionFigures(long id, String sku, String nome, String marchio_Produttore, double prezzo, double peso, double altezza, double larghezza, ProductState stato, String descrizione, String materiale, boolean assemblaggio_necessario) {
        super(id, sku, nome, marchio_Produttore, prezzo, peso, altezza, larghezza, stato, descrizione);
        this.materiale = materiale;
        this.assemblaggio_necessario = assemblaggio_necessario;
    }

    public int getMax_q() {
        return ActionFigures.MAX_Q;
    }

    public String getMateriale() {
        return materiale;
    }

    public void setMateriale(String materiale) {
        this.materiale = materiale;
    }

    public boolean isAssemblaggio_necessario() {
        return assemblaggio_necessario;
    }

    public void setAssemblaggio_necessario(boolean assemblaggio_necessario) {
        this.assemblaggio_necessario = assemblaggio_necessario;
    }
}
