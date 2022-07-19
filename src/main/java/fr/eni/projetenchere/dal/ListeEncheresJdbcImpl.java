package fr.eni.projetenchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.projetenchere.bo.Article;
import fr.eni.projetenchere.bo.ListeCourses;
import fr.eni.projetenchere.bo.Utilisateur;

public class ListeEncheresJdbcImpl {


    private final static String INSERT_USER = "INSERT INTO UTILISATEURS(pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe, credit, administrateur) VALUES(?,?,?,?,?,?,?,?,?,1000,0)";

    private final static String SELECT_USER = "SELECT pseudo, mot_de_passe FROM UTILISATEURS WHERE pseudo = ?";

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

                pStmt.executeUpdate();

                System.out.println("PASSER Jdbc");

                cnx.commit();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Utilisateur getUser(String pseudo){

        Utilisateur user = new Utilisateur();

        try(Connection cnx = ConnectionProvider.getConnection()) {
            try{

                PreparedStatement pStmt = cnx.prepareStatement(SELECT_USER);
                pStmt.setString(1, pseudo);
                ResultSet rs = pStmt.executeQuery();

                while(rs.next()) {
                    user.setPseudo(rs.getString("pseudo"));
                    user.setMot_de_passe(rs.getString("mot_de_passe"));
                }



            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
/*

    public List<ListeCourses> selectAll() {
        List<ListeCourses> listes = new ArrayList<>();

        try(Connection cnx = ConnectionProvider.getConnection()) {


            Statement stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_ALL);
            while(rs.next()) {
//				ListeCourses l = map(rs);
//				listes.add(l);
                listes.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listes;
    }


    public void delete(int id) {
        try(Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pStmt = cnx.prepareStatement(DELETE);
            pStmt.setInt(1, id);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ListeCourses selectById(int id) {
        ListeCourses uneListe = null;

        try(Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pStmt = cnx.prepareStatement(SELECT_BY_ID);
            pStmt.setInt(1, id);
            ResultSet rs = pStmt.executeQuery();
            while(rs.next()) {
                //à la 1ere itération, on crée la ListeCourses
                if(uneListe == null) {
                    uneListe = map(rs);
                }
                //à chaque itération, on ajoute les articles
                int idArticle = rs.getInt("idArticle");
                String nomArticle = rs.getString("nomArticle");
                boolean coche = rs.getBoolean("coche");
                Article a = new Article(idArticle, nomArticle, coche);

                uneListe.ajouterArticle(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return uneListe;
    }

    public void cocheOuDecoche(int idArticle, boolean estCoche) {
        try(Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pStmt = cnx.prepareStatement(CHECK);
            pStmt.setBoolean(1, estCoche);
            pStmt.setInt(2, idArticle);
            pStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ListeCourses map(ResultSet rs) throws SQLException {
        int id = rs.getInt("idListe");
        String nom = rs.getString("nom");
        ListeCourses l = new ListeCourses(id, nom);
        return l;
    }


    public void decocheTout(int idListe) {
        try(Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pStmt = cnx.prepareStatement(UNCHECK_ALL);
            pStmt.setInt(1, idListe);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(ListeCourses nouvelleListe) {

        try(Connection cnx = ConnectionProvider.getConnection()) {
            try {
                cnx.setAutoCommit(false);

                PreparedStatement pStmt = cnx.prepareStatement(INSERT_LISTE, Statement.RETURN_GENERATED_KEYS);
                pStmt.setString(1, nouvelleListe.getNom());
                pStmt.executeUpdate();

                ResultSet rs = pStmt.getGeneratedKeys();
                if(rs.next()) {
                    int idListe = rs.getInt(1);
                    nouvelleListe.setId(idListe);

                    Article a = nouvelleListe.getArticles().get(0);

                    PreparedStatement pStmt2 = cnx.prepareStatement(INSERT_ARTICLE);
                    pStmt2.setString(1, a.getNom());
                    pStmt2.setInt(2, idListe);
                    pStmt2.executeUpdate();
                }
                cnx.commit();

            } catch (SQLException e) {
                e.printStackTrace();
                cnx.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void insertArticle(Article a, int idListe) {
        try(Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pStmt = cnx.prepareStatement(INSERT_ARTICLE);
            pStmt.setString(1, a.getNom());
            pStmt.setInt(2, idListe);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void deleteArticle(int idArticle) {
        try(Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pStmt = cnx.prepareStatement(DELETE_ARTICLE);
            pStmt.setInt(1, idArticle);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
}

