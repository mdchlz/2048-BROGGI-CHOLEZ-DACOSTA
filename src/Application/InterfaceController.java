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
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Thomas
 */
public class InterfaceController implements Initializable {
    
    Statement stmt;
    Connexion maConnexion = new Connexion();

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
    public Label etatfinal;
    public Label labelnomuser;
    public Button sendscore;
    public Button bcloseclassement;
    public TextField inputnomuser;
    public TableView<Score> tclassement;
    final ObservableList<Score> data = FXCollections.observableArrayList();
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
        String request = "SELECT * FROM grille";
        try {
            stmt = maConnexion.ObtenirConnexion().createStatement();
            ResultSet resultat = stmt.executeQuery(request);
            boolean ok = false;
            while (resultat.next()) {
                g.ajoutCase(resultat.getInt("Colonne"),resultat.getInt("Ligne"),resultat.getInt("Valeur"));
                ok = true;
            }
            if (!ok) {
                boolean b = g.nouvelleCase();
                b = g.nouvelleCase();   
            }
            System.out.println(g);
            ajoutImageCase(g);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
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
                    gameOver();
                }
            }
            ajoutImageCase(g);
            if (g.getValeurMax()>=OBJECTIF) {
                //Enlever g.victory() mais ajouter élement victoire visuelle
                victory();
            }
        } else {
            //Enlever g.gameOver() mais ajouter élement victoire visuelle
            gameOver();
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
    
    public void sauvegardeGrille() {
        razdatabase();
        for(Case c : g.getGrille()) {
            int cx = c.getX();
            int cy = c.getY();
            int cvalue = c.getValeur();
            String request = "INSERT INTO grille (colonne, ligne, valeur) VALUES ("+cx+","+cy+","+cvalue+")";
            try {
                stmt = maConnexion.ObtenirConnexion().createStatement();
                stmt.executeUpdate(request);
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        System.exit(0);
    }
    
    public void razdatabase () {
        String request = "TRUNCATE TABLE `grille`";
        try {
            stmt = maConnexion.ObtenirConnexion().createStatement();
            stmt.executeUpdate(request);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void sauvegardeScore() {
        String nom = inputnomuser.getText();
        int valeur = g.getValeurMax();
        System.out.println(nom);
        System.out.println(valeur);
        String request = "INSERT INTO score (pseudo, valeur) VALUES ('"+nom+"',"+valeur+")";
        try {
            stmt = maConnexion.ObtenirConnexion().createStatement();
            stmt.executeUpdate(request);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        System.exit(0);
    }
    
    /**
     * Méthode permettant d'afficher un message en cas de victoire et de fermer l'application ensuite
     */
    public void victory() {
        razdatabase();
        grille.setOpacity(0.1);
        etatfinal.setText("VICTOIRE");
        labelnomuser.setVisible(true);
        inputnomuser.setVisible(true);
        sendscore.setVisible(true);
    }

    /**
     * Méthode permettant d'afficher un message en cas de défaite et de fermer l'aplication ensuite 
     */
    public void gameOver() {
        razdatabase();
        grille.setOpacity(0.1);
        etatfinal.setText("DEFAITE");
        labelnomuser.setVisible(true);
        inputnomuser.setVisible(true);
        sendscore.setVisible(true);
    }
    
    public void classement() {
        tclassement.setVisible(true);
        bcloseclassement.setVisible(true);
        tclassement.setEditable(true);
        TableColumn Pseudo = new TableColumn("Pseudonyme");
        Pseudo.setMinWidth(214);
        Pseudo.setCellValueFactory(
                new PropertyValueFactory<Score, String>("pseudo")
        );
        TableColumn Score = new TableColumn("Score");
        Score.setMinWidth(115);
        Score.setCellValueFactory(
                new PropertyValueFactory<Score, Integer>("valeur")
        );
        String request = "SELECT * FROM score ORDER BY valeur DESC";
        try {
            stmt = maConnexion.ObtenirConnexion().createStatement();
            ResultSet resultat = stmt.executeQuery(request);
            while (resultat.next()) {
                data.add(new Score(resultat.getString("pseudo"),resultat.getInt("valeur")));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        tclassement.setItems(data);
        tclassement.getColumns().addAll(Pseudo, Score);
    }
    
    public void closeclassement() {
        tclassement.setVisible(false);
        bcloseclassement.setVisible(false);
    }
    
}
    