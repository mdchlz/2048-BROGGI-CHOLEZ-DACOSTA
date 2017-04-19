/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import static Application.Parametres.BAS;
import static Application.Parametres.DROITE;
import static Application.Parametres.GAUCHE;
import static Application.Parametres.HAUT;
import static Application.Parametres.OBJECTIF;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Thomas
 */
public class InterfaceController implements Initializable {

    /**
     * Grille représentant l'application
     */
    @FXML
    private GridPane grille;
    /**
     * Encadré pour afficher le score du joueur
     */
    @FXML
    private Label score;
    @FXML
    private Label scoreint;
    /**
     * Encadré pour afficher le meilleur score du joueur
     */
    @FXML
    private Label best;
    @FXML
    private Label bestint;
    /**
     * Reference to the main application.
    */
    private MainApp mainApp;
    /**
     * Entier pour récupérer la direction souhaitée
     */
    public int direction;
    /**
     * Nouvelle grille pour lancer une nouvelle partie
     */
    Grille g = new Grille();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    /**
     * Permet d'initialiser une nouvelle partie :
     *  - création des deux nouvelles cases au début de la partie
     *  - ajout des images associées aux cases créées
     */
    public void initPartie () {
        boolean b = g.nouvelleCase();
        b = g.nouvelleCase();   
        System.out.println(g);
        ajoutImageCase(g);
    }
    
    /**
     * Permet d'ajouter toutes les images aux cases qui sont dans l'ensemble de la grille
     * @param g Grille dans laquelle se trouve les cases du jeu pour lesquelles il faut ajouter les images
     */
    public void ajoutImageCase(Grille g) {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                String chemin = "file:Images/empty.png";
                ImageView imgview = new ImageView();
                Image image = new Image(chemin);
                imgview.setImage(image);
                grille.add(imgview, x, y); 
            }
        }
        for(Case c : g.getGrille()) {
            int cx = c.getX();
            int cy = c.getY();
            int cvalue = c.getValeur();
            String chemin = "file:Images/"+cvalue+".png";
            ImageView imgview = new ImageView();
            Image image = new Image(chemin);
            imgview.setImage(image);
            grille.add(imgview, cx, cy);
        }
        scoreint.setText(String.valueOf(g.getValeurMax()));
    }
    
    /**
     * Permet le fonctionnement d'une partie tant qu'elle n'est pas terminée : 
     *  - si la partie n'est pas finie, déplacement des cases en fonction de la direction choisie
     *  - génération des nouvelles cases après un déplacement
     *  - génération du message de victoire ou de défaite 
     * @param g Grille dans laquelle la partie se déroule
     */
    public void deroulementPartie(Grille g) {
        boolean b;
        if (!g.partieFinie()) {
            boolean b2 = g.lanceurDeplacerCases(direction);
            if (b2) {
                b = g.nouvelleCase();
                if (!b) {
                    g.gameOver();
                    System.out.println("DEFAITE");
                }
            }
            ajoutImageCase(g);
            if (g.getValeurMax()>=OBJECTIF) {
                //Enlever g.victory() mais ajouter élement victoire visuelle
                g.victory();
                System.out.println("VICTOIRE");
            }
        } else {
            //Enlever g.gameOver() mais ajouter élement victoire visuelle
            g.gameOver();
            System.out.println("PARTIE TERMINEE");
        }
    }
    
    /**
     * Permet définir la direction dans laquelle les cases doivent être déplacées en fonction de la touche préssée
     * Puis lancement du déroulement de la partie
     * @param ke Evénement lié au clic de la souris
     */
    @FXML
    public void keyPressed(KeyEvent ke) {
        if (ke.getCode() == KeyCode.LEFT) {
            direction = GAUCHE;
        } else if (ke.getCode() == KeyCode.RIGHT) {
            direction = DROITE;
        } else if (ke.getCode() == KeyCode.UP) { 
            direction = HAUT;
        } else if (ke.getCode() == KeyCode.DOWN) { 
            direction = BAS;
        }
        deroulementPartie(g);
    }
    
    @FXML
    private void handleButtonAction(MouseEvent event) {
    }
}
    