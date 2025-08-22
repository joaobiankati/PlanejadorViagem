package br.cesul.planejadorViagens;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception{
        var loader = new FXMLLoader(getClass().getResource(
                "/br.cesul.planejadorViagens/trip_view.fxml"));

        Parent root = loader.load();

        stage.setTitle("Trip Planner - MVC");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main( String[] args ) {launch(args);}
}
