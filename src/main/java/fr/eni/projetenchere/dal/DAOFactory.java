package fr.eni.projetenchere.dal;

public class DAOFactory {
    public static ListeEncheresJdbcImpl getListeEnchereDAO() {
        return new ListeEncheresJdbcImpl();
    }
}
