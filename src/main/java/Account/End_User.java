package Account;

import Merchandising.Prodotto;
import Ordine.Order;

import java.sql.Date;
import java.util.ArrayList;

public class End_User {
    private String email;
    private String nome;
    private String cognome;
    private String password;
    private String telefono;
    private Date data_nascita;
    private Indirizzo indirizzo;
    private Order order;

    private ArrayList<Prodotto> carrello;

    private CreditCard creditCard;

    public End_User(String email,String nome,String cognome,String password,String telefono,Date data_nascita,Indirizzo indirizzo,CreditCard creditCard,Order order,ArrayList<Prodotto> carrello){
        this.email=email;
        this.nome=nome;
        this.cognome=cognome;
        this.password=password;
        this.telefono=telefono;
        this.data_nascita=data_nascita;
        this.indirizzo=indirizzo;
        this.creditCard=creditCard;
        this.order=order;
        this.carrello=carrello;
    }

    public void addPtoUser(Prodotto p){
        carrello.add(p);
    }

    public void removePFromUser(Prodotto p){
        carrello.remove(p);
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getData_nascita() {
        return data_nascita;
    }

    public void setData_nascita(Date data_nascita) {
        this.data_nascita = data_nascita;
    }

    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(Indirizzo indirizzo) {
        this.indirizzo = indirizzo;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
}
