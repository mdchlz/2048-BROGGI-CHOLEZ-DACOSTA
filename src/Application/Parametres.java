/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

/**
 * Classe permettant de regrouper toutes les variables nécessaires pour le fonctionnement du jeu
 * Ces variables pourront être appelées dans les autres classes pour faciliter l'écriture de certaines méthodes
 * @author Sylvain
 */
public interface Parametres {
    /**
     * Entier représentant la direction HAUT 
     */
    static final int HAUT = 1;
    /**
     * Entier représentant la direction DROITE
     */
    static final int DROITE = 2;
    /**
     * Entier représentant la direction BAS
     */
    static final int BAS = -1;
    /**
     * Entier représentant la direction GAUCHE
     */
    static final int GAUCHE = -2;
    /**
     * Entier représentant la taille souhaitée pour la grille de jeu
     */
    static final int TAILLE = 4;
    /**
     * Entier représentant l'objectif à atteindre pour gagner la partie
     */
    static final int OBJECTIF = 2048;
}
