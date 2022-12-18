package Merchandising;

import java.sql.Date;

public class Manga extends Prodotto{

    private String isbn;
    private String story_maker;
    private String story_boarder;
    private String rilegatura;
    private String lingua;
    private String volume;
    private Date data_uscita;
    private int pagine;
    private String interni;

    private static final int MAX_Q=0;//CONSTANT

    public Manga(long id,String sku,String nome,String marchio_Produttore,double prezzo,double peso,double altezza,double larghezza,ProductState stato,String descrizione,String isbn,String story_maker,String story_boarder,String rilegatura,String lingua,String volume,Date data_uscita,int pagine,String interni){
        super(id,sku,nome,marchio_Produttore,prezzo,peso,altezza,larghezza,stato,descrizione);
        this.isbn=isbn;
        this.story_maker=story_maker;
        this.story_boarder=story_boarder;
        this.rilegatura=rilegatura;
        this.lingua=lingua;
        this.volume=volume;
        this.data_uscita=data_uscita;
        this.pagine=pagine;
        this.interni=interni;
    }

    public int getMaxQ(){
        return Manga.MAX_Q;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getStory_maker() {
        return story_maker;
    }

    public void setStory_maker(String story_maker) {
        this.story_maker = story_maker;
    }

    public String getStory_boarder() {
        return story_boarder;
    }

    public void setStory_boarder(String story_boarder) {
        this.story_boarder = story_boarder;
    }

    public String getRilegatura() {
        return rilegatura;
    }

    public void setRilegatura(String rilegatura) {
        this.rilegatura = rilegatura;
    }

    public String getLingua() {
        return lingua;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public Date getData_uscita() {
        return data_uscita;
    }

    public void setData_uscita(Date data_uscita) {
        this.data_uscita = data_uscita;
    }

    public int getPagine() {
        return pagine;
    }

    public void setPagine(int pagine) {
        this.pagine = pagine;
    }

    public String getInterni() {
        return interni;
    }

    public void setInterni(String interni) {
        this.interni = interni;
    }
}
