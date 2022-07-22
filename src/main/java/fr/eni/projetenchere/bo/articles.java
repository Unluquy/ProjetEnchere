package fr.eni.projetenchere.bo;

public class articles {

    private int no_article;
    private String nom_article;
    private String description;
    private int date_debut_encheres;
    private int date_fin_encheres;
    private int prix_initial;
    private int prix_vente;
    private String rue;
    private int code_postale;
    private String ville;


    public int getNo_article() {
        return no_article;
    }

    public void setNo_article(int no_article) {
        this.no_article = no_article;
    }

    public String getNom_article() {
        return nom_article;
    }

    public void setNom_article(String nom_article) {
        this.nom_article = nom_article;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDate_debut_encheres() {
        return date_debut_encheres;
    }

    public void setDate_debut_encheres(int date_debut_encheres) {
        this.date_debut_encheres = date_debut_encheres;
    }

    public int getDate_fin_encheres() {
        return date_fin_encheres;
    }

    public void setDate_fin_encheres(int date_fin_encheres) {
        this.date_fin_encheres = date_fin_encheres;
    }

    public int getPrix_initial() {
        return prix_initial;
    }

    public void setPrix_initial(int prix_initial) {
        this.prix_initial = prix_initial;
    }

    public int getPrix_vente() {
        return prix_vente;
    }

    public void setPrix_vente(int prix_vente) {
        this.prix_vente = prix_vente;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public int getCode_postale() {
        return code_postale;
    }

    public void setCode_postale(int code_postale) {
        this.code_postale = code_postale;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public articles(int no_article, String nom_article, String description, int date_debut_encheres, int date_fin_encheres, int prix_initial, int prix_vente, String rue, int code_postale, String ville) {
        this.no_article = no_article;
        this.nom_article = nom_article;
        this.description = description;
        this.date_debut_encheres = date_debut_encheres;
        this.date_fin_encheres = date_fin_encheres;
        this.prix_initial = prix_initial;
        this.prix_vente = prix_vente;
        this.rue = rue;
        this.code_postale = code_postale;
        this.ville = ville;
    }

    @Override
    public String toString() {
        return "articles{" +
                "no_article=" + no_article +
                ", nom_article='" + nom_article + '\'' +
                ", description='" + description + '\'' +
                ", date_debut_encheres=" + date_debut_encheres +
                ", date_fin_encheres=" + date_fin_encheres +
                ", prix_initial=" + prix_initial +
                ", prix_vente=" + prix_vente +
                ", rue='" + rue + '\'' +
                ", code_postale=" + code_postale +
                ", ville='" + ville + '\'' +
                '}';
    }
}