package Account;

import java.sql.Date;

public class CreditCard {

    private String number;
    private String cvc;
    private String nome;
    private String cognome;
    private Date data_scadenza;

    public CreditCard(String number,String cvc,String nome,String cognome,Date data_scadenza){
        this.number=number;
        this.cvc=cvc;
        this.nome=nome;
        this.cognome=cognome;
        this.data_scadenza=data_scadenza;
    }

    public Date getData_scadenza() {
        return data_scadenza;
    }

    public void setData_scadenza(Date data_scadenza) {
        this.data_scadenza = data_scadenza;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
}
