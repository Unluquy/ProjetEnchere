package fr.eni.projetenchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import fr.eni.projetenchere.bo.Utilisateur;

public class ListeEncheresJdbcImpl{

    private final static String INSERT_USER = "INSERT INTO UTILISATEURS(pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,salt,credit,administrateur) VALUES(?,?,?,?,?,?,?,?,?,?,1000,0)";

    private final static String CONNECT_USER = "SELECT pseudo, mot_de_passe, salt FROM UTILISATEURS WHERE pseudo = ?";

    private final static String SELECT_USER = "SELECT * FROM UTILISATEURS WHERE pseudo=?";
    private final static String DELETE_USER = "DELETE FROM UTILISATEURS WHERE pseudo=?";
    private final static String UPDATE_USER = "UPDATE UTILISATEURS " +
                                              "SET pseudo=?, nom=?, prenom=?, email=?, telephone=?, rue=?, code_postal=?, ville=? "+
                                              "WHERE pseudo=?";
    private final static String UPDATE_PASSWORD =   "UPDATE UTILISATEURS " +
                                                    "SET mot_de_passe = ? , salt = ? "+
                                                    "WHERE pseudo=?";


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
    public Utilisateur getUser(String pseudo){

        Utilisateur user = new Utilisateur();
        try(Connection cnx = ConnectionProvider.getConnection()) {

            System.out.println("[JDBC] Entered getuser");

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

            PreparedStatement pStmt = cnx.prepareStatement(DELETE_USER);
            pStmt.setString(1, pseudo);
            pStmt.executeUpdate();

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

}

