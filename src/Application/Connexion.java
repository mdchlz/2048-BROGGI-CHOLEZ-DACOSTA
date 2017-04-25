/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import java.sql.*;


/**
 * Classe permettant de se connecter à la base de données de l'application pour gérer la sauvegarde d'une partie et l'enregistrement du classement des joueurs
 * @author Thomas
 */
public class Connexion {
    /**
     * Chaine de caractères représentant le chemin pour atteindre le pilote
     */
    String urlPilote = "com.mysql.jdbc.Driver";
    /**
     * Chaine de caractères représentant le chemin pour effectuer la connexion à la base de données
     */
    String urlBaseDonnees = "jdbc:mysql://localhost:3306/2048";
    /**
     * Variable permettant de représenter la connexion
     */
    Connection con;
    
    /**
     * Constructeur de la classe Connexion pour mettre en place la connexion
     * Effectue d'une part le chargement du pilote
     * Et ensuite le chargement de la base de données
     */    
    public  Connexion() {
        
        try {
            Class.forName(urlPilote);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
        
        try {
            con = DriverManager.getConnection(urlBaseDonnees,"root","");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
    }
    /**
     * Permet d'obtenir l'état de la connexion
     * @return La variable qui permet de se connecter
     */
    Connection ObtenirConnexion() {
        return con;
    }
    
}
