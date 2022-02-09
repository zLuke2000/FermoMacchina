package it.centoreluca;

import it.centoreluca.controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.Objects;

public class MainApp extends Application {

    private static FXMLLoader fxmlLoader;
    private static final Double aWidth = 600.0;     // Avvio
    private static final Double aHeight = 300.0;    // Avvio
    private static final Double nmWidth = 1100.0;   // Nuova Manutenzione
    private static final Double nmHeight = 400.0;   // Nuova Manutenzione
    private static final Double tmmWidth = 800.0;   // Tabella Manutenzione Macchine
    private static final Double tmmHeight = 500.0;  // Tabella Manutenzione Macchine

    public static Stage stage;
    private static Scene scene;
    private Double xOffset;
    private Double yOffset;
    private static Double width = aWidth;
    private static Double height = aHeight;

    @Override
    public void start(Stage stage) {
        MainApp.stage = stage;
        scene = new Scene(Objects.requireNonNull(loadFXML("Avvio")));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        stage.getIcons().add(new Image(Objects.requireNonNull(MainApp.class.getResourceAsStream("icon/iconLLQ.png"))));
        stage.setTitle("Produzione");
        stage.show();

        // Trascinamento finestra
        scene.setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });
        scene.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - xOffset);
            stage.setY(mouseEvent.getScreenY() - yOffset);
        });
    }

    public static void setRoot(String fxml, int param, String operatore) {
        scene.setRoot(loadFXML(fxml));
        Controller c = fxmlLoader.getController();
        switch (fxml) {
            case "Avvio":
                stage.setTitle("PRODUZIONE");
                width = aWidth;
                height = aHeight;
                break;
            case "NuovaManutenzione":
                stage.setTitle("PRODUZIONE - Manutenzione macchine");
                c.initParameter(null, null, operatore, -1);
                width = nmWidth;
                height = nmHeight;
                break;
            case "TabellaManutenzioni":
                stage.setTitle("PRODUZIONE - Tabella manutenzione macchine");
                c.initParameter(null, null, operatore, param);
                width = tmmWidth;
                height = tmmHeight;
                break;
            default:
                System.err.println("[ATTENZIONE] NOME FXML ERRATO");
                break;
        }
        stage.setWidth(width);
        stage.setHeight(height);
    }

    public static Parent loadFXML(String fxml) {
        fxmlLoader = new FXMLLoader(MainApp.class.getResource(fxml + ".fxml"));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        launch();
    }

}
