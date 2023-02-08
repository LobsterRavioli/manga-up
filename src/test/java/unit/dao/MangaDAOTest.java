package unit.dao;

import Merchandising.MerchandiseService.*;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.oracle.OracleDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import javax.sql.DataSource;

import java.sql.Date;
import java.util.ArrayList;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MangaDAOTest {


    private static IDatabaseTester tester;

    private MangaDAO m;

    @BeforeAll
    void setUpAll() throws ClassNotFoundException {
        tester = new JdbcDatabaseTester(org.h2.Driver.class.getName(),
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:schema_db/schema.sql'",
                "prova",
                ""

        );

        // Refresh permette di svuotare la cache dopo un modifica con setDataSet
        // DeleteAll ci svuota il DB mantenendo lo schema
        tester.setSetUpOperation(DatabaseOperation.REFRESH);
        tester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        try{
            tester.getConnection().getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new OracleDataTypeFactory());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private static void refreshDataSet(String filename) throws Exception {
        IDataSet initialState = new FlatXmlDataSetBuilder()
                .build(MangaDAOTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Prepara lo stato iniziale di default
        refreshDataSet("Merchandising/manga_dao/init.xml");
        DataSource ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        m = new MangaDAO(ds);
    }


    @AfterEach
    void tearDown() throws Exception {
        tester.onTearDown();
    }

    @ParameterizedTest(name = "{index} - {0} (parametri: {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10}, {11}, {12}, {13}, {14}, {15}, {16}, {17}, {18}, {19})")
    @MethodSource("createTestFailProvider")
    void createTestFailInvalidInput(String testName, String isbn, String publisher, String binding, String language, String volume, int pages, Date exitDate, String name, String description, double price, double height, double length, double weight, int quantity, String interior, String imagePath, Collection collection, ProductState state, String storyMaker, Genre genre) throws Exception {
        Manga manga = new Manga(isbn,publisher,binding,language,volume,pages,exitDate,0,name,description,price,height,length,weight,quantity,interior,imagePath,collection,state,storyMaker,genre);
        String message = Assert.assertThrows(Exception.class, () ->m.create(manga)).getMessage();
        System.out.println(message);

    }

    private static Stream<Arguments> createTestFailProvider() {
        /*nome prodotto: “Sanctuary 3”
        publisher: Star Comics
        prezzo: 25.99
        peso:  3
        dimensioni: (altezza: 3, larghezza:5)
        stato: “NEW”
        descrizione: “vuoto”
        ISBN: 9997546123412
        rilegatura: “bordatura”
        volume: “VOL.5”
        data di uscita: 2001-03-05
        numero di pagine: 287
        quantità: 3
        interni: 6
        lingua: “ITA”
        collezione: *presente nel db*
        genere: *presente nel db*
        storyMaker: “Akira Toriyama”*/

        return Stream.of(

                //Controllo ISBN
                Arguments.of("Test case creazione prodotto fallita ISBN non valido", null,"Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita ISBN non valido", "9997546123","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo Editore
                Arguments.of("Test case creazione prodotto fallita Editore non valido", "9997546123412",null,"bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita Editore non valido", "9997546123412","","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita Editore non valido", "9997546123412","Lorem ipsum dolor sit amet, consectetur sodales sed","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo Rilegatura
                Arguments.of("Test case creazione prodotto fallita Rilegatura non valida", "9997546123412","Star Comics",null,"ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita Rilegatura non valida", "9997546123412","Star Comics","","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita Rilegatura non valida", "9997546123412","Star Comics","Lorem ipsum dolor sit amet, consectetur sodales sed","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo lingua
                Arguments.of("Test case creazione prodotto fallita lingua non valida", "9997546123412","Star Comics","bordatura",null,"VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita lingua non valida", "9997546123412","Star Comics","bordatura","","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita lingua non valida", "9997546123412","Star Comics","bordatura","Lorem ipsum dolor sit amet, consectetur sodales sed","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo volume
                Arguments.of("Test case creazione prodotto fallita volume non valido", "9997546123412","Star Comics","bordatura","ITA",null,287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita volume non valido", "9997546123412","Star Comics","bordatura","ITA","",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita volume non valido", "9997546123412","Star Comics","bordatura","ITA","Lorem ipsum dolor sit amet, consectetur sodales sed",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo Numero di pagine
                Arguments.of("Test case creazione prodotto fallita Numero di pagine non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",0,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo Data
                Arguments.of("Test case creazione prodotto fallita Data Non valida", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,null,"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita Data non valida", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2025-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo Nome
                Arguments.of("Test case creazione prodotto fallita Nome non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),null,"",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita Nome non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita Nome non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Lorem ipsum dolor sit amet, consectetur sodales sed","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita Nome non valido ", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo Prezzo
                Arguments.of("Test case creazione prodotto fallita Prezzo non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",0,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo Altezza
                Arguments.of("Test case creazione prodotto fallita Altezza non valida", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,0,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo Lunghezza
                Arguments.of("Test case creazione prodotto fallita Lunghezza non valida", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,0,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo Peso
                Arguments.of("Test case creazione prodotto fallita Peso non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,0,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo Quantità
                Arguments.of("Test case creazione prodotto fallita Quantità non valida", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,0,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo Interni
                Arguments.of("Test case creazione prodotto fallita Interni non validi", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,null,"",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita Interni non validi", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita Interni non validi", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"Lorem ipsum dolor sit amet, consectetur sodales sed","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo collezione
                Arguments.of("Test case creazione prodotto fallita collezione non valida", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",null,ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita collezione non valida", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection(null),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita collezione non valida", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection(""),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita collezione non valida", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection("Me"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo stato
                Arguments.of("Test case creazione prodotto fallita stato non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),null,"Akira Toriyama",new Genre("Josei")),
                //Controllo StoryMaker
                Arguments.of("Test case creazione prodotto fallita storymaker non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,null,new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita storymaker non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita storymaker non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Lorem ipsum dolor sit amet, consectetur sodales sed",new Genre("Josei")),
                //Controllo genere
                Arguments.of("Test case creazione prodotto fallita genere non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",null),
                Arguments.of("Test case creazione prodotto fallita genere non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre(null)),
                Arguments.of("Test case creazione prodotto fallita genere non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("")),
                Arguments.of("Test case creazione prodotto fallita genere non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Jo")),
                //Controllo Descrizione
                Arguments.of("Test case creazione prodotto fallita descrizione non valida", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary 5","Lorem ipsum dolor sit amet, consectetur sodales sedLorem ipsum dolor sit amet, consectetur sodales sedLorem ipsum dolor sit amet, consectetur sodales sedLorem ipsum dolor sit amet, consectetur sodales sedLorem ipsum dolor sit amet, consectetur sodales sedLorem ipsum dolor sit amet, consectetur sodales sed",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Jo"))

                );
    }

    @Test
    void createTestPass() throws Exception {
        Manga manga = new Manga("9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),10,"Sanctuary 2","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei"));
        try{
            m.create(manga);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
                .build(MangaDAOTest.class.getClassLoader().getResourceAsStream("Merchandising/manga_dao/create_manga.xml"));
        String[] ignoreCol = new String[1];
        ITable expectedTable = expectedDataSet.getTable("MANGA");
        ITable actualTable = tester.getConnection().createDataSet().getTable("MANGA");
        Assert.assertTrue(actualTable.getRowCount()==3);
    }



    @Test
    void deleteNotPassed() {
        Manga manga = new Manga(15);
        System.out.println(Assert.assertThrows(Exception.class, () ->m.delete(manga.getId())).getMessage());
    }


    @Test
    void deletePassed() throws Exception{
        Manga manga = new Manga(20);
        m.delete(manga.getId());
        ITable actualTable = tester.getConnection().createDataSet().getTable("MANGA");
        Assert.assertTrue(actualTable.getRowCount()==1);
    }

    @Test
    void retrieveByIdNotNull() {
        Manga manga = new Manga(20);
        Assert.assertNotNull(m.retrieveById(manga.getId()));
    }

    @Test
    void retrieveByIdNull(){
        Manga manga = new Manga(15);
        Assert.assertNull(m.retrieveById(manga.getId()));
    }

    @Test
    void filterForUsers() {
    }

    @Test
    void retrieveAllExistingElements() throws Exception {
        ArrayList<Manga> lista = m.retrieveAll();
        Assert.assertTrue(lista.size()==2);
    }

    @Test
    void retrieveAllNotExistingElements() throws Exception{
        m.delete(20);
        System.out.println(Assert.assertThrows(Exception.class, () ->m.retrieveAll()).getMessage());
    }


    @Test
    void filterForEndUsers() {
    }

    @Test
    void updateQuantityInvalidQuantity() throws Exception {
        Assert.assertThrows(Exception.class, () ->m.updateQuantity(0,20));
    }

    @Test
    void updateQuantityInvalidId() {
        Assert.assertThrows(Exception.class, () ->m.updateQuantity(20,15));
    }

    @Test
    void updateQuantitySuccess() throws Exception{
        m.updateQuantity(15,20);
        ITable actualTable = tester.getConnection().createDataSet().getTable("MANGA");
        Assert.assertEquals((int) actualTable.getValue(1,"quantity"),18);
    }

    @Test
    void filterTestNotExistentCollection() throws Exception {
        Assert.assertThrows(Exception.class, () -> m.filterForEndUsers("Sanctuary 3","Meclasd"));
    }

    @Test
    void filterTestNoMatchingProducts() throws Exception {
        Assert.assertThrows(Exception.class, () -> m.filterForEndUsers("Sanctuar655","Mecha"));
    }

    @Test
    void filterTestMatchingProducts() throws Exception {
        Assert.assertTrue(m.filterForEndUsers("Sanctuary 3","Mecha").size()>=1);
    }

    @ParameterizedTest(name = "{index} - {0} (parametri: {1}, {2})")
    @MethodSource("filterTestUserSDefaultsProvider")
    void filterTestUserSDefaults(String testName, String name, String collection_name,float min_price,float max_price) throws Exception {
        Assert.assertEquals(m.filterForUsers(name,min_price,max_price,collection_name,true,true).size(),2);
    }

    private static Stream<Arguments> filterTestUserSDefaultsProvider() {

        return Stream.of(

                //Controllo nome
                Arguments.of("Test case filtra prodotto User... Restituisci tutti i prodotti", null,null,0,0),
                Arguments.of("Test case filtra prodotto User... Restituisci tutti i prodotti", "",null,0,0),
                Arguments.of("Test case filtra prodotto User... Restituisci tutti i prodotti", null,"",0,0),
                Arguments.of("Test case filtra prodotto User... Restituisci tutti i prodotti", "","",0,0)

        );
    }

    @Test
    void filterTestUserNotExistingCollection(){
        System.out.println(Assert.assertThrows(Exception.class, () -> m.filterForUsers("Sanctuary 3",0,100,"Meclasd",true,true)).getMessage());
    }

    @Test
    void filterTestUserNotValidRange(){
        System.out.println(Assert.assertThrows(Exception.class, () -> m.filterForUsers("Sanctuary 3",20,10,"Mecha",true,true)).getMessage());
    }

    @Test
    void filterTestUserFoundProducts() throws Exception{
        Assert.assertTrue( m.filterForUsers("Sanctuary 3",0,100,"Mecha",true,true).size()>=1);
    }

    @Test
    void filterTestUserNotFoundProducts() throws Exception{
        System.out.println(Assert.assertThrows(Exception.class,()-> m.filterForUsers("Sanctuardsasdd",0,100,"Mecha",true,true)).getMessage());
        System.out.println(Assert.assertThrows(Exception.class,()-> m.filterForUsers("Sanctuary 3",0,10,"Mecha",true,true)).getMessage());
    }


    @ParameterizedTest(name = "{index} - {0} (parametri: {1}, {2})")
    @MethodSource("filterTestCollectionOrNameNullProvider")
    void filterTestCollectionOrNameNull(String testName, String name, String collection_name) throws Exception {
        Assert.assertEquals(m.filterForEndUsers(name,collection_name).size(),2);
    }

    private static Stream<Arguments> filterTestCollectionOrNameNullProvider() {

        return Stream.of(

                //Controllo nome
                Arguments.of("Test case filtra prodotto End User... Restituisci tutti i prodotti", null,null),
                Arguments.of("Test case filtra prodotto End User... Restituisci tutti i prodotti", "",null),
                Arguments.of("Test case filtra prodotto End User... Restituisci tutti i prodotti", null,""),
                Arguments.of("Test case filtra prodotto End User... Restituisci tutti i prodotti", "","")

        );
    }
}