package it.centoreluca.controller;

import it.centoreluca.MainApp;
import it.centoreluca.database.Database;
import it.centoreluca.util.DialogHelper;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ControllerAvvio extends Controller {

    @FXML private AnchorPane ap_root;
    @FXML private Button b_gestioneProfili;

    private final DialogHelper dh = new DialogHelper();
    private final Database db = Database.getInstance();

    @Override
    public void initParameter(Controller parentController, Stage stage, int param) {
        if(MainApp.operatore.equals("admin")) {
            b_gestioneProfili.setVisible(true);
        }
    }

    @Override
    public void defaultOpacity() {
        FadeTransition ft = new FadeTransition(new Duration(2000), ap_root);
        ft.setFromValue(0.1);
        ft.setToValue(1.0);
        ft.play();
    }

    @FXML
    private void manutenzioneMacchine() { MainApp.setRoot("CambioFiltri", -1); }

    @FXML
    private void ricevimentoLotti() { MainApp.setRoot("RicevimentoLotti", -1); }

    @FXML
    private void apriDialogProfili() {
        dh.newDialog("Admin profili","ProfileManager", ap_root, this);
    }

    @FXML
    void exit() {
        db.close();
        Platform.exit();
        System.exit(0);
    }

}
