package fr.eni.projetenchere.servlet;

import fr.eni.projetenchere.bll.EnchereManager;
import fr.eni.projetenchere.bo.ListeCourses;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ServletNouvelle", value = "/ServletNouvelle")
public class ServletNouvelle extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String idListeParam = request.getParameter("id_liste");
        if(idListeParam != null) { //la liste existe déjà, on ne fait que supprimer un article
            ListeCourses nouvelleListe = null;
            int idListe = Integer.parseInt(idListeParam);

            String idArticleParam = request.getParameter("id_article");
            int idArticle = Integer.parseInt(idArticleParam);
            EnchereManager.getInstance().supprimerArticle(idArticle);

            nouvelleListe = EnchereManager.getInstance().getListe(idListe);
            request.setAttribute("nouvelleListe", nouvelleListe);
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/nouvelle.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String nomListe = request.getParameter("nom_liste");

        String nomArticle = request.getParameter("nom_article");

        /*
         * Si la requête arrive avec un id_liste dans ses paramètres,
         * c'est que l'on ajoute un article,
         * sinon on crée une nouvelle liste avec un 1er articles
         */
        String idListeParam = request.getParameter("id_liste");
        ListeCourses nouvelleListe = null;
        if(idListeParam != null) { //la liste existe déjà, on ne fait qu'ajouter un article
            int idListe = Integer.parseInt(idListeParam);
            nouvelleListe = EnchereManager.getInstance().insererArticle(idListe, nomArticle);
        } else {
            nouvelleListe =  EnchereManager.getInstance().insererListeEtArticle(nomListe, nomArticle);
        }

        System.out.println(nouvelleListe);
        request.setAttribute("nouvelleListe", nouvelleListe);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/nouvelle.jsp");
        rd.forward(request, response);
    }
}
