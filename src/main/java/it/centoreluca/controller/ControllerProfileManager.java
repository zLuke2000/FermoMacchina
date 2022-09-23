package it.centoreluca.controller;

import it.centoreluca.util.CssHelper;
import it.centoreluca.util.DialogHelper;
import it.centoreluca.util.Auth;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ControllerProfileManager extends Controller {

    @FXML private AnchorPane ap_root;
    @FXML private Button b_reset;
    @FXML private ToggleButton tb_abilitazioneReset;
    @FXML private TextField tf_nuovoUsername;
    @FXML private PasswordField pf_nuovaPassword;

    private Controller parent;
    private Stage stage;
    private final Auth auth = Auth.getInstance();
    private final CssHelper css = CssHelper.getInstance();
    private final DialogHelper dh = new DialogHelper();

    @Override
    public void initParameter(Controller parentController, Stage stage, int param) {
        this.parent = parentController;
        this.stage = stage;
    }

    @FXML
    private void salvaUtente() {
        boolean check = true;

        if(tf_nuovoUsername.getText().trim().length() < 4) {
            css.toError(tf_nuovoUsername, new Tooltip("Inserire almeno 4 caratteri"));
            check = false;
        }
        if (tf_nuovoUsername.getText().trim().equals("admin")) {
            css.toError(tf_nuovoUsername, new Tooltip("Username riservato!"));
            check = false;
        }
        if(pf_nuovaPassword.getText().trim().length() < 4) {
            css.toError(pf_nuovaPassword, new Tooltip("Inserire almeno 4 caratteri"));
            check = false;
        }

        if(check) {
            auth.aggiungiUtente(tf_nuovoUsername.getText().trim(), pf_nuovaPassword.getText().trim());
            tf_nuovoUsername.setText("");
            pf_nuovaPassword.setText("");
        }
    }

    @FXML
    private void resetPassword() {
        auth.reset();
        indietro();
    }

    @FXML
    private void indietro() {
        stage.close();
        parent.defaultOpacity();
    }

    @FXML
    private void modificaNomeUtente() {
        dh.newDialog("Admin profili - Username","CambiaUsernameDialog", ap_root, this);
    }

    @FXML
    private void modificaPassword() {
        dh.newDialog("Admin profili - Password","CambiaPasswordDialog", ap_root, this);
    }

    @FXML
    private void abilitaReset() {
        tb_abilitazioneReset.setTextAlignment(TextAlignment.CENTER);
        if(tb_abilitazioneReset.isSelected()) {
            b_reset.setDisable(false);
            tb_abilitazioneReset.setText("Disabilita\nRESET");
        } else {
            b_reset.setDisable(true);
            tb_abilitazioneReset.setText("Abilita\nRESET");
        }
    }

    @Override
    public void defaultOpacity() {
        FadeTransition ft = new FadeTransition(new Duration(2000), ap_root);
        ft.setFromValue(0.1);
        ft.setToValue(1.0);
        ft.play();
    }
}
