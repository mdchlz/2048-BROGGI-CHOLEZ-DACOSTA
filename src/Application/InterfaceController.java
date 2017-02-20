/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Thomas
 */
public class InterfaceController {
    
    @FXML
    private GridPane grid;
    @FXML
    private Label Score;
    @FXML
    private Label Scoreint;
    @FXML
    private Label Best;
    @FXML
    private Label Bestint;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public InterfaceController() {
        
    }
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        nouvellePartie();
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    public void nouvellePartie() {
        Grille g = new Grille();
        boolean b = g.nouvelleCase();
        b = g.nouvelleCase();
        for(Case c : g.getGrille()) {
            int cx = c.getX();
            int cy = c.getY();
            int cvalue = c.getValeur();
            String chemin = "file:Images/"+cvalue+".png";
            ImageView imgview = new ImageView();
            Image image = new Image(chemin);
            imgview.setImage(image);
            grid.add(imgview, cx, cy);
            System.out.println(cx+", "+cy+", "+cvalue);
        }
         
    }
    
}

