/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

/**
 * Classe permettant de générer une nouvelle case et de gérer ses paramètres
 * @author Sylvain
 */
public class Case implements Parametres, Cloneable {
    /**
     * x, y : Coordonnées de la case dans la grille de jeu
     * valeur : Chiffre/nombre contenu dans la case
     * grille : Objet représentant la grille de jeu
     */
    private int x, y, valeur;
    private Grille grille;

    /**
     * Constructeur de la classe Case permettant d'initialiser une nouvelle case dans la grille
     * @param abs Coordonnée en abscisse de la case
     * @param ord Coordonnée en ordonnée de la case
     * @param v Valeur contenue dans la case
     */
    public Case(int abs, int ord, int v) {
        this.x = abs;
        this.y = ord;
        this.valeur = v;
    }

    /**
     * Setter de la variable grille permettant de modifier cette variable
     * @param g Représente la grille dans laquelle se déroule la partie
     */
    public void setGrille(Grille g) {
        this.grille = g;
    }
    
    /**
     * Getter de la coordonnée en abscisse de la case
     * @return L'entier correspondant à la coordonnée en abscisse
     */
    public int getX() {
        return this.x;
    }

    /**
     * Getter de la coordonnée en ordonnée de la case
     * @return L'entier correspondant à la coordonnée en ordonnée
     */
    public int getY() {
        return this.y;
    }

    /**
     * Setter de la coordonnée en abscisse permettant de la modifier
     * @param x Variable qui contient la nouvelle valeur de la coordonnée en abscisse
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Setter de la coordonnée en ordonnée permettant de la modifier
     * @param y Variable qui contient la nouvelle valeur de la coordonnée en ordonnée
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Setter de la valeur contenue dans la case permettant de la modifier
     * @param valeur Variable qui contient la nouvelle valeur de la case 
     */
    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    /**
     * Getter de la valeur de la case
     * @return Retourne l'entier correspondant à la valeur de la case
     */
    public int getValeur() {
        return this.valeur;
    }

    /**
     * Utilisée lors de l'ajout d'une case à un ensemble pour vérifier qu'il n'y a pas de doublon
     * @param obj Objet pour lequel on veut vérifier son existence dans l'ensemble des cases de la grille
     * @return Un booléen vrai si l'objet existe déjà dans l'ensemble, faux sinon
     */
    @Override
    public boolean equals(Object obj) { 
        if (obj instanceof Case) {
            Case c = (Case) obj;
            return (this.x == c.x && this.y == c.y);
        } else {
            return false;
        }
    }

    /**
     * Permet de déterminer un hashcode 
     * @return Le hashcode associé
     */
    @Override
    public int hashCode() {
        return this.x * 7 + this.y * 13;
    }

    /**
     * Permet de comparer la valeur de la case ayant appelée la méthode et la valeur de la case passée en paramètre
     * @param c Case pour laquelle il faut comparer la valeur avec la valeur de la case appelant la méthode
     * @return Un booléen vrai si les valeurs des deux cases sont égales, faux sinon
     */
    public boolean valeurEgale(Case c) {
        if (c != null) {
            return this.valeur == c.valeur;
        } else {
            return false;
        }
    }

    /**
     * Permet de récupérer la case voisine d'une autre, en fonction d'une direction passée en paramètre
     * Pour chaque case de l'ensemble représentant la grille, vérification des coordonnées pour trouver la case voisine
     * @param direction Variable représentant la direction vers laquelle il faut récupérer les voisins : HAUT=1, DROITE=2, BAS=-1, GAUCHE=-2
     * @return La case voisine si elle existe, null sinon
     */
    public Case getVoisinDirect(int direction) {
        if (direction == HAUT) {
            for (int i = this.y - 1; i >= 0; i--) {
                for (Case c : grille.getGrille()) {
                    if (c.getX() == this.x && c.getY() == i) {
                        return c;
                    }
                }
            }
        } else if (direction == BAS) {
            for (int i = this.y + 1; i < TAILLE; i++) {
                for (Case c : grille.getGrille()) {
                    if (c.getX() == this.x && c.getY() == i) {
                        return c;
                    }
                }
            }
        } else if (direction == GAUCHE) {
            for (int i = this.x - 1; i >= 0; i--) {
                for (Case c : grille.getGrille()) {
                    if (c.getX() == i && c.getY() == this.y) {
                        return c;
                    }
                }
            }
        } else if (direction == DROITE) {
            for (int i = this.x + 1; i < TAILLE; i++) {
                for (Case c : grille.getGrille()) {
                    if (c.getX() == i && c.getY() == this.y) {
                        return c;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Réécriture de la méthode toString pour afficher les paramètres d'une case
     * @return Une chaine de caractères regroupant les coordonnées et la valeur de la case appelant cette méthode
     */
    @Override
    public String toString() {
        return "Case(" + this.x + "," + this.y + "," + this.valeur + ")";
    }

    @Override
    public Object clone() { 
	Object copy = null;
	try {
            copy = super.clone();
            } catch(CloneNotSupportedException ex) {
                System.out.println(ex);
	}
	return copy;
    }
    
}
