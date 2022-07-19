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

public class ListeEncheresJdbcImpl {
    private final static String SELECT_ALL = "SELECT * FROM ListesCourse";

    private final static String DELETE = "DELETE FROM ListesCourse WHERE idListe = ?";

    private final static String SELECT_BY_ID =  "SELECT * FROM ListesCourse " +
            "INNER JOIN Articles ON idListe=liste " +
            "WHERE idListe = ?";

    private final static String CHECK = "UPDATE Articles SET coche=? WHERE idArticle=?";

    private final static String UNCHECK_ALL = "UPDATE Articles SET coche=0 WHERE liste=?";

    private final static String INSERT_LISTE = "INSERT INTO ListesCourse(nom) VALUES(?)";

    private final static String INSERT_ARTICLE = "INSERT INTO Articles(nomArticle,liste) VALUES(?,?)";

    private final static String DELETE_ARTICLE = "DELETE FROM Articles WHERE idArticle = ?";


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
    }
}
