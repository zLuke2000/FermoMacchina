package it.centoreluca.controller;

import it.centoreluca.util.CssHelper;
import it.centoreluca.util.DialogHelper;
import it.centoreluca.util.Auth;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class ControllerProfileManager extends Controller {

    @FXML private AnchorPane ap_root;
    @FXML private Button b_reset;
    @FXML private ToggleButton tb_abilitazioneReset;
    @FXML private TextField tf_nuovoUsername;
    @FXML private PasswordField pf_nuovaPassword;

    private ControllerAccesso parent;
    private Stage stage;
    private final Auth auth = Auth.getInstance();
    private final CssHelper css = CssHelper.getInstance();
    private final DialogHelper dh = DialogHelper.getInstance();

    @FXML void salvaUtente() {
        boolean check = true;

        if(tf_nuovoUsername.getText().trim().length() < 4) {
            css.toError(tf_nuovoUsername, new Tooltip("Inserire almeno 4 caratteri"));
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

    @FXML void resetPassword() {
        auth.reset();
        indietro();
    }

    @FXML void indietro() {
        stage.close();
        parent.defaultOpacity();
    }

    @Override public void initParameter(Controller parentController, Stage stage, String operatore, int param) {
        this.parent = (ControllerAccesso) parentController;
        this.stage = stage;
    }

    @FXML void modificaNomeUtente() {
        dh.newDialog("Admin profili - Username","CambiaUsernameDialog", ap_root, this, "");
    }

    @FXML void modificaPassword() {
        dh.newDialog("Admin profili - Password","CambiaPasswordDialog", ap_root, this, "");
    }

    @FXML void abilitaReset() {
        tb_abilitazioneReset.setTextAlignment(TextAlignment.CENTER);
        if(tb_abilitazioneReset.isSelected()) {
            b_reset.setDisable(false);
            tb_abilitazioneReset.setText("Disabilita\nRESET");
        } else {
            b_reset.setDisable(true);
            tb_abilitazioneReset.setText("Abilita\nRESET");
        }
    }
}
