
package cz.cvut.fel.vyhliluk.tjv.internetbanking.servlet;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Lucky
 */
@WebServlet(name="ConfigServlet", urlPatterns={"/ConfigServlet"})
public class ConfigServlet extends HttpServlet {

   private static final long serialVersionUID = 1L;
   static Logger logger;
   private static String PROPERTY_FILE = "log4j.properties";

    @Override
   public void init(){

        URL url = getClass().getResource(PROPERTY_FILE);
      PropertyConfigurator.configure(url.getPath());
      logger = Logger.getLogger(ConfigServlet.class);
      if(logger.isInfoEnabled()){
         logger.info("Soubor s nastavenim pro log4j "+PROPERTY_FILE+" byl uspesne nacten");
      }
   }
   
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
