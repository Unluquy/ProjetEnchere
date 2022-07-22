package fr.eni.projetenchere.servlet;

import fr.eni.projetenchere.bll.EnchereManager;
import fr.eni.projetenchere.bo.Utilisateur;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Arrays;

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
            String salt = null;
            try {
                salt = getSalt();
            } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
                throw new RuntimeException(e);
            }
            String securePasswords = getSecurePassword(motDePasseSaisie, salt);

            /*If not identitcal user, create user ; else send back to Inscription with error msg*/
            if(userCheck == null || !pseudoSaisie.toLowerCase().equals(userCheck.getPseudo().toLowerCase())){
                hasError = false;
                user.setPseudo(pseudoSaisie);
                user.setNom(nomSaisie);
                user.setPrenom(prenomSaisie);
                user.setEmail(emailSaisie);
                user.setRue(rueSaisie);
                user.setCode_postal(codePostalSaisie);
                user.setVille(villeSaisie);
                user.setMot_de_passe(securePasswords);
                user.setHash(salt);
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

    protected static String getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {

        /*SHA1PRNG algorithm is a strong pseudo - random number generator based on the SHA-! message-digest algorithm*/
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");

        /*create array for salt*/
        byte[] salt = new byte[16];

        /*get random salt*/
        sr.nextBytes(salt);

        return Arrays.toString(salt);
    }

    protected static String getSecurePassword(String passwordToHash, String salt){
        String generatedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(salt.getBytes());

            byte[] bytes = md.digest(passwordToHash.getBytes());

            StringBuilder sb = new StringBuilder();

            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            generatedPassword = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return generatedPassword;
    }
}
