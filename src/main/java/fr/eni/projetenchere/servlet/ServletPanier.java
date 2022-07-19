package fr.eni.projetenchere.servlet;

import fr.eni.projetenchere.bll.EnchereManager;
import fr.eni.projetenchere.bo.ListeCourses;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ServletPanier", value = "/ServletPanier")
public class ServletPanier extends HttpServlet {
    private static final long serialVersionUID = 1L;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Si id_article est présent dans les paramètres, on coche/décoche l'article correspondant
        String idArticleSaisi = request.getParameter("id_article");
        if(idArticleSaisi != null) {
            int idArticle = Integer.parseInt(idArticleSaisi);

            String coche = request.getParameter("coche");
            boolean estCoche = coche != null;
            //		boolean estCoche = false;
            //		if(coche != null) {
            //			estCoche = true;
            //		}

            EnchereManager.getInstance().cocheOuDecocheArticle(idArticle, estCoche);
        }

        String idListe = request.getParameter("id");
        int id = 0;
        if(idListe != null) {
            try {
                id = Integer.parseInt(idListe);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        //Gestion du cas où on utilise l'URL /clear -> remise à 0 des articles
        String urlServlet = request.getServletPath();
        if(urlServlet.equals("/clear")) {
            EnchereManager.getInstance().decocheTout(id);
        }

        //TODO : gestion erreur si id incorrect

        ListeCourses uneListe = EnchereManager.getInstance().getListe(id);
        System.out.println(uneListe);
        request.setAttribute("uneListe", uneListe);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/panier.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}
