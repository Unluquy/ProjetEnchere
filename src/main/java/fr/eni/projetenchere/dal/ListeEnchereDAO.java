package fr.eni.projetenchere.dal;


import fr.eni.projetenchere.bo.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


public class ListeEnchereDAO {

	void insertUser(Utilisateur user){}

	Utilisateur connectUser(String pseudo){return null;}

	Utilisateur getUser(String pseudo, int no_user){return null;}
	void deleteUser(String pseudo){}
	void updateUser(String oldPseudo, Utilisateur newInfo){}
	void updatePassword(String pseudo, String password, String hash){}
	Categorie getCategorie(String libelle, int no_categorie){return null;}
	List<ArticleVendu> getAllArticles(){return null;}
	List<Categorie> getAllCategories(){return null;}
	void insertArticle(ArticleVendu article){}
	ArticleVendu getArticle(String nom, Utilisateur user){return null;}
	ArticleVendu getArticleByNo(int number){return null;}

	Retrait getRetrait(int number){return null;}
	void insertRetrait(Retrait retrait){}

	void insertEnchere(ArticleVendu article, Utilisateur user, int montant) {}
	void updateEnchere(ArticleVendu article, Utilisateur user, int montant) {}

	Enchere getEnchere(int noArticle){return null;}
	List<Enchere> getAllEnchereUser(Utilisateur user){return null;}

}
