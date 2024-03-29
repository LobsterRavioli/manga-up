package Merchandising.ProductsView;

import Merchandising.MerchandiseService.*;
import User.AccountService.EndUserDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.*;
import java.sql.Date;
import java.text.SimpleDateFormat;


@WebServlet(name = "processProductInsertion", value = "/ProductsView/processProductInsertion")
@MultipartConfig
public class processProductInsertion extends HttpServlet {

    private MangaDAO daoM;
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(daoM==null) {
            DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
            daoM = new MangaDAO(ds);
        }
        final String prodName = request.getParameter("nome");
        final String prodPublisher = request.getParameter("editore");
        final String prodPrice = request.getParameter("prezzo");
        final String prodWeight = request.getParameter("peso");
        final String prodHeight = request.getParameter("altezza");
        final String prodLength = request.getParameter("larghezza");
        final String prodState = request.getParameter("stato");
        ProductState ps = null;
        if(prodState.equals("NEW"))
            ps= ProductState.NEW;
        else ps= ProductState.USED;
        final String prodDescription = request.getParameter("descrizione");
        final String prodISBN = request.getParameter("isbn");
        final String prodBinding = request.getParameter("rilegatura");
        final String prodVolume = request.getParameter("volume");
        final String prodDataUscita = request.getParameter("data_uscita");

        if(prodDataUscita==null){
            request.setAttribute("error","La data di uscita non è stata specificata...errore");
            RequestDispatcher rD = this.getServletContext().getRequestDispatcher("/ProductsView/homepage.jsp");
            rD.forward(request,response);
            return;
        }
        final Date data = Date.valueOf(prodDataUscita);
        final String prodPageNumber = request.getParameter("numPagine");
        final String prodQuantity = request.getParameter("quantity");
        final String prodInterior = request.getParameter("interni");
        final String prodLanguage = request.getParameter("lingua");
        final String prodCollection = request.getParameter("collection");
        final String prodGenre = request.getParameter("genere");
        final String prodStoryMaker = request.getParameter("story_maker");
        // Create path components to save the file
        final Part filePart = request.getPart("immagine");
        //final String fileName = getFileName(filePart);
        OutputStream out = null;
        InputStream filecontent = null;
        //final PrintWriter writer = response.getWriter();

        if(prodName == null){
            request.setAttribute("error","Il nome del prodotto non è stato specificato...errore");
            RequestDispatcher rD = this.getServletContext().getRequestDispatcher("/ProductsView/homepage.jsp");
            rD.forward(request,response);
            return;
        }

        try {
            out = new FileOutputStream(new File("C:\\Users\\Francesco Monzillo\\Dropbox\\Il mio PC (LAPTOP-AMUDE4IL)\\Desktop\\Uni\\Corsi\\3° anno\\Primo Semestre\\Ingegneria del Software\\Progetto\\Implementation\\manga-up\\src\\main\\webapp\\images\\products" + File.separator
                    + prodName.trim()+".jpeg"));

            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            Manga m = new Manga(prodISBN,prodPublisher,prodBinding,prodLanguage,prodVolume,Integer.parseInt(prodPageNumber),(java.sql.Date)data,0,prodName,prodDescription,Float.parseFloat(prodPrice),Integer.parseInt(prodHeight),Integer.parseInt(prodLength),Integer.parseInt(prodWeight),Integer.parseInt(prodQuantity),prodInterior,prodName.trim()+".jpeg",new Collection(prodCollection),ps,prodStoryMaker,new Genre(prodGenre));
            try{
                daoM.create(m);
                while ((read = filecontent.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                System.out.println("New file " + prodName + " created at " + "C:\\Users\\Francesco Monzillo\\Dropbox\\Il mio PC (LAPTOP-AMUDE4IL)\\Desktop\\Uni\\Corsi\\3° anno\\Primo Semestre\\Ingegneria del Software\\Progetto\\Implementation\\manga-up\\src\\main\\webapp\\images\\products" + File.separator + prodName);
                request.setAttribute("success","prodotto inserito con successo");
                RequestDispatcher rD = this.getServletContext().getRequestDispatcher("/ProductsView/homepage.jsp");
                rD.forward(request,response);
                return;
            }catch (Exception e){
                System.out.println(e.getMessage());
                request.setAttribute("error",e.getMessage());
                RequestDispatcher rD = this.getServletContext().getRequestDispatcher("/ProductsView/homepage.jsp");
                rD.forward(request,response);
                return;
            }
        } catch (FileNotFoundException fne) {
           System.out.println("You either did not specify a file to upload or are "
                    + "trying to upload a file to a protected or nonexistent "
                    + "location." + "\n" +fne.getMessage());

        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
        }
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    public void setMangaDAO(MangaDAO dao){
        this.daoM = dao;
    }
}
