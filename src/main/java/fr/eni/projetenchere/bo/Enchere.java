package fr.eni.projetenchere.bo;

import java.util.Date;

public class Enchere {
    private Date dateEnchere;
    private int montantEnchere;

    private Utilisateur user;

    private ArticleVendu article;


    public Date getDateEnchere() {return dateEnchere;}

    public void setDateEnchere(Date dateEnchere) {
        this.dateEnchere = dateEnchere;
    }

    public int getMontantEnchere() {
        return montantEnchere;
    }

    public void setMontantEnchere(int montantEnchere) {
        this.montantEnchere = montantEnchere;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    public ArticleVendu getArticle() {
        return article;
    }

    public void setArticle(ArticleVendu article) {
        this.article = article;
    }

    public Enchere() {
        this.user = new Utilisateur();
        this.article = new ArticleVendu();
    }

    public Enchere(Date dateEnchere, int montantEnchere, Utilisateur user, ArticleVendu article) {
        this();
        this.dateEnchere = dateEnchere;
        this.montantEnchere = montantEnchere;
    }
}
