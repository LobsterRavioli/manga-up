package Merchandising.MerchandiseService.beans;

public class ActionFigures extends Product{

    private String materiale;
    private boolean assemblaggio_necessario;

    private static final int MAX_Q=0;

    public ActionFigures(String name, String producer, String description, double price, double height, double length, double weight, ProductState state, Collection collection, String materiale, boolean assemblaggio_necessario) {
        super(name, producer, description, price, height, length, weight, state, collection);
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
