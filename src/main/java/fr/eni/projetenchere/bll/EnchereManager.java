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

    public Utilisateur connectUser(String pseudoSaisie, String passwordSaisie) {
        return DAOFactory.getListeEnchereDAO().connectUser(pseudoSaisie, passwordSaisie);
    }


    public Utilisateur getUser(String pseudoSaisie) {
        return DAOFactory.getListeEnchereDAO().getUser(pseudoSaisie);
    }
}
