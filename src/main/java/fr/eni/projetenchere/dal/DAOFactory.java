package fr.eni.projetenchere.dal;

public class DAOFactory {
    public static ListeEncheresJdbcImpl getListeCoursesDAO() {
        return new ListeEncheresJdbcImpl();
    }
}
