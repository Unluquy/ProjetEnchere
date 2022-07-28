package fr.eni.projetenchere.servlet;

import fr.eni.projetenchere.bll.EnchereManager;
import fr.eni.projetenchere.bo.ArticleVendu;
import fr.eni.projetenchere.bo.Enchere;
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
import java.util.Date;

@WebServlet(name = "ServletDetailVente", value = "/ServletDetailVente")
public class ServletDetailVente extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/detailVente.jsp");
        /*Get article*/
        ArticleVendu article = EnchereManager.getInstance().getArticleByNo(Integer.parseInt(request.getParameter("id")));
        Retrait retraitArticle = EnchereManager.getInstance().getRetrait(Integer.parseInt(request.getParameter("id")));
        Enchere enchere = EnchereManager.getInstance().getEnchere(Integer.parseInt(request.getParameter("id")));
        System.out.println(article.getUser().getPseudo());
        request.setAttribute("vendeur",article.getUser().getPseudo());
        request.setAttribute("nom_article",article.getNomArticle());
        request.setAttribute("description",article.getDescription());
        request.setAttribute("categorie",article.getCategorie().getLibelle());
        request.setAttribute("prix_initial",article.getPrixInitial());
        request.setAttribute("prix_vente",enchere==null?null:enchere.getMontantEnchere()); /*get From Encheres*/
        request.setAttribute("encheriseur",enchere==null?null:enchere.getUser().getPseudo()); /*get From Encheres*/
        request.setAttribute("date_fin_encheres",article.getDateFinEncheres());
        request.setAttribute("rue",retraitArticle.getRue());
        request.setAttribute("code_postal",retraitArticle.getCodePostal());
        request.setAttribute("ville",retraitArticle.getVille());



        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate date = LocalDate.now();
        try {
            Date dateNow =  format.parse(String.valueOf(date));
            request.setAttribute("dateNow",dateNow);

            if(article.getDateFinEncheres().compareTo(dateNow) <= 0){
                request.setAttribute("fin",true);
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }



        rd.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        boolean hasError = true;
        String errorString = "";

        int montant = request.getParameter("prix_initial").isEmpty() ? 0 : Integer.parseInt(request.getParameter("prix_initial"));
        ArticleVendu article = EnchereManager.getInstance().getArticleByNo(Integer.parseInt(request.getParameter("id")));
        Utilisateur user = EnchereManager.getInstance().getUser(String.valueOf(session.getAttribute("pseudoUser")),-1);
        Enchere enchere = EnchereManager.getInstance().getEnchere(Integer.parseInt(request.getParameter("id")));

        /*Gestions erreurs*/
        if (user != null) {
            errorString = "Veuillez vous connecter pour encherir";
        } else if (montant > article.getPrixInitial()){
            errorString = "Le montant doit etre superieur au prix initial pour encherir";
        } else if(enchere != null && montant > enchere.getMontantEnchere() ){
            errorString = "Le montant doit etre superieur a la mise pour encherir";
        }

        /*Si vente !existe insert*/
        if( enchere == null && user != null && montant > article.getPrixInitial() && montant <= user.getCredit()){
                EnchereManager.getInstance().insertEnchere(article, user, montant);
                hasError = false;
        }
        /*Sinon update*/
        else if(enchere != null && user != null && montant > enchere.getMontantEnchere() && montant <= user.getCredit()){
            EnchereManager.getInstance().updateEnchere(article, user, montant);
            hasError = false;
        }


        if(!hasError){
            response.sendRedirect("accueil" );
        } else {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/detailVente.jsp");
            request.setAttribute("errorString", errorString);
            rd.forward(request, response);

        }


    }
}
