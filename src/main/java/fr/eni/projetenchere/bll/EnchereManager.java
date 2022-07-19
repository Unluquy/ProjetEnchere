package fr.eni.projetenchere.bll;

import java.util.List;

import fr.eni.projetenchere.bo.Article;
import fr.eni.projetenchere.bo.ListeCourses;
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

    public List<ListeCourses> getListes() {
        return DAOFactory.getListeCoursesDAO().selectAll();
    }

    public void supprimerListe(int id) {
        //TODO : vérification id
        DAOFactory.getListeCoursesDAO().delete(id);
    }

    public ListeCourses getListe(int id) {
        //TODO : vérification id

        return DAOFactory.getListeCoursesDAO().selectById(id);
    }

    public void cocheOuDecocheArticle(int idArticle, boolean estCoche) {
        DAOFactory.getListeCoursesDAO().cocheOuDecoche(idArticle, estCoche);
    }

    public void decocheTout(int idListe) {
        DAOFactory.getListeCoursesDAO().decocheTout(idListe);
    }

    public ListeCourses insererListeEtArticle(String nomListe, String nomArticle) {
        //TODO vérifier les données

        ListeCourses nouvelleListe = new ListeCourses(nomListe);
        Article a = new Article(nomArticle);
        nouvelleListe.ajouterArticle(a);

        DAOFactory.getListeCoursesDAO().insert(nouvelleListe);

        return nouvelleListe;
    }

    public ListeCourses insererArticle(int id, String nomArticle) {

        //TODO vérifier les données

        //on recupère la liste en cours de création dans la BDD
        ListeCourses nouvelleListe = DAOFactory.getListeCoursesDAO().selectById(id);

        //on lui ajoute l'article
        Article a = new Article(nomArticle);
        DAOFactory.getListeCoursesDAO().insertArticle(a, id);
        nouvelleListe.ajouterArticle(a);

        return nouvelleListe;
    }

    public void supprimerArticle(int idArticle) {
        //TODO vérifier données
        DAOFactory.getListeCoursesDAO().deleteArticle(idArticle);
    }

}
