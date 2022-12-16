package Account;

public class Indirizzo {

    private String paese;
    private String citta;
    private String via;
    private int n_civico;
    private String cap;

    public Indirizzo(String paese,String citta,String via,int n_civico,String cap){
        this.paese=paese;
        this.citta=citta;
        this.via=via;
        this.n_civico=n_civico;
        this.cap=cap;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getPaese() {
        return paese;
    }

    public void setPaese(String paese) {
        this.paese = paese;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public int getN_civico() {
        return n_civico;
    }

    public void setN_civico(int n_civico) {
        this.n_civico = n_civico;
    }
}
