package fr.eni.projetenchere.servlet;

import fr.eni.projetenchere.bll.EnchereManager;
import fr.eni.projetenchere.bo.Utilisateur;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ServletConnection", value = "/ServletConnection")
public class ServletConnection extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/connect.jsp");
        rd.forward(request, response);
        response.setContentType("text/html");


        System.out.println("PASSER doGET");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String pseudoSaisie = request.getParameter("identifiant");
        String motDePasseSaisie = request.getParameter("motDePasse");

        Utilisateur user=  EnchereManager.getInstance().getUser(pseudoSaisie);

        if (pseudoSaisie.equals(user.getPseudo()) && motDePasseSaisie.equals(user.getMot_de_passe())){
            System.out.println("connected");
        } else {
            System.out.println("not connected");

        }


    }
}
