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
     * Affichage du mot "score" dans l'interface
     */
    @FXML
    private Label score;
    /**
     * Affichage du score du joueur
     */
    @FXML
    private Label scoreint;
    /**
     * Affichage du mot "best" dans l'interface
     */
    @FXML
    private Label best;
    /**
     * Affichage du meilleur score
     */
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
     * Utilisé dans la mise en place l'intelligence artificielle
     */
    public Label etatfinal;
    public Label labelnomuser;
    public Button sendscore;
    public Button bcloseclassement;
    public TextField inputnomuser;
    /** 
     * Affichage de la table contenant le classement des joueurs de l'application
     */
    public TableView<Score> tclassement;
    
    final ObservableList<Score> data = FXCollections.observableArrayList();
    /**
     * Nouvelle grille pour lancer une nouvelle partie
     */
    Grille g;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    /**
     * Permet d'initialiser une nouvelle partie :
     *  - création des deux nouvelles cases au début de la partie
     *  - ajout des images associées aux cases créées
     */
    public void initPartie () {
        
        grille.setOpacity(1);
        etatfinal.setVisible(false);
        labelnomuser.setVisible(false);
        inputnomuser.setVisible(false);
        sendscore.setVisible(false);
        tclassement.setVisible(false);
        bcloseclassement.setVisible(false);
        
        g = new Grille();
        
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
                victory();
            }
        } else {
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
    
    /**
     * Méthode permettant de sauvegarder la grille représentant la partie en cours
     * Pour toutes les cases présentes dans la grille : leurs coordonnées et leur valeur sont stockées puis envoyées dans la base de données
     * Cela n'est possible que si la connexion avec la base de données peut être établie
     */
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
    
    /**
     * Méthode permettant de remettre à zéro la base de données (il faut être connecté)
     */
    public void razdatabase () {
        String request = "TRUNCATE TABLE `grille`";
        try {
            stmt = maConnexion.ObtenirConnexion().createStatement();
            stmt.executeUpdate(request);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    /**
     * Méthode permmettant de sauvegarder le score du joueur une fois qu'il a terminé sa partie
     * Le joueur doit entrer son nom et ensuite on récupère le score qu'il a fait
     * Et on envoie les données à la base de données (il faut y être connecté)
     */
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
        etatfinal.setVisible(true);
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
        etatfinal.setVisible(true);
        etatfinal.setText("DEFAITE");
        labelnomuser.setVisible(true);
        inputnomuser.setVisible(true);
        sendscore.setVisible(true);
    }
    
    /**
     * Méthode qui permet d'afficher le classement des joueurs de l'application
     * Récupère les données (nom, score) et les ajoute à la base de données dans la table score
     */    
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
    
    /**
     * Permet de fermer le classement lorsque l'on clique sur le bouton retour qui se trouve en dessous du classement dans l'interface graphique
     */
    public void closeclassement() {
        tclassement.setVisible(false);
        bcloseclassement.setVisible(false);
    }
    
    /**
     * Méthode permettant d'effectuer le déplacement en fonction du déplacement conseillé par la méthode testAide()
     * Suite au déplacement, une nouvelle case est générée comme pour un déplacement choisi par l'utilisateur
     */
    public void aideDeplacement() {
        Boolean b;
        Boolean b2;
        
        direction = testAide();
        b2 = g.lanceurDeplacerCases(direction);
        b = g.nouvelleCase();
        if (!b) {
            gameOver();
        }
        ajoutImageCase(g);
        if (g.getValeurMax()>=OBJECTIF) {
            victory();
        }
    }
    
    /**
     * Méthode permettant l'appel à la méthode testIA() pour ensuite pouvoir faire une partie complète grâce à l'IA dans la méthode jeuxIA()
     */
    public void deplacementCompletIA() {
        Boolean b;
        
        direction = testIA();
        g.lanceurDeplacerCases(direction);
        b = g.nouvelleCase();
        if (!b) {
            gameOver();
        }
        ajoutImageCase(g);
        if (g.getValeurMax()>=OBJECTIF) {
            victory();
        }
    }
    
    /**
     * Méthode utilisant la méthode deplacementCompletIA() pour le déroulement d'une partie normale
     * C'est à dire tant que la partie n'est pas finie, l'IA effectue des coups
     */
    public void jeuxIA() {
        while (!g.partieFinie()) {
            deplacementCompletIA();
        }
        if (g.getValeurMax()>=OBJECTIF) {
            victory();
        } else {
            gameOver();
        }
    }
    
    /**
     * Méthode permettant de définir le coup le plus approprié pour l'IA
     * L'heuristique est faite de façon à ce que les déplacements soient créés pour une profondeur de 2
     * Les déplacements privilégiés sont ceux vers le haut et ceux vers la gauche
     * Enfin, la méthode retient le coup qui a permis d'obtenir le moins de cases possibles
     * @return Entier correspondant à la direction choisie par l'IA : HAUT=1, DROITE=2, BAS=-1, GAUCHE=-2
     */
    public int testAide() {
        int nbreCases[] = new int[4];
        int indexmincases;
        
        //1er test --> gauche & gauche
        Grille copy = (Grille) g.clone();
        copy.lanceurDeplacerCases(GAUCHE);
        copy.lanceurDeplacerCases(GAUCHE);
        nbreCases[0] = copy.getGrille().size();
        
        //2ième test --> gauche & haut
        copy = (Grille) g.clone();
        copy.lanceurDeplacerCases(GAUCHE);
        copy.lanceurDeplacerCases(HAUT);
        nbreCases[1] = copy.getGrille().size();
        
        //3ième test --> haut & gauche
        copy = (Grille) g.clone();
        copy.lanceurDeplacerCases(HAUT);
        copy.lanceurDeplacerCases(GAUCHE);
        nbreCases[2] = copy.getGrille().size();
        
        //4ième test --> haut & haut
        copy = (Grille) g.clone();
        copy.lanceurDeplacerCases(HAUT);
        copy.lanceurDeplacerCases(HAUT);
        nbreCases[3] = copy.getGrille().size();
        
        indexmincases = minimumCases(nbreCases);
        if ((indexmincases == 0) || (indexmincases == 1)) {
            direction = GAUCHE;
        } else {
            direction = HAUT;
        }
        
        return direction;
    }
    
    public int testIA() {
        int nbreCases[] = new int[2];
        int indexmincases;
        //on effectue les mêmes tests
        //cependant pour un soucis de mémoire étant donné que le test s'effectue sur toute la partie
        //on ne peut tester sur une profondeur de 2
        
        //1er test --> gauche
        Grille copy = (Grille) g.clone();
        copy.lanceurDeplacerCases(GAUCHE);
        nbreCases[0] = copy.getGrille().size();

        //2ième test --> haut
        copy = (Grille) g.clone();
        copy.lanceurDeplacerCases(HAUT);
        nbreCases[1] = copy.getGrille().size();
        
        indexmincases = minimumCases(nbreCases);
        if (indexmincases == 0) {
            direction = GAUCHE;
        } else {
            direction = HAUT;
        }
        
        return direction;
    }
    
    public int minimumCases(int [] tab) {
        int min = 16;
        int i;
        int index = 0;
        for (i = 0; i < tab.length; i++)	{
            if (tab[i] < min)	{
                min = tab[i];
                index = i;
            }
        }	
        return index;
    }
    
}
    