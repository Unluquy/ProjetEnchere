package fr.eni.projetenchere.dal;

import java.util.List;

import fr.eni.projetenchere.bo.Article;
import fr.eni.projetenchere.bo.ListeCourses;

public class ListeEnchereDAO {
    public List<ListeCourses> selectAll() {
        return null;
    }

    void delete(int id) {

    }

    ListeCourses selectById(int id) {
        return null;
    }

    void cocheOuDecoche(int idArticle, boolean estCoche) {

    }

    void decocheTout(int idListe){};

    void insert(ListeCourses nouvelleListe){};

    void insertArticle(Article a, int id){};

    void deleteArticle(int idArticle){};
}
