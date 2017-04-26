# 2048-BROGGI-CHOLEZ-DACOSTA

Afin de pouvoir utiliser notre application, il faut procéder à l’installation de la base de données sur le logiciel easyPHP, puis à la mise en place celle-ci dans l’IDE que nous utilisions, ici nous décrivons la méthode pour le faire avec NetBeans. Voici les étapes à suivre pour y parvenir :
  1.	Dans EasyPHP : Importer le fichier 2048.sql dans phpMyAdmin, avec comme nom pour la base de données : 2048.
  2.	Dans le projet sur NetBeans : Aller dans « Librairies »
    > Clic droit sur « Librairies » puis « Add Library »
    > Choisir « MySQL JDBC Drive » puis « Add Library »
  3.	Ouvrir l’onglet window > « Services » 
    > Clic droit sur « Databases »
    > « New connection »
  4.	Dans la fenêtre « New connection Wizard », dans le champ « Driver »
    > « MySQL (Connector/J Driver)
    > « Next »
    > « Host » : « localhost »
    > « Port » : « 3306 »
    > « Database » : « mysql »
    > « User Name » : « root »
    > « JDBC URL » : « jdbc:mysql://localhost:3306/2048 »
  5.	Test de la connexion : 
    > « Test Connection »
    > « Connection succeeded »
