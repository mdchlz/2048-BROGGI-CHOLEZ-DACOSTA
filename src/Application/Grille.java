/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

/**
 * Classe permettant de générer une nouvelle grille et de gérer ses paramètres
 * @author Sylvain
 */
public class Grille implements Parametres, Serializable {
    /**
     * grille : Ensemble de cases regroupant les cases présentent dans la grille de jeu
     */
    private final HashSet<Case> grille;
    /**
     * valeurMax : Entier représentant la valeur maximale contenue dans la grille de jeu actuel
     */
    private int valeurMax = 0;
    /**
     * deplacement : Booléen vrai si au moins une case a été déplacée suite à un déplacement, faux sinon
     */
    private boolean deplacement;

    /**
     * Constructeur de la classe Grille permettant d'initialiser un nouvelle ensemble pour démarrer une nouvelle partie
     */
    public Grille() {
        this.grille = new HashSet<>();
    }

    /**
     * Réécriture de la méthode toString pour afficher la grille dans la console 
     * Permet de récupérer la taille de la grille grâce aux paramètres de @see 2048.Parametres.java
     * Pour toutes les cases de l'ensemble de la grille, en fonction de ses coordonnées, affichage de sa valeur
     * @return Chaine de caractères retournant les valeurs contenu dans le tableau
     */
    @Override
    public String toString() {
        int[][] tableau = new int[TAILLE][TAILLE];
        for (Case c : this.grille) {
            tableau[c.getY()][c.getX()] = c.getValeur();
        }
        String result = "";
        for (int i = 0; i < tableau.length; i++) {
            result += Arrays.toString(tableau[i]) + "\n";
        }
        return result;
    }
    
    /**
     * Permet l'affichage du tableau contenant les cases de la grille dans la console sous la forme d'HTML
     * @return Retourne la même chaine de caractères que la méthode toString en ajoutant les balises HTML nécessaires
     */
    public String toHTML() {
        int[][] tableau = new int[TAILLE][TAILLE];
        for (Case c : this.grille) {
            tableau[c.getY()][c.getX()] = c.getValeur();
        }
        String result = "<html>";
        for (int i = 0; i < tableau.length; i++) {
            result += Arrays.toString(tableau[i]) + "<br/>";
        }
        result += "</html>";
        return result;
    }

    /**
     * Getter de la variable grille
     * @return Retourne la variable de la grille
     */
    public HashSet<Case> getGrille() {
        return grille;
    }

    /**
     * Getter de l'entier contenant la valeur maximale de la grille
     * @return L'entier correspondant à la valeur maximale d'une case contenue dans la grille
     */
    public int getValeurMax() {
        return valeurMax;
    }

    /**
     * Permet de dire si la partie est terminée, ou non, en fonction de si la grille est complète : la grille contient 16 cases
     * @return Booléen faux s'il reste de la place dans la grille, ou si deux cases voisines ont la même valeur, vrai sinon
     */
    public boolean partieFinie() {
        if (this.grille.size() < TAILLE * TAILLE) {
            return false;
        } else {
            for (Case c : this.grille) {
                for (int i = 1; i <= 2; i++) {
                    if (c.getVoisinDirect(i) != null) {
                        if (c.valeurEgale(c.getVoisinDirect(i))) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * En fonction d'une direction, permet de dire si une case a été déplacée
     * @param direction Variable permettant de préciser la direction choisie pour le déplacement : HAUT=1, DROITE=2, BAS=-1, GAUCHE=-2
     * @return Booléen vrai si des cases ont été déplacées, faux sinon
     */
    public boolean lanceurDeplacerCases(int direction) {
        Case[] extremites = this.getCasesExtremites(direction);
        deplacement = false;
        for (int i = 0; i < TAILLE; i++) {
            switch (direction) {
                case HAUT:
                    this.deplacerCasesRecursif(extremites, i, direction, 0);
                    break;
                case BAS:
                    this.deplacerCasesRecursif(extremites, i, direction, 0);
                    break;
                case GAUCHE:
                    this.deplacerCasesRecursif(extremites, i, direction, 0);
                    break;
                default:
                    this.deplacerCasesRecursif(extremites, i, direction, 0);
                    break;
            }
        }
        return deplacement;
    }

    /**
     * Permet la fusioon de la case qui appelle la méthode et de la case passée en paramètre
     * Dans le cas où le résultat de la fusion des deux cases est supérieur à la valeur maximale actuelle présente dans la grille
     * On remplace la valeur maximale par la valeur de la fusion
     * On passe à vrai la variable deplacement pour dire qu'un déplacement a eu lieu
     * @param c Case avec laquelle il faut effectuer la fusion
     */
    private void fusion(Case c) {
        c.setValeur(c.getValeur() * 2);
        if (this.valeurMax < c.getValeur()) {
            this.valeurMax = c.getValeur();
        }
        deplacement = true;
    }

    /**
     * Méthode permettant de déplacer récursivement les cases présentent de la grille, en fonction des cases voisines, et des cases ayant les mêmes valeurs pour pouvoir les fusionner 
     * @param extremites Tableau regroupant les cases qui sont aux extrémités de la grille
     * @param rangee Entier représentant la rangée dans laquelle on se trouve
     * @param direction Entier représentant la direction dans laquelle il faut se déplacer : HAUT=1, DROITE=2, BAS=-1, GAUCHE=-2
     * @param compteur Entier permettant de savoir où on se trouve dans les déplacements
     */
    private void deplacerCasesRecursif(Case[] extremites, int rangee, int direction, int compteur) {
        if (extremites[rangee] != null) {
            if ((direction == HAUT && extremites[rangee].getY() != compteur)
                    || (direction == BAS && extremites[rangee].getY() != TAILLE - 1 - compteur)
                    || (direction == GAUCHE && extremites[rangee].getX() != compteur)
                    || (direction == DROITE && extremites[rangee].getX() != TAILLE - 1 - compteur)) {
                this.grille.remove(extremites[rangee]);
                switch (direction) {
                    case HAUT:
                        extremites[rangee].setY(compteur);
                        break;
                    case BAS:
                        extremites[rangee].setY(TAILLE - 1 - compteur);
                        break;
                    case GAUCHE:
                        extremites[rangee].setX(compteur);
                        break;
                    default:
                        extremites[rangee].setX(TAILLE - 1 - compteur);
                        break;
                }
                this.grille.add(extremites[rangee]);
                deplacement = true;
            }
            Case voisin = extremites[rangee].getVoisinDirect(-direction);
            if (voisin != null) {
                if (extremites[rangee].valeurEgale(voisin)) {
                    this.fusion(extremites[rangee]);
                    extremites[rangee] = voisin.getVoisinDirect(-direction);
                    this.grille.remove(voisin);
                    this.deplacerCasesRecursif(extremites, rangee, direction, compteur + 1);
                } else {
                    extremites[rangee] = voisin;
                    this.deplacerCasesRecursif(extremites, rangee, direction, compteur + 1);
                }
            }
        }
    }

    /**
     * Méthode permettant de récupérer les cases aux extrémités en fonction de la direction passée en paramètre
     * Si direction = HAUT : retourne les 4 cases qui sont le plus en haut (une pour chaque colonne)
     * Si direction = DROITE : retourne les 4 cases qui sont le plus à droite (une pour chaque ligne)
     * Si direction = BAS : retourne les 4 cases qui sont le plus en bas (une pour chaque colonne)
     * Si direction = GAUCHE : retourne les 4 cases qui sont le plus à gauche (une pour chaque ligne)
     * Attention : le tableau retourné peut contenir des null si les lignes/colonnes sont vides
     * @param direction Variable permettant de préciser la direction choisie : HAUT=1, DROITE=2, BAS=-1, GAUCHE=-2
     * @return Tableau de cases de taille correspondante à la taille donner dans la classe Parametres @see 2048.Parametres.java, les cases sont celles qui se trouvent dans l'extrémité concernée par la direction choisie
     */
    public Case[] getCasesExtremites(int direction) {
        Case[] result = new Case[TAILLE];
        for (Case c : this.grille) {
            switch (direction) {
                case HAUT:
                    if ((result[c.getX()] == null) || (result[c.getX()].getY() > c.getY())) { // si on n'avait pas encore de case pour cette rangée ou si on a trouvé un meilleur candidat
                        result[c.getX()] = c;
                    }
                    break;
                case BAS:
                    if ((result[c.getX()] == null) || (result[c.getX()].getY() < c.getY())) {
                        result[c.getX()] = c;
                    }
                    break;
                case GAUCHE:
                    if ((result[c.getY()] == null) || (result[c.getY()].getX() > c.getX())) {
                        result[c.getY()] = c;
                    }
                    break;
                default:
                    if ((result[c.getY()] == null) || (result[c.getY()].getX() < c.getX())) {
                        result[c.getY()] = c;
                    }
                    break;
            }
        }
        return result;
    }

    /**
     * Méthode permettant d'afficher un message en cas de victoire et de fermer l'application ensuite
     */
    public void victory() {
        System.out.println("Bravo ! Vous avez atteint " + this.valeurMax);
        System.exit(0);
    }

    /**
     * Méthode permettant d'afficher un message en cas de défaite et de fermer l'apllication ensuite 
     */
    public void gameOver() {
        System.out.println("La partie est finie. Votre score est " + this.valeurMax);
        System.exit(1);
    }

    /**
     * Méthode permettant la création d'une nouvelle case dans la grille si elle n'est pas déjà complète
     * L'ajout se fait à partir de toutes les cases libres présentes dans la grille, puis une de ces cases est choisie aléatoirement pour être ajoutée à la grille
     * @return Booléen vrai la case a pu être créée et ajouter à la grille, faux sinon
     */
    public boolean nouvelleCase() {
        if (this.grille.size() < TAILLE * TAILLE) {
            ArrayList<Case> casesLibres = new ArrayList<>();
            Random ra = new Random();
            int valeur = (1 + ra.nextInt(2)) * 2;
            // on crée toutes les cases encore libres
            for (int x = 0; x < TAILLE; x++) {
                for (int y = 0; y < TAILLE; y++) {
                    Case c = new Case(x, y, valeur);
                    if (!this.grille.contains(c)) { // contains utilise la méthode equals dans Case
                        casesLibres.add(c);
                    }
                }
            }
            // on en choisit une au hasard et on l'ajoute à la grille
            Case ajout = casesLibres.get(ra.nextInt(casesLibres.size()));
            ajout.setGrille(this);
            this.grille.add(ajout);
            if ((this.grille.size() == 1) || (this.valeurMax == 2 && ajout.getValeur() == 4)) { // Mise à jour de la valeur maximale présente dans la grille si c'est la première case ajoutée ou si on ajoute un 4 et que l'ancien max était 2
                this.valeurMax = ajout.getValeur();
            }
            return true;
        } else {
            return false;
        }
    }
    
    public void ajoutCase(int x, int y, int valeur) {
        Case c = new Case(x,y,valeur);
        c.setGrille(this);
        this.grille.add(c);
    }
    
}
