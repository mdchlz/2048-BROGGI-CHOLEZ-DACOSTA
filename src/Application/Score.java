/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Thomas
 */
public class Score {
    private final SimpleStringProperty pseudo;
    private final SimpleIntegerProperty valeur;
 
    public Score(String pseudo, int valeur) {
        this.pseudo = new SimpleStringProperty(pseudo);
        this.valeur = new SimpleIntegerProperty(valeur);
    }
 
    public String getPseudo() {
        return pseudo.get();
    }
    
    public int getValeur() {
        return valeur.get();
    }

}
