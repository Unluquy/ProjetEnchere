package fr.eni.projetenchere.bo;

public class Article {
    private int id;

    private String nom;

    private boolean estCoche;

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

    public boolean isEstCoche() {
        return estCoche;
    }

    public void setEstCoche(boolean estCoche) {
        this.estCoche = estCoche;
    }

    public Article() { }

    public Article(String nom) {
        this();
        this.nom = nom;
        this.estCoche = false;
    }

    public Article(int id, String nom, boolean estCoche) {
        this();
        this.id = id;
        this.nom = nom;
        this.estCoche = estCoche;
    }

    @Override
    public String toString() {
        return "Article [id=" + id + ", nom=" + nom + ", estCoche=" + estCoche + "]";
    }
}
