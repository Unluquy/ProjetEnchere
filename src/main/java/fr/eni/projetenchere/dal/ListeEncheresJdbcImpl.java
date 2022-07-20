package fr.eni.projetenchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import fr.eni.projetenchere.bo.Utilisateur;

public class ListeEncheresJdbcImpl{

    private final static String INSERT_USER = "INSERT INTO UTILISATEURS(pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe, credit, administrateur) VALUES(?,?,?,?,?,?,?,?,?,1000,0)";

    private final static String CONNECT_USER = "SELECT pseudo, mot_de_passe FROM UTILISATEURS WHERE pseudo = ? and mot_de_passe = ?";

    private final static String SELECT_USER = "SELECT * FROM UTILISATEURS WHERE pseudo=?";

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

                cnx.commit();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Utilisateur connectUser(String pseudo, String password){

        Utilisateur user = new Utilisateur();

        try(Connection cnx = ConnectionProvider.getConnection()) {

            PreparedStatement pStmt = cnx.prepareStatement(CONNECT_USER);
                pStmt.setString(1, pseudo);
                pStmt.setString(2, password);
                ResultSet rs = pStmt.executeQuery();

                while(rs.next()) {
                    user.setPseudo(rs.getString("pseudo"));
                    user.setMot_de_passe(rs.getString("mot_de_passe"));
                    return user;
                }

            return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Utilisateur getUser(String pseudo){

        Utilisateur user = new Utilisateur();
        try(Connection cnx = ConnectionProvider.getConnection()) {

            PreparedStatement pStmt = cnx.prepareStatement(SELECT_USER);
            pStmt.setString(1, pseudo);
            ResultSet rs = pStmt.executeQuery();

            while (rs.next()){
                user.setPseudo(rs.getString("pseudo"));
                user.setPrenom(rs.getString("prenom"));
                user.setNom(rs.getString("nom"));
                user.setEmail(rs.getString("email"));
                user.setTelephone(rs.getString("telephone"));
                user.setRue(rs.getString("rue"));
                user.setCode_postal(rs.getString("code_postal"));
                user.setVille(rs.getString("ville"));
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

}

