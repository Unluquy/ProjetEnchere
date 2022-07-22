package fr.eni.projetenchere.bll;

import fr.eni.projetenchere.bo.Utilisateur;
import fr.eni.projetenchere.dal.DAOFactory;

public class EnchereManager {

    private static EnchereManager instance;

    public static EnchereManager getInstance() {
        if(instance == null) {
            instance = new EnchereManager();
        }
        return instance;
    }
    public void insertUser(Utilisateur user) {
        DAOFactory.getListeEnchereDAO().insertUser(user);
    }

    public Utilisateur connectUser(String pseudoSaisie) {
        return DAOFactory.getListeEnchereDAO().connectUser(pseudoSaisie);
    }


    public Utilisateur getUser(String pseudoSaisie) {
        return DAOFactory.getListeEnchereDAO().getUser(pseudoSaisie);
    }
    public void deleteUser(String pseudoSaisie) {
        DAOFactory.getListeEnchereDAO().deleteUser(pseudoSaisie);
    }
    public void updateUser(String oldPseudo, Utilisateur newInfo){
        DAOFactory.getListeEnchereDAO().updateUser(oldPseudo, newInfo);
    }
    public void updatePassword(String pseudo, String password, String hash){
        DAOFactory.getListeEnchereDAO().updatePassword(pseudo, password, hash);
    }
}
