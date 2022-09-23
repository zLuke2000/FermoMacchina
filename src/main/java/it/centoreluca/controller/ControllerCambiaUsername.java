package it.centoreluca.controller;

import it.centoreluca.util.CssHelper;
import it.centoreluca.util.Auth;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

public class ControllerCambiaUsername extends Controller {

    @FXML private TextField tf_vecchioUsername;
    @FXML private TextField tf_nuovoUsername;

    private Stage stage;
    private final Auth auth = Auth.getInstance();
    private final CssHelper css = CssHelper.getInstance();
    private ControllerProfileManager parent;

    @FXML
    void conferma() {
        switch(auth.aggiornamentoNome(tf_vecchioUsername.getText().trim(), tf_nuovoUsername.getText().trim())) {
            case 0:
                css.toError(tf_vecchioUsername, new Tooltip("Utente inesistente"));
                css.toDefault(tf_nuovoUsername);
                break;
            case 1:
                indietro();
                break;
            case 2:
                css.toError(tf_nuovoUsername, new Tooltip("Username gi\u00E0 in uso"));
                css.toDefault(tf_vecchioUsername);
                break;
            case 3:
                css.toError(tf_nuovoUsername, new Tooltip("Minimo 4 caratteri"));
                css.toDefault(tf_vecchioUsername);
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
