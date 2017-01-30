/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage Stage;
    private AnchorPane root;

    @Override
    public void start(Stage Stage) {
        this.Stage = Stage;
        this.Stage.setTitle("2048");

        initInterface();
    }

    /**
     * Initializes the root layout.
     */
    public void initInterface() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("Interface.fxml"));
            root = (AnchorPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(root);
            Stage.setScene(scene);
            Stage.show();
            
            // Give the controller access to the main app.
            InterfaceController controller = loader.getController();
            controller.setMainApp(this);
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getStage() {
        return Stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
