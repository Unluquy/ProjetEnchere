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
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/inscription.jsp");
        rd.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*Create user with and get info*/
        Utilisateur user = new Utilisateur();

        boolean hasError = true;
        String errorString = "";

        String pseudoSaisie     = request.getParameter("pseudo");
        String prenomSaisie     = request.getParameter("prenom");
        String nomSaisie        = request.getParameter("nom");
        String emailSaisie      = request.getParameter("email");
        String telephoneSaisie  = request.getParameter("telephone");
        String rueSaisie        = request.getParameter("rue");
        String codePostalSaisie = request.getParameter("codePostal");
        String villeSaisie      = request.getParameter("ville");
        String motDePasseSaisie = request.getParameter("password");
        String motDePasseValidationSaisie = request.getParameter("confirmPassword");

        /*If nothing empty then put check if user is in DB*/
        if (pseudoSaisie.length() != 0 && prenomSaisie.length() != 0 && nomSaisie.length() != 0 && emailSaisie.length() != 0 && rueSaisie.length() != 0 && codePostalSaisie.length() != 0 && villeSaisie.length() != 0 && motDePasseSaisie.length() != 0 && motDePasseValidationSaisie.equals(motDePasseSaisie)){

            Utilisateur userCheck = EnchereManager.getInstance().getUser(pseudoSaisie);

            /*If not identitcal user, create user ; else send back to Inscription with error msg*/
            if(userCheck == null){
                hasError = false;
                user.setPseudo(pseudoSaisie);
                user.setNom(nomSaisie);
                user.setPrenom(prenomSaisie);
                user.setEmail(emailSaisie);
                user.setRue(rueSaisie);
                user.setCode_postal(codePostalSaisie);
                user.setVille(villeSaisie);
                user.setMot_de_passe(motDePasseSaisie);
                user.setTelephone(telephoneSaisie);

                EnchereManager.getInstance().insertUser(user);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
                rd.forward(request, response);


            } else if (emailSaisie.equals(userCheck.getEmail())){
                errorString = "Email deja existante";
            } else {
                errorString = "Pseudo deja existant";
            }

        }else {
            if(motDePasseValidationSaisie.equals(motDePasseSaisie)){
                errorString = "Veuillez remplir les champs";
            } else {
                errorString = "Les mots de passes ne correspondent pas";
            }

        }

        if(hasError){
            request.setAttribute("errorString", errorString);
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/inscription.jsp");
            rd.forward(request, response);
        }

    }
}
