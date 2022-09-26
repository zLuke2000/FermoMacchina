package it.centoreluca.controller;

import it.centoreluca.MainApp;
import it.centoreluca.database.Database;
import it.centoreluca.enumerator.Macchine;
import it.centoreluca.enumerator.Mesi;
import it.centoreluca.models.Manutenzione;
import it.centoreluca.util.DateHelper;
import it.centoreluca.util.ExcelHelper;
import it.centoreluca.util.Impostazioni;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class ControllerCambioFiltro extends Controller {

    @FXML private AnchorPane ap_root;
    @FXML public Button b_visualizzaExcel;
    @FXML public ToggleGroup selettoreMacchina;
    @FXML private Label l_titolo;
    @FXML private RadioButton rb_macchina1;
    @FXML private RadioButton rb_macchina2;
    @FXML private RadioButton rb_macchina3;
    @FXML private RadioButton rb_macchina4;
    @FXML private RadioButton rb_macchina5;
    @FXML private RadioButton rb_macchina6;
    @FXML private RadioButton rb_macchina7;
    @FXML private CheckBox cb_filtro1;
    @FXML private CheckBox cb_filtro2;
    @FXML private TextField tf_giorno;
    @FXML private ComboBox<Mesi> cb_mese;
    @FXML private TextField tf_anno;
    @FXML private TextField tf_ora;
    @FXML private TextField tf_minuto;

    private final ExcelHelper excelHelper = ExcelHelper.getInstance();
    private final Database db = Database.getInstance();
    private final DateHelper dateHelper = DateHelper.getInstance();
    private final Desktop desktop = Desktop.getDesktop();
    private RadioButton[] rb_macchine;
    private File file;

    @FXML
    private void initialize() {
        // Inizializzo il ComboBox con i mesi
        dateHelper.impostaCBMesi(cb_mese);

        rb_macchine = new RadioButton[]{rb_macchina1, rb_macchina2, rb_macchina3, rb_macchina4, rb_macchina5, rb_macchina6, rb_macchina7};
        // Inizializzo i nomi delle macchine con quelli impostati
        for(int i=0; i<rb_macchine.length; i++) {
            rb_macchine[i].setText(String.valueOf(Macchine.values()[i]));
        }
    }

    @FXML
    private void dataAutomatica() {
        dateHelper.dataAutomatica(tf_giorno, cb_mese, tf_anno, tf_ora, tf_minuto);
    }

    @FXML
    private void apriEventiSel() {
        for(int i=0; i<rb_macchine.length; i++) {
            if(rb_macchine[i].isSelected()) {
                MainApp.setRoot("TabellaCambioFiltri", i+1);
            }
        }
    }

    @FXML
    void apriEventi() {
        MainApp.setRoot("TabellaCambioFiltri", 0);
    }

    @FXML
    private void conferma() {

        Manutenzione manutenzione = new Manutenzione();

        // Imposto la macchina selezionata
        for(int i=0; i<rb_macchine.length; i++) {
            if(rb_macchine[i].isSelected()) {
                manutenzione.setMacchina(Macchine.values()[i]);
                manutenzione.setId(i+1);
            }
        }

        // Imposto i filtri cambiati
        if (cb_filtro1.isSelected()) {
            manutenzione.setFiltro1(true);
        }
        if (cb_filtro2.isSelected()) {
            manutenzione.setFiltro2(true);
        }

        // Imposto la data selezionata
        if (dateHelper.check(tf_giorno, cb_mese, tf_anno, tf_ora, tf_minuto, manutenzione)) {
            manutenzione.setOperatore(MainApp.operatore);
            db.save(manutenzione);
        }
    }

    @FXML
    private void esportaExcel() {
        Impostazioni imp = new Impostazioni();
        String path = imp.leggiImpostazione("excelPath") + "\\" + dateHelper.timestamp() + ".xlsx";
        System.out.println(path);
        file = new File(path);
        if(excelHelper.esportaXLSX(db.getManutenzioni(0), file)) {
            b_visualizzaExcel.setText("Manutenzione salvata correttamente in: " + path + "\nPremere QUI per aprire il file");
            b_visualizzaExcel.setVisible(true);
        }
    }

    @FXML
    private void visualizzaExcel() {
        try {
            desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void indietro() {
        MainApp.setRoot("Avvio", -1);
    }

    @FXML
    private void exit() {
        System.exit(0);
    }

    @Override
    public void initParameter(Controller parentController, Stage stage, int param) {
        // Metto il nome utente come titolo
        l_titolo.setText(l_titolo.getText() + " - " + MainApp.operatore);
    }

    @FXML
    private void selezionaTutti() {
        cb_filtro1.setSelected(true);
        cb_filtro2.setSelected(true);
    }
}