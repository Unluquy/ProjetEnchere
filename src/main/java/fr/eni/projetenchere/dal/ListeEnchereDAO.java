package fr.eni.projetenchere.dal;


import fr.eni.projetenchere.bo.Utilisateur;


public class ListeEnchereDAO {

	void insertUser(Utilisateur user){}

	Utilisateur connectUser(String pseudo, String password){return null;}

	Utilisateur getUser(String pseudo){return null;}
	void deleteUser(String pseudo){}
	void updateUser(String oldPseudo, Utilisateur newInfo){}
	void updatePassword(String pseudo, String password, String hash){}

}
