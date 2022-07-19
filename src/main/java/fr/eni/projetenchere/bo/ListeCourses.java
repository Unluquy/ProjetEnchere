package fr.eni.projetenchere.bo;
import java.util.ArrayList;
import java.util.List;
public class ListeCourses {
    private int id;

    private String nom;

    private List<Article> articles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public void ajouterArticle(Article a) {
        this.articles.add(a);
    }

    public ListeCourses() {
        articles = new ArrayList<>();
    }

    public ListeCourses(String nom) {
        this();
        this.nom = nom;
    }

    public ListeCourses(int id, String nom) {
        this();
        this.id = id;
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "ListeCourses [id=" + id + ", nom=" + nom + ", articles=" + articles + "]";
    }

}
