package fr.eni.projetenchere.dal;


import fr.eni.projetenchere.bo.ArticleVendu;
import fr.eni.projetenchere.bo.Categorie;
import fr.eni.projetenchere.bo.Retrait;
import fr.eni.projetenchere.bo.Utilisateur;

import java.util.List;


public class ListeEnchereDAO {

	void insertUser(Utilisateur user){}

	Utilisateur connectUser(String pseudo){return null;}

	Utilisateur getUser(String pseudo){return null;}
	void deleteUser(String pseudo){}
	void updateUser(String oldPseudo, Utilisateur newInfo){}
	void updatePassword(String pseudo, String password, String hash){}
	Categorie getCategorie(String libelle){return null;}
	List<Categorie> getAllCategories(){return null;}
	void insertArticle(ArticleVendu article){}
	ArticleVendu getArticle(String nom, Utilisateur user){return null;}
	void insertRetrait(Retrait retrait){}

}
