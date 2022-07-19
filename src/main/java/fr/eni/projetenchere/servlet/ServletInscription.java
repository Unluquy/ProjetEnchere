package fr.eni.projetenchere.servlet;

import fr.eni.projetenchere.bll.EnchereManager;
import fr.eni.projetenchere.bo.Utilisateur;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ServletInscription", value = "/ServletInscription")
public class ServletInscription extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/profil.jsp");
        rd.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Utilisateur user = new Utilisateur();

        String pseudoSaisie = request.getParameter("pseudo");
        String prenomSaisie = request.getParameter("prenom");
        String nomSaisie = request.getParameter("nom");
        String emailSaisie = request.getParameter("email");
        String telephoneSaisie = request.getParameter("telephone");
        String rueSaisie = request.getParameter("rue");
        String codePostalSaisie = request.getParameter("codePostal");
        String villeSaisie = request.getParameter("ville");
        String motDePasseSaisie = request.getParameter("userPassword");

        if (pseudoSaisie.length() != 0 || prenomSaisie.length() != 0 || nomSaisie.length() != 0 || emailSaisie.length() != 0 || rueSaisie.length() != 0 ||codePostalSaisie.length() != 0 ||villeSaisie.length() != 0 ||motDePasseSaisie.length() != 0 ){
            user.setPseudo(pseudoSaisie);
            user.setNom(nomSaisie);
            user.setPrenom(prenomSaisie);
            user.setEmail(emailSaisie);
            user.setRue(rueSaisie);
            user.setCode_postal(codePostalSaisie);
            user.setVille(villeSaisie);
            user.setMot_de_passe(motDePasseSaisie);

            if (telephoneSaisie == null){
                user.setTelephone("");
            } else {
                user.setTelephone(telephoneSaisie);
            }

            EnchereManager.getInstance().insertUser(user);
        }

    }
}
