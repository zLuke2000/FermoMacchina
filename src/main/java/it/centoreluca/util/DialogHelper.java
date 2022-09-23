package it.centoreluca.util;

import it.centoreluca.MainApp;
import it.centoreluca.controller.Controller;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.io.IOException;
import java.util.Objects;

public class DialogHelper {

    private Parent parent;
    private Double xOffset;
    private Double yOffset;
    private Stage stage;

    public DialogHelper() {}

    public void newDialog(String title, String fxmlName, Pane rootPane, Controller parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(fxmlName + ".fxml"));
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(parent);
        stage = new Stage();
        Controller c = fxmlLoader.getController();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle(title);
        stage.getIcons().add(new Image(Objects.requireNonNull(MainApp.class.getResourceAsStream("icon/iconLLQ.png"))));
        stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        c.initParameter(parentController, stage, -1);
        if(rootPane != null) {
            FadeTransition ft = new FadeTransition(Duration.millis(2000), rootPane);
            ft.setFromValue(1.0);
            ft.setToValue(0.1);
            ft.play();
        }

        // Trascinamento finestra
        scene.setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });
        scene.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - xOffset);
            stage.setY(mouseEvent.getScreenY() - yOffset);
        });
        stage.showAndWait();
    }
}
