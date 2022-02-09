package it.centoreluca.controller;

import it.centoreluca.util.CssHelper;
import it.centoreluca.util.DialogHelper;
import it.centoreluca.util.Auth;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ControllerAccesso extends Controller {

    @FXML private AnchorPane ap_root;
    @FXML private TextField tf_username;
    @FXML private PasswordField pf_password;

    private final Auth auth = Auth.getInstance();
    private final CssHelper cssHelper = CssHelper.getInstance();
    private final DialogHelper dh = DialogHelper.getInstance();
    private ControllerAvvio parent;
    private Stage stage;

    @FXML void Accesso() {
        switch (auth.autenticazione(tf_username.getText().trim(), pf_password.getText().trim())) {
            case 0:
                cssHelper.toError(pf_password, new Tooltip("Password errata"));
                cssHelper.toDefault(tf_username);
                break;
            case 1:
                cssHelper.toDefault(tf_username);
                cssHelper.toDefault(pf_password);
                parent.callback(true, tf_username.getText().trim());
                stage.close();
                break;
            case 2:
                cssHelper.toError(tf_username, new Tooltip("Username non trovato"));
                cssHelper.toDefault(pf_password);
                break;
            case 3:
                dh.newDialog("Admin profili","ProfileManager", ap_root, this, tf_username.getText().trim());
                break;
        }
    }

    @FXML void indietro() {
        parent.callback(false, "");
        stage.close();
    }

    @Override public void initParameter(Controller parentController, Stage stage, String operatore, int param) {
        this.parent = (ControllerAvvio) parentController;
        this.stage = stage;
    }

    public void defaultOpacity() {
        FadeTransition ft = new FadeTransition(new Duration(2000), ap_root);
        ft.setFromValue(0.1);
        ft.setToValue(1.0);
        ft.play();
    }

}
