package fr.eni.projetenchere.servlet;

import fr.eni.projetenchere.bll.EnchereManager;
import fr.eni.projetenchere.bo.Utilisateur;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ServletConnection", value = "/ServletConnection")
public class ServletConnexion extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/connexion.jsp");
        rd.forward(request, response);
        response.setContentType("text/html");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*Get credentials from form*/
        String pseudoSaisie     = request.getParameter("identifiant");
        String motDePasseSaisie = request.getParameter("password");

        boolean hasError = false;
        String errorString = "";

        /*verify if the user wrote credentials*/
        if (!(pseudoSaisie.isEmpty() || motDePasseSaisie.isEmpty())) {

            /*try to find the user in the DB*/
            try {
                Utilisateur user = EnchereManager.getInstance().connectUser(pseudoSaisie, motDePasseSaisie);

                if (user == null) {
                    hasError = true;
                    errorString = "Pseudo ou Mot de passe invalide(s)";
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            hasError = true;
            errorString = "Pseudo ou mot de passe necessaires";
        }

        /*Connect if no error otherwise send back to connect.jsp with error message*/
        if(!hasError){
            System.out.println("Connected");
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
            rd.forward(request, response);
        } else {

            request.setAttribute("errorString", errorString);
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/connexion.jsp");
            rd.forward(request, response);
        }



    }
}
