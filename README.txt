
Lanceur(Main) pour lancer le  programme.


Fonctionnalit�:
	-Upload => galerry: choisir une image depuis notre galerry
	-Upload => repertoire: choisir une image depuis votre r�pertoire
	-clear: Supprimer tous les points de l'�cran indiqu�
	-<<>>: inverser les 2 images dans les �crans (ssi on a fini de placer tous les points dans les 2 �crans)
	-New: recommencer la partie
	-Exit: quitter 
	une fois l'image install� dans l'�cran gauche clique gauche sur l'�cran pour ajouter les points bleu ,
clique droit sur l'�cran pour terminer (on peut plus modifier),toutes les informatitions seront transmis dans 
notre database,puis refaisez la meme chose pour la 2�me �cran(les points de la 1ere image vont aparaitre vert 
successivement pour vous indiquer l'ordre ).




------------------------------------------------------------------------------------------------------------------
Base de donn�:(vous importez le fichier morphing.sql dans phpmyamine)
	
	nom de base:morphing3
	tables: point;photo
	driver = "com.mysql.jdbc.Driver";
	url : "jdbc:mysql://localhost:3306/morphing"
	utilisateur: "root"
	mot de passe = ""