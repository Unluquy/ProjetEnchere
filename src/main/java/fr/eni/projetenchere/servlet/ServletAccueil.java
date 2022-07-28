package fr.eni.projetenchere.servlet;



import fr.eni.projetenchere.bll.EnchereManager;
import fr.eni.projetenchere.bo.ArticleVendu;
import fr.eni.projetenchere.bo.Categorie;
import fr.eni.projetenchere.bo.Enchere;
import fr.eni.projetenchere.bo.Utilisateur;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ServletAccueil", value = "/ServletAccueil")
public class ServletAccueil extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        LocalDate date = LocalDate.now();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateNow =  format.parse(String.valueOf(date));
            session.setAttribute("dateNow", dateNow);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        session.setAttribute("radioAchats", true);
        Utilisateur user = EnchereManager.getInstance().getUser((String) session.getAttribute("pseudoUser"),-1);
        List<ArticleVendu> articlesList = EnchereManager.getInstance().getAllArticles();
        List<Enchere> enchereList = user != null ? EnchereManager.getInstance().getAllEnchereUser(user): new ArrayList<Enchere>();
        request.setAttribute("categories", EnchereManager.getInstance().getAllCategories());
        request.setAttribute("articlesList", articlesList);
        request.setAttribute("enchereList", enchereList);


        if(request.getParameter("submitFilter") != null){
            String[] checkedBoxAchats = request.getParameterValues("CheckEncheres") != null ? request.getParameterValues("CheckEncheres") : new String[]{}; /*Insert ceux qui sont actifs*/
            String[] checkedBoxVendus = request.getParameterValues("CheckVentes")   != null ? request.getParameterValues("CheckVentes") : new String[]{}; /*Insert ceux qui sont actifs*/
            String radioFiltre = request.getParameter("radioFiltre");


            session.setAttribute("EncheresOuvertes",null);
            session.setAttribute("EncheresEnCours",null);
            session.setAttribute("EncheresRemportees",null);

            session.setAttribute("VentesEnCours",null);
            session.setAttribute("VentesNonDebutees",null);
            session.setAttribute("VentesTerminees",null);

            for (String check:
                 checkedBoxAchats) {
                switch (check){
                    case "EncheresOuvertes"      : session.setAttribute("EncheresOuvertes",true);break;
                    case "EncheresEnCours"       : session.setAttribute("EncheresEnCours",true);break;
                    case "EncheresRemportees"    : session.setAttribute("EncheresRemportees",true);break;
                }
            }

            for (String check:
                    checkedBoxVendus) {
                switch (check){
                    case "VentesEnCours"        : session.setAttribute("VentesEnCours",true);break;
                    case "VentesNonDebutees"    : session.setAttribute("VentesNonDebutees",true);break;
                    case "VentesTerminees"      : session.setAttribute("VentesTerminees",true);break;
                }
            }

            switch (radioFiltre){
                case "radioAchats":session.setAttribute("radioAchats",true);session.setAttribute("radioVentes",null);break;
                case "radioVentes":session.setAttribute("radioVentes",true);session.setAttribute("radioAchats",null);break;
            }
            session.setAttribute("categorieSelected",request.getParameter("CategorieFiltre"));
        } else {

        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if(request.getParameter("disconnect") != null){
            session.setAttribute("pseudoUser", null);

        }

        request.setAttribute("categories", EnchereManager.getInstance().getAllCategories());
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
        rd.forward(request, response);
    }

}
