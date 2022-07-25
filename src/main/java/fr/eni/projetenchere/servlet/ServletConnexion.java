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
                Utilisateur user = EnchereManager.getInstance().connectUser(pseudoSaisie);
                /*get hash from db to compare the password*/
                String saltDB = null;
                if (user != null) {
                    saltDB = user.getHash();
                }


                if (user == null || !getSecurePassword(motDePasseSaisie, saltDB).equals(user.getMot_de_passe())) {
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
            Utilisateur user = EnchereManager.getInstance().connectUser(pseudoSaisie);

            HttpSession session = request.getSession();
            System.out.println("[ServletConnexion] " + pseudoSaisie);
            session.setAttribute("pseudoUser", user.getPseudo());
            response.sendRedirect("accueil" );
        } else {
            request.setAttribute("errorString", errorString);
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/connexion.jsp");
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
