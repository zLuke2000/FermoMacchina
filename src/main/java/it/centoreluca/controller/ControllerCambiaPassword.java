package it.centoreluca.controller;

import it.centoreluca.util.CssHelper;
import it.centoreluca.util.Auth;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

public class ControllerCambiaPassword extends Controller {

    @FXML private TextField tf_username;
    @FXML private PasswordField pf_nuovaPassword1;
    @FXML private PasswordField pf_nuovaPassword2;

    private final Auth auth = Auth.getInstance();
    private final CssHelper css = CssHelper.getInstance();
    private Stage stage;
    private ControllerProfileManager parent;

    @FXML
    void conferma() {
        switch(auth.aggiornamentoPassword(tf_username.getText().trim(), pf_nuovaPassword1.getText().trim(), pf_nuovaPassword2.getText().trim())) {
            case 0:
                css.toError(tf_username, new Tooltip("Operatore inesistente"));
                css.toDefault(pf_nuovaPassword1);
                css.toDefault(pf_nuovaPassword2);
                break;
            case 1:
                indietro();
                break;
            case 2:
                css.toError(pf_nuovaPassword1, new Tooltip("Le password non sono identiche"));
                css.toError(pf_nuovaPassword2, new Tooltip("Le password non sono identiche"));
                break;
            case 3:
                css.toError(pf_nuovaPassword1, new Tooltip("Minimo 4 caratteri"));
                css.toError(pf_nuovaPassword2, new Tooltip("Minimo 4 caratteri"));
                break;
        }
    }

    @FXML
    void indietro() {
        stage.close();
        parent.defaultOpacity();
    }

    @Override
    public void initParameter(Controller parentController, Stage stage, int param) {
        this.parent = (ControllerProfileManager) parentController;
        this.stage = stage;
    }
}