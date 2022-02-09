package it.centoreluca.models;

import it.centoreluca.MainApp;
import it.centoreluca.enumerator.Macchine;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Manutenzione {
    private int id;
    private Macchine macchina;
    private boolean filtro1 = false;
    private boolean filtro2 = false;
    private Image stato1;
    private Image stato2;
    private String data;
    private String operatore;

    public Manutenzione() {}

    public Manutenzione(int id, Macchine macchina, boolean filtro1, boolean filtro2, String data, String operatore) {
        this.id = id;
        this.macchina = macchina;
        this.filtro1 = filtro1;
        this.filtro2 = filtro2;
        this.data = data;
        this.operatore = operatore;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Macchine getMacchina() {
        return macchina;
    }

    public void setMacchina(Macchine macchina) {
        this.macchina = macchina;
    }

    public boolean isFiltro1() {
        return filtro1;
    }

    public void setFiltro1(boolean filtro1) {
        this.filtro1 = filtro1;
    }

    public boolean isFiltro2() {
        return filtro2;
    }

    public void setFiltro2(boolean filtro2) {
        this.filtro2 = filtro2;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ImageView getStato1() {
        if(this.filtro1) {
            return new ImageView(new Image(Objects.requireNonNull(MainApp.class.getResourceAsStream("icon/check.png"))));
        }
        return null;
    }

    public ImageView getStato2() {
        if(this.filtro2) {
            return new ImageView(new Image(Objects.requireNonNull(MainApp.class.getResourceAsStream("icon/check.png"))));
        }
        return null;
    }

    public String getFiltro1() {
        if(filtro1) {
            return "X";
        } else {
            return "";
        }
    }

    public String getFiltro2() {
        if(filtro2) {
            return "X";
        } else {
            return "";
        }
    }

    public String getOperatore() {
        return operatore;
    }

    public void setOperatore(String operatore) {
        this.operatore = operatore;
    }

    @Override
    public String toString() {
        return "Manutenzione{ id=" + id + ", macchina=" + macchina + ", filtro1=" + filtro1 + ", filtro2=" + filtro2 + ", data='" + data + "' }";
    }
}
