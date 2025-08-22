package br.cesul.planejadorViagens.controller;

// Essa sera a unica classe de CONTROLLER do projeto
// A sua função é ligar a  interace grafica (View) com a lógica de negocio

// intrage com os elementos do FXML
// Coleta os dados preenchidos pelo usuario
// Converte textos para os tipos corretos
// Chama o service (que valida e salva)
// É o responsavel por atualizar a interface com os dados vindos da service
// Responde a eventos da View

import br.cesul.planejadorViagens.model.Viagem;
import br.cesul.planejadorViagens.services.PlanejamentoService;
import com.sun.javafx.binding.StringFormatter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TripController {
    // Os elementos do FXML ( ou seja o fx:id)
    @FXML private DatePicker dataInicioPicker;
    @FXML private DatePicker dataFimPicker;
    @FXML private TextField destinoField;
    @FXML private TextField orcamentoField;
    @FXML private Button btnAdicionar;
    @FXML private Button btnEditar;
    @FXML private TableView<Viagem> viagensTable;
    @FXML private TableColumn<Viagem, String> colCidade;
    @FXML private TableColumn<Viagem, String> colIni;
    @FXML private TableColumn<Viagem, String> colFim;
    @FXML private TableColumn<Viagem, Number> colCusto;
    @FXML private Label lblTotal;

    private Viagem viagemEmEdicao = null;
    private boolean modoEdicao = false;

    private final PlanejamentoService service = new PlanejamentoService();

    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    @FXML
    private void initialize(){
        // Define COMO cada coluna extrai informação da entidade

        // Se você so remover uma String simples, o JFX nao consegue saber se o valor mudou depois
        // Por isso, se devolver uma SimpleStringProperty ele pode observar aquela propriedade e a
        // Atualizar a interface automaticamente.
        colCidade.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getDestino()));

        colIni.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getDataInicio().format(fmt)));

        colFim.setCellValueFactory(c ->
                new  javafx.beans.property.SimpleStringProperty(c.getValue().getDataFim().format(fmt)));

        colCusto.setCellValueFactory(c ->
                new  javafx.beans.property.SimpleDoubleProperty(c.getValue().getCusto()));

        // Preencher a tabela com dados ja gravados
        // Chamando o service para buscar as viagens

        viagensTable.getSelectionModel().selectedItemProperty().addListener((obs, oldViagem, novaViagem) -> {
            if(novaViagem != null){
                destinoField.setText(novaViagem.getDestino());
                dataInicioPicker.setValue(novaViagem.getDataInicio());
                dataFimPicker.setValue(novaViagem.getDataFim());
                orcamentoField.setText(String.format(Locale.US, "%.2f", novaViagem.getCusto()));
            }
        });

        viagensTable.setItems(FXCollections.observableArrayList(service.listar()));
        atualizarTotal();

        viagensTable.getSelectionModel().selectedItemProperty().addListener((obs, oldV, nova) -> {

        });

        Viagem viagemSelecionada = viagensTable.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void editar(){
        Viagem selecionada = viagensTable.getSelectionModel().getSelectedItem();
        if(selecionada == null){
            mostrarErro("Selecione uma viagem na tabela para editar");
            return;
        }

        try{
            double custo = Double.parseDouble(orcamentoField.getText().replace(",", "."));

        service.atualizar(selecionada, destinoField.getText(), dataInicioPicker.getValue(), dataFimPicker.getValue(), custo);

        viagensTable.getItems().setAll(service.listar());
        atualizarTotal();
        limparCampos();
        viagensTable.getSelectionModel().clearSelection();

        }catch(Exception e){
            mostrarErro(e.getMessage());
        }
    }

    @FXML
    public void adicionar(){
        try{
            // Conversão de dados
            double custo = Double.parseDouble(orcamentoField.getText().replace(",", "."));
            service.adicionar(destinoField.getText(), dataInicioPicker.getValue(), dataFimPicker.getValue(), custo);

            viagensTable.getItems().setAll(service.listar());
            atualizarTotal();
            limparCampos();

        }catch (Exception ex){
            mostrarErro(ex.getMessage());
        }
    }

    @FXML
    public void remover(){      //em (on action) colocar o metodo remover)"
        Viagem v = viagensTable.getSelectionModel().getSelectedItem();
        service.remover(v);
        atualizarTotal();
        viagensTable.getItems().setAll(service.listar());
    }

    private void limparCampos(){
        destinoField.clear();
        orcamentoField.clear();
        dataInicioPicker.setValue(null);
        dataFimPicker.setValue(null);
    }

    private void mostrarErro(String msg){
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }

    private void atualizarTotal(){
        lblTotal.setText("Total: R$ " + String.format( "%.2f", service.totalGasto()));
    }

}
