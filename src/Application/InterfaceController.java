/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Thomas
 */
public class InterfaceController {
    
    @FXML
    private GridPane Grid;
    @FXML
    private Label griid;
    @FXML
    private Label Score;
    @FXML
    private Label Scoreint;
    @FXML
    private Label Best;
    @FXML
    private Label Bestint;
    @FXML
    private ImageView imge14;
    @FXML
    private ImageView imge23;

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
        ajoutTuile();
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    public void ajoutTuile() {
        //String tab[] = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p"};
        /*
        Random rand = new Random();
        for (int x = 0; x <= 1; x++) {
            Random ra = new Random();
            int tuiles = (1 + ra.nextInt(2)) * 2;
            String chemin = "file:/C:/Users/Thomaas/Pictures/empty/"+tuiles+".png";
            ImageView img = new ImageView();
            a.getChildren().add(img);
            Image image = new Image(chemin);
            img.setId("imga");
            img.setImage(image);
            System.out.println(chemin);
            System.out.println(tuiles);
        } */
        Button button = new Button("CAMARCHE?");
        GridPane.setConstraints(button,0,0);
        Bestint.setText(String.valueOf(522));
    }
    
}

