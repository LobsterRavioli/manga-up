package Merchandising.MerchandiseService;

public class Autore {

    private int id;
    private String nome;

    private RuoloAutore ruolo;

    public Autore(int id, String nome, RuoloAutore ruolo) {
        this.id = id;
        this.nome = nome;
        this.ruolo = ruolo;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public RuoloAutore getRuolo() {
        return ruolo;
    }

    public void setRuolo(RuoloAutore ruolo) {
        this.ruolo = ruolo;
    }
}
