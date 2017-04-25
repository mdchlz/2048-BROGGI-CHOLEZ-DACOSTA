/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Classe permettant de gérer le score d'un joueur
 * @author Thomas
 */
public class Score {
    /**
     * Variable finale représentant le pseudo du joueur
     */
    private final SimpleStringProperty pseudo;
    /**
     * Variable finale représentant le score du joueur
     */
    private final SimpleIntegerProperty valeur;
 
    /**
     * Constructeur de la classe Score permettant d'iniatialiser le nom et le score du joueur
     * @param pseudo Chaine de caractères représentant le pseudo du joueur
     * @param valeur Entier représentant le score du joueur
     */
    public Score(String pseudo, int valeur) {
        this.pseudo = new SimpleStringProperty(pseudo);
        this.valeur = new SimpleIntegerProperty(valeur);
    }
 
    /**
     * Getteur du pseudo
     * @return Retourne la chaine de caractère correspondant au pseudo
     */
    public String getPseudo() {
        return pseudo.get();
    }
    
    /**
     * Getter du score
     * @return Retourne le score
     */
    public int getValeur() {
        return valeur.get();
    }

}
