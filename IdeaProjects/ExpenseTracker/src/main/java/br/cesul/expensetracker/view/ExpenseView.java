package br.cesul.expensetracker.view;

import br.cesul.expensetracker.model.Expense;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.NumberStringConverter;

import java.awt.*;
import java.time.LocalDate;

// "Controller" vinculado ao FXML
// Faz APENAS a cola entre componenetes da tela e o ViewModel
// Aqui não se implementa regras de negócio
public class ExpenseView {
    @FXML private TextField descriptionField;
    @FXML private TextField amountField;
    @FXML private DatePicker datePicker;
    @FXML private Button addButton;
    @FXML private TableView<Expense> expenseTable;
    @FXML private TableColumn<Expense, LocalDate> dateCol;
    @FXML private TableColumn<Expense, String> descCol;
    @FXML private TableColumn<Expense, Number> amountCol;
    @FXML private Button deleteButton;
    @FXML private Label totalLabel;

    @FXML private void initialize(){
        // Bindings de entrada
        // Campo de descrição -> SimpleProperty
        descriptionField.textProperty().bindBidirectional();

        TextFormatter<Number> amountFormatter = new TextFormatter<>(new NumberStringConverter());
        amountField.setTextFormatter(amountFormatter);

        // Fazer um binding bidirecional entre o numberProperty que vem da (VM) e o valueProperty que vem do (Formatter)
        Bindings.bindBidirectional();

        // Binding do DatePicker
        datePicker.valueProperty().bindBidirectional();

        // Configuração da tabela
        // Dizer qual getter do expense abastece cada coluna
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        // Ligar a lista observavel do viewModel diretamente na tableView ou seja (popular os dados)
        expenseTable.setItems();

        // Mostrar a label formatada com o valor total ou seja ira se "atualizar sozinha"
        totalLabel.textProperty()
    }
}
