package fr.eni.projetenchere.servlet;

import fr.eni.projetenchere.bll.EnchereManager;
import fr.eni.projetenchere.bo.Utilisateur;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ServletProfil", value = "/ServletProfil")
public class ServletProfil extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/profil.jsp");


        HttpSession session = request.getSession();
        System.out.println("[ServletProfil] Pseudo User session " + session.getAttribute("pseudoUser"));

        Utilisateur user = EnchereManager.getInstance().getUser((String) session.getAttribute("pseudoUser"));
        if (user != null){
            System.out.println("[ServletProfil] User " + user);
            request.setAttribute("pseudo", user.getPseudo());
            request.setAttribute("nom", user.getNom());
            request.setAttribute("prenom", user.getPrenom());
            request.setAttribute("email", user.getEmail());
            request.setAttribute("telephone", user.getTelephone());
            request.setAttribute("rue", user.getRue());
            request.setAttribute("code_postal", user.getCode_postal());
            request.setAttribute("ville", user.getVille());
            request.setAttribute("credit", user.getCredit());
        }

        rd.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
