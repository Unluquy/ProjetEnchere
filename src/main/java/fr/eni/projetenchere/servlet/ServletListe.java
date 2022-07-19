package fr.eni.projetenchere.servlet;

import fr.eni.projetenchere.bo.ListeCourses;
import fr.eni.projetenchere.bll.EnchereManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletListe", value = "/ServletListe")
public class ServletListe extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

/*        //Si on reçoit un id en paramètre dans l'URL, c'est que l'on veut supprimer une liste
        String idListe = request.getParameter("id");
        if(idListe != null) {
            int id = 0;
            try {
                id = Integer.parseInt(idListe);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            //Si l'id est un id valide --> on supprime
            if(id > 0) {
                EnchereManager.getInstance().supprimerListe(id);
            }
        }

        //Affichage de la liste
        List<ListeCourses> listes = EnchereManager.getInstance().getListes();
        System.out.println(listes);
        request.setAttribute("listes", listes);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/listes.jsp");
        rd.forward(request, response);*/
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
