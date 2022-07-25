package fr.eni.projetenchere.bll;

import fr.eni.projetenchere.bo.ArticleVendu;
import fr.eni.projetenchere.bo.Categorie;
import fr.eni.projetenchere.bo.Retrait;
import fr.eni.projetenchere.bo.Utilisateur;
import fr.eni.projetenchere.dal.DAOFactory;

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

    public Utilisateur getUser(String pseudoSaisie) {
        return DAOFactory.getListeEnchereDAO().getUser(pseudoSaisie);
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
    public Categorie getCategorie(String libelle) {
        return DAOFactory.getListeEnchereDAO().getCategorie(libelle);}
    public List<Categorie> getAllCategories() {
        return DAOFactory.getListeEnchereDAO().getAllCategories();}
    public void insertArticle(ArticleVendu article) {
        DAOFactory.getListeEnchereDAO().insertArticle(article);}
    public ArticleVendu getArticle(String nom, Utilisateur user) {
        return DAOFactory.getListeEnchereDAO().getArticle(nom, user);
    }
    public void insertRetrait(Retrait retrait){
        DAOFactory.getListeEnchereDAO().insertRetrait(retrait);
    }
}
