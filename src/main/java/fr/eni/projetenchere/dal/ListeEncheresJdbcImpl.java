package fr.eni.projetenchere.dal;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.eni.projetenchere.bo.*;

public class ListeEncheresJdbcImpl{

    //TODO Mettre credits a 0 pour la prod
    private final static String INSERT_USER = "INSERT INTO UTILISATEURS(pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,salt,credit,administrateur) VALUES(?,?,?,?,?,?,?,?,?,?,1000,0)";

    private final static String CONNECT_USER = "SELECT pseudo, mot_de_passe, salt FROM UTILISATEURS WHERE pseudo = ? OR email = ?";

    private final static String SELECT_USER = "SELECT * FROM UTILISATEURS WHERE pseudo=? OR no_utilisateur=?";

    /*DELETE USER*/
    private final static String DELETE_USER_RETRAIT = "DELETE RETRAITS FROM RETRAITS inner join ARTICLES_VENDUS on RETRAITS.no_article=ARTICLES_VENDUS.no_article where ARTICLES_VENDUS.no_utilisateur=?";
    private final static String DELETE_USER_ARTICLE = "DELETE FROM ARTICLES_VENDUS WHERE no_utilisateur=?";
    private final static String DELETE_USER = "DELETE FROM UTILISATEURS WHERE no_utilisateur=?";
    /*DELETE USER END*/

    private final static String UPDATE_USER = "UPDATE UTILISATEURS " +
                                              "SET pseudo=?, nom=?, prenom=?, email=?, telephone=?, rue=?, code_postal=?, ville=? "+
                                              "WHERE pseudo=?";
    private final static String UPDATE_PASSWORD =   "UPDATE UTILISATEURS " +
                                                    "SET mot_de_passe = ? , salt = ? "+
                                                    "WHERE pseudo=?";
    private final static String INSERT_ARTICLE = "INSERT INTO ARTICLES_VENDUS(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, no_utilisateur, no_categorie) VALUES(?,?,?,?,?,?,?)";

    private final static String SELECT_CATEGORIE = "SELECT * FROM CATEGORIES WHERE libelle=? or no_categorie=?";

    private final static String SELECT_ALL_CATEGORIES = "SELECT * FROM CATEGORIES";

    private final static String INSERT_RETRAIT = "INSERT INTO RETRAITS(no_article, rue, code_postal, ville) VALUES(?,?,?,?)";
    private final static String SELECT_RETRAIT = "SELECT * FROM RETRAITS WHERE no_article=?";
    private final static String SELECT_ARTICLE = "SELECT * FROM ARTICLES_VENDUS WHERE nom_article=? AND no_utilisateur=? ";
    private final static String SELECT_ARTICLE_BY_NUMBER = "SELECT * FROM ARTICLES_VENDUS WHERE no_article=?";
    private final static String SELECT_ALL_ARTICLES = "SELECT * FROM ARTICLES_VENDUS";
    private final static String INSERT_ENCHERE = "INSERT INTO ENCHERES(no_utilisateur,no_article,date_enchere,montant_enchere) VALUES(?,?,?,?)";
    private final static String SELECT_ENCHERE = "SELECT * FROM ENCHERES WHERE no_article=?";
    private final static String UPDATE_ENCHERE =    "UPDATE ENCHERES " +
                                                    "SET no_utilisateur = ? , montant_enchere = ?, date_enchere=? "+
                                                    "WHERE no_article = ?";

    private final static String SELECT_ALL_ENCHERE = "SELECT * FROM ENCHERES WHERE no_utilisateur=?";
    public void insertUser (Utilisateur user){
        try(Connection cnx = ConnectionProvider.getConnection()) {
            try{
                cnx.setAutoCommit(false);

                PreparedStatement pStmt = cnx.prepareStatement(INSERT_USER);
                pStmt.setString(1, user.getPseudo());
                pStmt.setString(2, user.getNom());
                pStmt.setString(3, user.getPrenom());
                pStmt.setString(4, user.getEmail());
                pStmt.setString(5, user.getTelephone());
                pStmt.setString(6, user.getRue());
                pStmt.setString(7, user.getCode_postal());
                pStmt.setString(8, user.getVille());
                pStmt.setString(9, user.getMot_de_passe());
                pStmt.setString(10, user.getHash());

                pStmt.executeUpdate();

                cnx.commit();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    /**
     * <p>Connect the user</p>
     * */
    public Utilisateur connectUser(String pseudo){

        Utilisateur user = new Utilisateur();

        try(Connection cnx = ConnectionProvider.getConnection()) {

            PreparedStatement pStmt = cnx.prepareStatement(CONNECT_USER);
                pStmt.setString(1, pseudo);
                pStmt.setString(2, pseudo);
                ResultSet rs = pStmt.executeQuery();

                while(rs.next()) {
                    user.setPseudo(rs.getString("pseudo"));
                    user.setMot_de_passe(rs.getString("mot_de_passe"));
                    user.setHash(rs.getString("salt"));
                    return user;
                }

            return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return user
     * */
    public Utilisateur getUser(String pseudo, int no_user){

        Utilisateur user = new Utilisateur();
        try(Connection cnx = ConnectionProvider.getConnection()) {

            PreparedStatement pStmt = cnx.prepareStatement(SELECT_USER);
            pStmt.setString(1, pseudo);
            pStmt.setInt(2, no_user);
            ResultSet rs = pStmt.executeQuery();

            while (rs.next()){
                user.setNo_utilisateur(rs.getInt("no_utilisateur"));
                user.setPseudo(rs.getString("pseudo"));
                user.setPrenom(rs.getString("prenom"));
                user.setNom(rs.getString("nom"));
                user.setEmail(rs.getString("email"));
                user.setTelephone(rs.getString("telephone"));
                user.setRue(rs.getString("rue"));
                user.setCode_postal(rs.getString("code_postal"));
                user.setVille(rs.getString("ville"));
                user.setMot_de_passe(rs.getString("mot_de_passe"));
                user.setHash(rs.getString("salt"));
                user.setCredit(rs.getInt("credit"));
                user.setAdmin(rs.getByte("administrateur"));

                return user;
            }

            return null;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void deleteUser(String pseudo){
        try(Connection cnx = ConnectionProvider.getConnection()) {

            System.out.println("[JDBC] Deleted user");

            Utilisateur user = getUser(pseudo,-1);
/*            System.out.println(user.getNo_utilisateur());*/

            PreparedStatement pStmt = cnx.prepareStatement(DELETE_USER_RETRAIT);
            pStmt.setInt(1, user.getNo_utilisateur());
            pStmt.executeUpdate();

            PreparedStatement pStmt2 = cnx.prepareStatement(DELETE_USER_ARTICLE);
            pStmt2.setInt(1, user.getNo_utilisateur());
            pStmt2.executeUpdate();

            PreparedStatement pStmt3 = cnx.prepareStatement(DELETE_USER);
            pStmt3.setInt(1, user.getNo_utilisateur());
            pStmt3.executeUpdate();


        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateUser(String oldPseudo, Utilisateur newInfo){
        try(Connection cnx = ConnectionProvider.getConnection()) {

            System.out.println("[JDBC] Updated user");

            PreparedStatement pStmt = cnx.prepareStatement(UPDATE_USER);
            pStmt.setString(1, newInfo.getPseudo());
            pStmt.setString(2, newInfo.getNom());
            pStmt.setString(3, newInfo.getPrenom());
            pStmt.setString(4, newInfo.getEmail());
            pStmt.setString(5, newInfo.getTelephone());
            pStmt.setString(6, newInfo.getRue());
            pStmt.setString(7, newInfo.getCode_postal());
            pStmt.setString(8, newInfo.getVille());
            pStmt.setString(9, oldPseudo);
            pStmt.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updatePassword(String pseudo, String password, String hash){
        try(Connection cnx = ConnectionProvider.getConnection()) {
            System.out.println("[JDBC] Updated password");

            PreparedStatement pStmt = cnx.prepareStatement(UPDATE_PASSWORD);
            pStmt.setString(1, password);
            pStmt.setString(2, hash);
            pStmt.setString(3, pseudo);

            pStmt.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insertArticle(ArticleVendu article){
        try(Connection cnx = ConnectionProvider.getConnection()) {

            PreparedStatement pStmt = cnx.prepareStatement(INSERT_ARTICLE);
            pStmt.setString(1, article.getNomArticle());
            pStmt.setString(2, article.getDescription());
            pStmt.setDate(3, (Date) article.getDateDebutEncheres());
            pStmt.setDate(4, (Date) article.getDateFinEncheres());
            pStmt.setInt(5, article.getPrixInitial());
            pStmt.setInt(6, article.getUser().getNo_utilisateur());
            pStmt.setInt(7, article.getCategorie().getNoCategorie());

            pStmt.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }


    }

    public ArticleVendu getArticle(String nom, Utilisateur user){
        ArticleVendu article = new ArticleVendu();
        try(Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pStmt = cnx.prepareStatement(SELECT_ARTICLE);
            pStmt.setString(1, nom);
            pStmt.setInt(2, user.getNo_utilisateur());
            ResultSet rs = pStmt.executeQuery();

            while(rs.next()) {
                System.out.println("[getArticle]" + rs.getInt("no_article"));
                article.setNoArticle(rs.getInt("no_article"));
                /*TODO getArticle*/
                return article;
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArticleVendu getArticleByNo(int number){
        ArticleVendu article = new ArticleVendu();
        try(Connection cnx = ConnectionProvider.getConnection()){
            PreparedStatement pStmt = cnx.prepareStatement(SELECT_ARTICLE_BY_NUMBER);
            pStmt.setInt(1,number);
            ResultSet rs = pStmt.executeQuery();

            while(rs.next()) {
                System.out.println("[getArticle]" + rs.getInt("no_article"));
                article.setUser(getUser(null,rs.getInt("no_utilisateur")));
                article.setNoArticle(rs.getInt("no_article"));
                article.setNomArticle(rs.getString("nom_article"));
                article.setDescription(rs.getString("description"));
                article.setCategorie(getCategorie(null,rs.getInt("no_categorie")));
                article.setPrixInitial(rs.getInt("prix_initial"));
                article.setDateFinEncheres(rs.getDate("date_fin_encheres"));
                article.setDateDebutEncheres(rs.getDate("date_debut_encheres"));

                return article;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public List<ArticleVendu> getAllArticles(){

        try(Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pStmt = cnx.prepareStatement(SELECT_ALL_ARTICLES);
            ResultSet rs = pStmt.executeQuery();

            List<ArticleVendu> articlesList = new ArrayList<ArticleVendu>();
            while (rs.next()) {
                ArticleVendu article = new ArticleVendu();
                article.setNoArticle(rs.getInt("no_article"));
                article.setNomArticle(rs.getString("nom_article"));
                article.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
                article.setDateFinEncheres(rs.getDate("date_fin_encheres"));
                article.setPrixInitial(rs.getInt("prix_initial"));
                article.setUser(getUser(null, rs.getInt("no_utilisateur")));
                article.setCategorie(getCategorie(null, rs.getInt("no_categorie")));
                articlesList.add(article);
            }
            return articlesList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void insertRetrait(Retrait retrait){
        try(Connection cnx = ConnectionProvider.getConnection()) {

            PreparedStatement pStmt = cnx.prepareStatement(INSERT_RETRAIT);
            pStmt.setInt(1, retrait.getArticle().getNoArticle());
            pStmt.setString(2, retrait.getRue());
            pStmt.setString(3, retrait.getCodePostal());
            pStmt.setString(4, retrait.getVille());

            pStmt.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }


    }

    public Retrait getRetrait(int number){
        Retrait retrait = new Retrait();
        try(Connection cnx = ConnectionProvider.getConnection()){
            PreparedStatement pStmt = cnx.prepareStatement(SELECT_RETRAIT);
            pStmt.setInt(1,number);
            ResultSet rs = pStmt.executeQuery();

            while(rs.next()) {
                retrait.setRue(rs.getString("rue"));
                retrait.setCodePostal(rs.getString("code_postal"));
                retrait.setVille(rs.getString("ville"));
                return retrait;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Categorie getCategorie(String libelle, int no_categorie){
        Categorie c = new Categorie();
        try(Connection cnx = ConnectionProvider.getConnection()) {

            PreparedStatement pStmt = cnx.prepareStatement(SELECT_CATEGORIE);
            pStmt.setString(1, libelle);
            pStmt.setInt(2, no_categorie);
            ResultSet rs = pStmt.executeQuery();

            while(rs.next()) {
                c.setLibelle(rs.getString("libelle"));
                c.setNoCategorie(rs.getInt("no_categorie"));
                return c;
            }

            return null;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Categorie> getAllCategories(){
        List<Categorie> cList = new ArrayList<Categorie>();


        try(Connection cnx = ConnectionProvider.getConnection()) {

            PreparedStatement pStmt = cnx.prepareStatement(SELECT_ALL_CATEGORIES);
            ResultSet rs = pStmt.executeQuery();

            while(rs.next()) {
                Categorie c = new Categorie();
                c.setNoCategorie(rs.getInt("no_categorie"));
                c.setLibelle(rs.getString("libelle"));
                cList.add(c);

            }
            return cList;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;

    }

    public void insertEnchere(ArticleVendu article, Utilisateur user, int montant){

        Calendar calendar = Calendar.getInstance();
        java.util.Date currentTime = calendar.getTime();
        long time = currentTime.getTime();

       try(Connection cnx = ConnectionProvider.getConnection()){
                PreparedStatement pStmt = cnx.prepareStatement(INSERT_ENCHERE);

                pStmt.setInt(1, user.getNo_utilisateur());
                pStmt.setInt(2, article.getNoArticle());
                pStmt.setTimestamp(3, new Timestamp(time));
                pStmt.setInt(4, montant);

                pStmt.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

    }

    public void updateEnchere(ArticleVendu article, Utilisateur user, int montant){

        Calendar calendar = Calendar.getInstance();
        java.util.Date currentTime = calendar.getTime();
        long time = currentTime.getTime();

        try(Connection cnx = ConnectionProvider.getConnection()){
            PreparedStatement pStmt = cnx.prepareStatement(UPDATE_ENCHERE);
            pStmt.setInt(4,article.getNoArticle());

            pStmt.setInt(1, user.getNo_utilisateur());
            pStmt.setTimestamp(3, new Timestamp(time));
            pStmt.setInt(2, montant);

            pStmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Enchere getEnchere(int noArticle){
        Enchere enchere = new Enchere();
        try(Connection cnx = ConnectionProvider.getConnection()){
            PreparedStatement pStmt = cnx.prepareStatement(SELECT_ENCHERE);
            pStmt.setInt(1, noArticle);

            ResultSet rs = pStmt.executeQuery();

            while(rs.next()) {
                enchere.setUser(getUser(null, rs.getInt("no_utilisateur")));
                enchere.setArticle(getArticleByNo(rs.getInt("no_article")));
                enchere.setDateEnchere(rs.getDate("date_enchere"));
                enchere.setMontantEnchere(rs.getInt("montant_enchere"));
                return enchere;
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Enchere> getAllEnchereUser(Utilisateur user){
        List<Enchere> eList = new ArrayList<Enchere>();

        try(Connection cnx = ConnectionProvider.getConnection()) {

            PreparedStatement pStmt = cnx.prepareStatement(SELECT_ALL_ENCHERE);
            pStmt.setInt(1, user.getNo_utilisateur());
            ResultSet rs = pStmt.executeQuery();

            while(rs.next()) {
                Enchere enchere = new Enchere();
                enchere.setDateEnchere(rs.getDate("date_enchere"));
                enchere.setArticle(getArticleByNo(rs.getInt("no_article")));
                enchere.setUser(getUser(null, rs.getInt("no_utilisateur")));
                eList.add(enchere);

            }
            return eList;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;

    }
}

