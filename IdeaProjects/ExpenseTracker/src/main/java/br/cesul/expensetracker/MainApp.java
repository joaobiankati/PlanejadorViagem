package br.cesul.expensetracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class MainApp extends Application
{
    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/cesul/expensetracker/ExpenseView.fxml"));

        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Expense Tracker - MVVM");
        stage.show();
    }

    public static void main( String[] args )
    {launch();}
}
