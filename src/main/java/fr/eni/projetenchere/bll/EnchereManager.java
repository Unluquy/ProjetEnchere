package fr.eni.projetenchere.bll;

import java.util.List;

import fr.eni.projetenchere.bo.Article;
import fr.eni.projetenchere.bo.ListeCourses;
import fr.eni.projetenchere.bo.Utilisateur;
import fr.eni.projetenchere.dal.DAOFactory;

public class EnchereManager {

    private static EnchereManager instance;

    private void ListeCoursesManager() {  }

    public static EnchereManager getInstance() {
        if(instance == null) {
            instance = new EnchereManager();
        }
        return instance;
    }
/*
    public List<ListeCourses> getListes() {
        return DAOFactory.getListeEnchereDAO().selectAll();
    }

    public void supprimerListe(int id) {
        //TODO : vérification id
        DAOFactory.getListeEnchereDAO().delete(id);
    }

    public ListeCourses getListe(int id) {
        //TODO : vérification id

        return DAOFactory.getListeEnchereDAO().selectById(id);
    }

    public void cocheOuDecocheArticle(int idArticle, boolean estCoche) {
        DAOFactory.getListeEnchereDAO().cocheOuDecoche(idArticle, estCoche);
    }

    public void decocheTout(int idListe) {
        DAOFactory.getListeEnchereDAO().decocheTout(idListe);
    }

    public ListeCourses insererListeEtArticle(String nomListe, String nomArticle) {
        //TODO vérifier les données

        ListeCourses nouvelleListe = new ListeCourses(nomListe);
        Article a = new Article(nomArticle);
        nouvelleListe.ajouterArticle(a);

        DAOFactory.getListeEnchereDAO().insert(nouvelleListe);

        return nouvelleListe;
    }

    public ListeCourses insererArticle(int id, String nomArticle) {

        //TODO vérifier les données

        //on recupère la liste en cours de création dans la BDD
        ListeCourses nouvelleListe = DAOFactory.getListeEnchereDAO().selectById(id);

        //on lui ajoute l'article
        Article a = new Article(nomArticle);
        DAOFactory.getListeEnchereDAO().insertArticle(a, id);
        nouvelleListe.ajouterArticle(a);

        return nouvelleListe;
    }

    public void supprimerArticle(int idArticle) {
        //TODO vérifier données
        DAOFactory.getListeEnchereDAO().deleteArticle(idArticle);
    }*/

    public void insertUser(Utilisateur user) {
        DAOFactory.getListeEnchereDAO().insertUser(user);
        System.out.println("PASSER manager");
    }

    public Utilisateur getUser(String pseudoSaisie) {


        return DAOFactory.getListeEnchereDAO().getUser(pseudoSaisie);
    }
}
