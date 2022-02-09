package it.centoreluca.controller;

import it.centoreluca.MainApp;
import it.centoreluca.database.Database;
import it.centoreluca.enumerator.Macchine;
import it.centoreluca.enumerator.Mesi;
import it.centoreluca.models.Manutenzione;
import it.centoreluca.util.DateHelper;
import it.centoreluca.util.ExcelHelper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ControllerNuovaManutenzione extends Controller {

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
    private File file;
    private String operatore = "";

    @FXML void initialize() {
        // Inizializzo il ComboBox con i mesi
        dateHelper.impostaCBMesi(cb_mese);

        // Inizializzo i nomi delle macchine con quelli impostati
        rb_macchina1.setText(String.valueOf(Macchine.values()[0]));
        rb_macchina2.setText(String.valueOf(Macchine.values()[1]));
        rb_macchina3.setText(String.valueOf(Macchine.values()[2]));
        rb_macchina4.setText(String.valueOf(Macchine.values()[3]));
        rb_macchina5.setText(String.valueOf(Macchine.values()[4]));
        rb_macchina6.setText(String.valueOf(Macchine.values()[5]));
        rb_macchina7.setText(String.valueOf(Macchine.values()[6]));
    }

    @FXML void dataAutomatica() {
        dateHelper.dataAutomatica(tf_giorno, cb_mese, tf_anno, tf_ora, tf_minuto);
    }

    @FXML void apriEventiSel() {
        if (rb_macchina1.isSelected()) {
            MainApp.setRoot("TabellaManutenzioni", 1, operatore);
        } else if (rb_macchina2.isSelected()) {
            MainApp.setRoot("TabellaManutenzioni", 2, operatore);
        } else if (rb_macchina3.isSelected()) {
            MainApp.setRoot("TabellaManutenzioni", 3, operatore);
        } else if (rb_macchina4.isSelected()) {
            MainApp.setRoot("TabellaManutenzioni", 4, operatore);
        } else if (rb_macchina5.isSelected()) {
            MainApp.setRoot("TabellaManutenzioni", 5, operatore);
        } else if (rb_macchina6.isSelected()) {
            MainApp.setRoot("TabellaManutenzioni", 6, operatore);
        } else if (rb_macchina7.isSelected()) {
            MainApp.setRoot("TabellaManutenzioni", 7, operatore);
        }
    }

    @FXML void apriEventi() {
        MainApp.setRoot("TabellaManutenzioni", 0, operatore);
    }

    @FXML void conferma() {

        Manutenzione manutenzione = new Manutenzione();

        // Imposto la macchina selezionata
        if (rb_macchina1.isSelected()) {
            manutenzione.setMacchina(Macchine.values()[0]);
            manutenzione.setId(1);
        } else if (rb_macchina2.isSelected()) {
            manutenzione.setMacchina(Macchine.values()[1]);
            manutenzione.setId(2);
        } else if (rb_macchina3.isSelected()) {
            manutenzione.setMacchina(Macchine.values()[2]);
            manutenzione.setId(3);
        } else if (rb_macchina4.isSelected()) {
            manutenzione.setMacchina(Macchine.values()[3]);
            manutenzione.setId(4);
        } else if (rb_macchina5.isSelected()) {
            manutenzione.setMacchina(Macchine.values()[4]);
            manutenzione.setId(5);
        } else if (rb_macchina6.isSelected()) {
            manutenzione.setMacchina(Macchine.values()[5]);
            manutenzione.setId(6);
        } else if (rb_macchina7.isSelected()) {
            manutenzione.setMacchina(Macchine.values()[6]);
            manutenzione.setId(7);
        }

        // Imposto i filtri cambiati
        if (cb_filtro1.isSelected()) {
            manutenzione.setFiltro1(true);
        }
        if (cb_filtro2.isSelected()) {
            manutenzione.setFiltro2(true);
        }

        if (dateHelper.check(tf_giorno, cb_mese, tf_anno, tf_ora, tf_minuto, manutenzione)) {
            manutenzione.setOperatore(operatore);
            db.save(manutenzione);
            esportaExcel();
        }
    }

    @FXML private void esportaExcel() {
        String path = "D:/SSD/Desktop/ManutenzioneMacchine " + dateHelper.timestamp() + ".xlsx";
        file = new File(path);
        if(excelHelper.esportaXLSX(db.getManutenzioni(0), file)) {
            b_visualizzaExcel.setText("Manutenzione salvata correttamente in: " + path + "\nPremere QUI per aprire il file");
            b_visualizzaExcel.setVisible(true);
        }
    }

    @FXML public void visualizzaExcel() {
        try {
            desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML void indietro() {
        MainApp.setRoot("Avvio", -1, "");
    }

    @FXML public void exit() {
        System.exit(0);
    }

    @Override public void initParameter(Controller parentController, Stage stage, String operatore, int param) {
        this.operatore = operatore;
        // Metto il nome utente come titolo
        l_titolo.setText(l_titolo.getText() + " - " + this.operatore);
    }

}