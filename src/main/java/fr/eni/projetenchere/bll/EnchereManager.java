package fr.eni.projetenchere.bll;

import fr.eni.projetenchere.bo.*;
import fr.eni.projetenchere.dal.DAOFactory;

import java.sql.Date;
import java.util.List;

public class EnchereManager {

    private static EnchereManager instance;

    public static EnchereManager getInstance() {
        if(instance == null) {
            instance = new EnchereManager();
        }
        return instance;
    }
    public void insertUser(Utilisateur user) {
        DAOFactory.getListeEnchereDAO().insertUser(user);
    }

    public Utilisateur connectUser(String pseudoSaisie) {
        return DAOFactory.getListeEnchereDAO().connectUser(pseudoSaisie);
    }

    public Utilisateur getUser(String pseudoSaisie, int no_user) {
        return DAOFactory.getListeEnchereDAO().getUser(pseudoSaisie, no_user);
    }
    public void deleteUser(String pseudoSaisie) {
        DAOFactory.getListeEnchereDAO().deleteUser(pseudoSaisie);
    }
    public void updateUser(String oldPseudo, Utilisateur newInfo){
        DAOFactory.getListeEnchereDAO().updateUser(oldPseudo, newInfo);
    }
    public void updatePassword(String pseudo, String password, String hash){
        DAOFactory.getListeEnchereDAO().updatePassword(pseudo, password, hash);
    }
    public Categorie getCategorie(String libelle, int no_categorie) {
        return DAOFactory.getListeEnchereDAO().getCategorie(libelle, no_categorie);}
    public List<ArticleVendu> getAllArticles() {
        return DAOFactory.getListeEnchereDAO().getAllArticles();}
    public List<Categorie> getAllCategories() {
        return DAOFactory.getListeEnchereDAO().getAllCategories();}
    public void insertArticle(ArticleVendu article) {
        DAOFactory.getListeEnchereDAO().insertArticle(article);}
    public ArticleVendu getArticle(String nom, Utilisateur user) {
        return DAOFactory.getListeEnchereDAO().getArticle(nom, user);
    }
    public ArticleVendu getArticleByNo(int number){
        return DAOFactory.getListeEnchereDAO().getArticleByNo(number);
    }
    public Retrait getRetrait(int number){
        return DAOFactory.getListeEnchereDAO().getRetrait(number);
    }
    public void insertRetrait(Retrait retrait){
        DAOFactory.getListeEnchereDAO().insertRetrait(retrait);
    }

    public void insertEnchere(ArticleVendu article, Utilisateur user, int montant) {DAOFactory.getListeEnchereDAO().insertEnchere(article,user,montant);}
    public void updateEnchere(ArticleVendu article, Utilisateur user, int montant) {DAOFactory.getListeEnchereDAO().updateEnchere(article,user,montant);}

    public Enchere getEnchere(int noArticle){return DAOFactory.getListeEnchereDAO().getEnchere(noArticle);}
    public List<Enchere> getAllEnchereUser(Utilisateur user){return DAOFactory.getListeEnchereDAO().getAllEnchereUser(user);}
}
