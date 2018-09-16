
Lanceur(Main) pour lancer le  programme.


Fonctionnalité:
	-Upload => galerry: choisir une image depuis notre galerry
	-Upload => repertoire: choisir une image depuis votre répertoire
	-clear: Supprimer tous les points de l'écran indiqué
	-<<>>: inverser les 2 images dans les écrans (ssi on a fini de placer tous les points dans les 2 écrans)
	-New: recommencer la partie
	-Exit: quitter 
	une fois l'image installé dans l'écran gauche clique gauche sur l'écran pour ajouter les points bleu ,
clique droit sur l'écran pour terminer (on peut plus modifier),toutes les informatitions seront transmis dans 
notre database,puis refaisez la meme chose pour la 2ème écran(les points de la 1ere image vont aparaitre vert 
successivement pour vous indiquer l'ordre ).




------------------------------------------------------------------------------------------------------------------
Base de donné:(vous importez le fichier morphing.sql dans phpmyamine)
	
	nom de base:morphing3
	tables: point;photo
	driver = "com.mysql.jdbc.Driver";
	url : "jdbc:mysql://localhost:3306/morphing"
	utilisateur: "root"
	mot de passe = ""