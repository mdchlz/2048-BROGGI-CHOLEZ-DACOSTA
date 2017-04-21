/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import java.sql.*;


/**
 *
 * @author Thomas
 */
public class Connexion {
    
    //chemin pour charger le pilote
    String urlPilote = "com.mysql.jdbc.Driver";
    //chemin de connexion à la bdd
    String urlBaseDonnees = "jdbc:mysql://localhost:3306/2048";
    Connection con;
    
    public  Connexion() {
        
        //chargement de notre pilote
        try {
            Class.forName(urlPilote);
            System.out.println("Chargement pilote réussi");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
        
        //chargement de notre bdd
        try {
            con = DriverManager.getConnection(urlBaseDonnees,"root","");
            System.out.println("Connexion à la BDD réussie");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
    }
    
    Connection ObtenirConnexion() {
        return con;
    }
    
}
