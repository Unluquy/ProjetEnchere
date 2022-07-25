package fr.eni.projetenchere.servlet;

import fr.eni.projetenchere.bll.EnchereManager;
import fr.eni.projetenchere.bo.ArticleVendu;
import fr.eni.projetenchere.bo.Categorie;
import fr.eni.projetenchere.bo.Retrait;
import fr.eni.projetenchere.bo.Utilisateur;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@WebServlet(name = "ServletNouvelleVente", value = "/ServletNouvelleVente")
public class ServletNouvelleVente extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/nouvelleVente.jsp");

        /*TODO Setup categories*/
        request.setAttribute("categories", EnchereManager.getInstance().getAllCategories());
        rd.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur user = EnchereManager.getInstance().getUser((String) session.getAttribute("pseudoUser"));
        request.setAttribute("pseudo", session.getAttribute("pseudoUser"));

        boolean hasError = false;
        String errorString = "";

        /*Create object*/
        ArticleVendu article = new ArticleVendu();
        Categorie categorie = EnchereManager.getInstance().getCategorie((String) request.getParameter("categorie"));
        Retrait retrait = new Retrait();


        /*set article*/
        if(request.getParameter("nom_article").isEmpty() || request.getParameter("description").isEmpty() || request.getParameter("prix_initial").isEmpty()){
            hasError = true;
            errorString = "Veuillez remplir les champs requis";
        } else {
            article.setNomArticle(request.getParameter("nom_article"));
            article.setDescription(request.getParameter("description"));
            article.setPrixInitial(Integer.parseInt(request.getParameter("prix_initial")));
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date formatDateDebut = format.parse(request.getParameter("date_debut_encheres"));
            Date formatDateFin = format.parse(request.getParameter("date_fin_encheres"));

            java.sql.Date dateDebut = new java.sql.Date(formatDateDebut.getTime());
            java.sql.Date dateFin = new java.sql.Date(formatDateFin.getTime());

            article.setDateDebutEncheres(dateDebut);
            article.setDateFinEncheres(dateFin);

            LocalDate date = LocalDate.now();
            Date dateNow =  format.parse(String.valueOf(date));

            if(dateNow.compareTo(formatDateDebut) > 0){
                hasError = true;
                errorString = "La date de debut doit etre au minimum aujourd'hui";
            } else if(formatDateDebut.compareTo(formatDateFin) > 0) {
                hasError = true;
                errorString = "La date de fin doit etre au minimum un(1) jour apres la date de debut";
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        article.setUser(user);
        article.setCategorie(categorie);
        /*end article*/

        /*retrait*/
        if (!request.getParameter("rue").isEmpty()) {
            retrait.setRue(request.getParameter("rue"));
        } else {
            retrait.setRue(user.getRue());
        }

        if (!request.getParameter("code_postal").isEmpty()) {
            retrait.setCodePostal(request.getParameter("code_postal"));
        } else {
            retrait.setCodePostal(user.getCode_postal());
        }
        if (!request.getParameter("ville").isEmpty()) {
            retrait.setVille(request.getParameter("ville"));
        } else {
            retrait.setVille(user.getVille());
        }

        if(EnchereManager.getInstance().getArticle(article.getNomArticle(),user) != null){
            hasError = true;
            errorString = "Duplication d'enchere";
        }

        /*Create Article vendu if no error*/
        if(hasError){
            request.setAttribute("errorString", errorString);
            request.setAttribute("categories", EnchereManager.getInstance().getAllCategories());
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/nouvelleVente.jsp");
            rd.forward(request,response);
        } else {
            EnchereManager.getInstance().insertArticle(article);

            retrait.setArticle(EnchereManager.getInstance().getArticle(article.getNomArticle(),user));
            System.out.println(EnchereManager.getInstance().getArticle(article.getNomArticle(),user));
            EnchereManager.getInstance().insertRetrait(retrait);
        }



    }
}
