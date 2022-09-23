package it.centoreluca.controller;

import it.centoreluca.MainApp;
import it.centoreluca.database.Database;
import it.centoreluca.enumerator.Macchine;
import it.centoreluca.models.Manutenzione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;

public class ControllerTabella extends Controller {

    @FXML private Label l_nomeMacchina;
    @FXML private TableView<Manutenzione> tv_tabella;
    @FXML private TableColumn<Manutenzione, String> tc_data;
    @FXML private TableColumn<Manutenzione, String> tc_nome;
    @FXML private TableColumn<Manutenzione, Integer> tc_id;
    @FXML private TableColumn<Manutenzione, ImageView> tc_filtro1;
    @FXML private TableColumn<Manutenzione, ImageView> tc_filtro2;
    @FXML private TableColumn<Manutenzione, String> tc_operatore;
    @FXML private CheckBox cb_abilitazione;
    @FXML private Label l_avvertenza;

    private final Database db = Database.getInstance();
    private final ObservableList<Manutenzione> listaManutenzioni = FXCollections.observableArrayList();
    private int macchina = -1;

    @FXML
    private void initialize() {
        tc_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tc_nome.setCellValueFactory(new PropertyValueFactory<>("macchina"));
        tc_data.setCellValueFactory(new PropertyValueFactory<>("data"));
        tc_filtro1.setCellValueFactory(new PropertyValueFactory<>("stato1"));
        tc_filtro2.setCellValueFactory(new PropertyValueFactory<>("stato2"));
        tc_operatore.setCellValueFactory(new PropertyValueFactory<>("operatore"));
    }

    @FXML
    private void indietro() {
            MainApp.setRoot("CambioFiltri", -1);
    }

    @FXML
    private void click(Event e) {
        if(cb_abilitazione.isSelected()) {
            int id = -1;
            try {
                if (e.getTarget() instanceof Text) {
                    try {
                        id = Integer.parseInt(((Text) e.getTarget()).getText());
                    } catch (NumberFormatException ignored) { }
                } else {
                    String[] parts = e.getTarget().toString().split("]'");
                    try {
                        id = Integer.parseInt(parts[1].replaceAll("'", ""));
                    } catch (NumberFormatException ignored) { }
                }
                System.out.println(id);
            } catch (Exception ignored) { }
            db.cancellaManutenzione(macchina, id);
            listaManutenzioni.clear();
            listaManutenzioni.addAll(db.getManutenzioni(macchina));
            tv_tabella.setItems(listaManutenzioni);
            tv_tabella.refresh();
        }
    }

    @FXML
    private void abilitaEliminazione() {
        l_avvertenza.setVisible(cb_abilitazione.isSelected());
    }

    @Override
    public void initParameter(Controller parentController, Stage stage, int macchina) {
        this.macchina = macchina;
        if(this.macchina == 0) {
            l_nomeMacchina.setText(Arrays.toString(Macchine.values()));
            cb_abilitazione.setVisible(false);
        } else {
            l_nomeMacchina.setText(String.valueOf(Macchine.values()[this.macchina - 1]));
        }
        listaManutenzioni.clear();
        listaManutenzioni.addAll(db.getManutenzioni(this.macchina));
        tv_tabella.setItems(listaManutenzioni);
    }
}