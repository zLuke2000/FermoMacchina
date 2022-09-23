package it.centoreluca.controller;

import it.centoreluca.MainApp;
import it.centoreluca.util.CssHelper;
import it.centoreluca.util.Auth;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;

public class ControllerAccesso extends Controller {

    @FXML private AnchorPane ap_root;
    @FXML private TextField tf_username;
    @FXML private PasswordField pf_password;

    private final Auth auth = Auth.getInstance();
    private final CssHelper cssHelper = CssHelper.getInstance();

    /*
     * case 0 e 3: Autenticazione livello 1 e 2
     * case 1: Password errata
     * case 2: Username non trovato
     */
    @FXML
    void Accesso() {
        switch (auth.autenticazione(tf_username.getText().trim(), pf_password.getText().trim())) {
            case 0:
            case 3:
                MainApp.operatore = tf_username.getText().trim();
                MainApp.setRoot("Avvio", -1);
                break;
            case 1:
                cssHelper.toError(pf_password, new Tooltip("Password errata"));
                cssHelper.toDefault(tf_username);
                break;
            case 2:
                cssHelper.toError(tf_username, new Tooltip("Username non trovato"));
                cssHelper.toDefault(pf_password);
                break;
        }
    }

    @FXML
    void chiudi() {
        MainApp.stage.close();
    }
}
