package it.centoreluca.controller;

import it.centoreluca.MainApp;
import it.centoreluca.database.Database;
import it.centoreluca.util.DialogHelper;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ControllerAvvio extends Controller {

    @FXML private AnchorPane ap_root;

    private final DialogHelper dh = DialogHelper.getInstance();
    private final Database db = Database.getInstance();

    @FXML void manutenzioneMacchine() {
        dh.newDialog("Accesso","AccessoDialog", ap_root, this, "");
    }

    @FXML void ricevimentoLotti() {
        MainApp.setRoot("RicevimentoLotti", -1, null);
    }

    @FXML void exit() {
        db.close();
        Platform.exit();
        System.exit(0);
    }

    void callback(boolean status, String operatore) {
        if(status) {
            MainApp.setRoot("NuovaManutenzione", -1, operatore);
        } else {
            FadeTransition ft = new FadeTransition(new Duration(2000), ap_root);
            ft.setFromValue(0.1);
            ft.setToValue(1.0);
            ft.play();
        }
    }

    @Override public void initParameter(Controller parentController, Stage stage, String operatore, int param) { }

}
