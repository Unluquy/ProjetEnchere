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

@WebServlet(name = "ServletModifProfil", value = "/ServletModifProfil")
public class ServletModifProfil extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/modifProfil.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur user = EnchereManager.getInstance().getUser((String) session.getAttribute("pseudoUser"), -1);

        String errorString = "";
        boolean hasError = false;
        boolean changePwd = false;

        String salt = null;
        String password = null;
        String securePasswords = null;


        /*if user clicks delete*/
        if (request.getParameter("delete") != null){
            EnchereManager.getInstance().deleteUser((String) session.getAttribute("pseudoUser"));

            /*dismount the user*/
            session.setAttribute("pseudoUser", null);
            response.sendRedirect("accueil" );
        }
        /*else if the user clicks register*/
        else if (request.getParameter("register") != null){
            /*get infos, if input is empty, get base user credentials*/
            String pseudoSaisie     = !request.getParameter("pseudo").isEmpty()  ? request.getParameter("pseudo"): user.getPseudo();
            String prenomSaisie     = !request.getParameter("prenom").isEmpty() ? request.getParameter("prenom"): user.getPrenom();
            String nomSaisie        = !request.getParameter("nom").isEmpty() ? request.getParameter("nom"): user.getNom();
            String emailSaisie      = !request.getParameter("email").isEmpty() ? request.getParameter("email"): user.getEmail();
            String telephoneSaisie  = !request.getParameter("telephone").isEmpty() ? request.getParameter("telephone"): user.getTelephone();
            String rueSaisie        = !request.getParameter("rue").isEmpty() ? request.getParameter("rue"): user.getRue();
            String codePostalSaisie = !request.getParameter("code_postal").isEmpty() ? request.getParameter("code_postal"): user.getPseudo();
            String villeSaisie      = !request.getParameter("ville").isEmpty() ? request.getParameter("ville"): user.getVille();

            /*If user entered a new password check password confirmation and if old password correspond*/
            if(!request.getParameter("password").isEmpty() && !request.getParameter("pwdActuel").isEmpty() && request.getParameter("password").equals(request.getParameter("confirmPassword"))){
                System.out.println(user.getMot_de_passe());
                System.out.println(getSecurePassword(request.getParameter("pwdActuel"),user.getHash()));
                if(user.getMot_de_passe().equals(getSecurePassword(request.getParameter("pwdActuel"),user.getHash()))){

                    try {
                        salt = getSalt();

                        password = request.getParameter("password");
                        securePasswords = getSecurePassword(password, salt);

                        changePwd = true;

                    } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
                        throw new RuntimeException(e);
                    }

                }else{
                    errorString = "Vous avez rentre le mauvais mot de passe";
                    hasError = true;
                }

            }else if (!request.getParameter("pwdActuel").isEmpty() && (request.getParameter("password").isEmpty() || request.getParameter("confirmPassword").isEmpty())){
                errorString = "Veuillez entrer tout les champs relatif au changement de mot de passe";
                hasError = true;
            } else if (request.getParameter("pwdActuel").isEmpty() && (!request.getParameter("password").isEmpty() || !request.getParameter("confirmPassword").isEmpty())){
                errorString = "Veuillez entrer tout les champs relatif au changement de mot de passe";
                hasError = true;
            } else if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))){
                errorString = "Les mots de passes ne correspondent pas";
                hasError = true;
            }

            user.setPseudo(pseudoSaisie);
            user.setPrenom(prenomSaisie);
            user.setNom(nomSaisie);
            user.setEmail(emailSaisie);
            user.setTelephone(telephoneSaisie);
            user.setRue(rueSaisie);
            user.setCode_postal(codePostalSaisie);
            user.setVille(villeSaisie);


            if(!hasError){
                if(changePwd){EnchereManager.getInstance().updatePassword((String) session.getAttribute("pseudoUser"), securePasswords, salt);}
                EnchereManager.getInstance().updateUser((String) session.getAttribute("pseudoUser"), user);
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/modifProfil.jsp");
                rd.forward(request, response);
            } else {
                request.setAttribute("errorString", errorString);
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/modifProfil.jsp");
                rd.forward(request, response);
            }


        }

    }

    protected static String getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
        System.out.println("HELLO");

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
