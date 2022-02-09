package it.centoreluca.util;

import it.centoreluca.enumerator.Mesi;
import it.centoreluca.models.Manutenzione;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateHelper {

    private final CssHelper cssHelper = CssHelper.getInstance();
    private static DateHelper instance = null;

    private DateHelper() {}

    public static DateHelper getInstance() {
        if(instance == null) {
            instance = new DateHelper();
        }
        return instance;
    }

    public void impostaCBMesi(ComboBox<Mesi> mese) {
        mese.getItems().addAll(Mesi.values());
    }

    public void dataAutomatica(TextField giorno, ComboBox<Mesi> mese, TextField anno, TextField ora, TextField minuto) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
        giorno.setText(String.valueOf(calendar.get(Calendar.DATE)));
        mese.setValue(Mesi.values()[calendar.get(Calendar.MONTH)]);
        anno.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        ora.setText(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
        minuto.setText(String.valueOf(calendar.get(Calendar.MINUTE)));
    }

    public boolean check(TextField giorno, ComboBox<Mesi> mese, TextField anno, TextField ora, TextField minuto, Manutenzione manutenzione) {
        boolean check = true;
        int year = -1;
        int month = -1;
        int day = -1;
        int hour = -1;
        int minute = -1;

        // Controllo e imposto il campo anno
        if (anno.getText().trim().length() == 4) {
            try {
                year = Integer.parseInt(anno.getText().trim());
                cssHelper.toValid(anno);
            } catch (NumberFormatException nfe) {
                cssHelper.toError(anno, new Tooltip("Ammessi solo numeri"));
                check = false;
            }
        } else {
            check = false;
            cssHelper.toError(anno, new Tooltip("Immettere 4 numeri"));
        }

        // Controllo e imposto il campo mese
        if (mese.getValue() == null) {
            cssHelper.toError(mese, new Tooltip("Selezionare il mese"));
            check = false;
        } else if (mese.getValue().equals(Mesi.GENNAIO)) {
            month = 1;
        } else if (mese.getValue().equals(Mesi.FEBBRAIO)) {
            month = 2;
        } else if (mese.getValue().equals(Mesi.MARZO)) {
            month = 3;
        } else if (mese.getValue().equals(Mesi.APRILE)) {
            month = 4;
        } else if (mese.getValue().equals(Mesi.MAGGIO)) {
            month = 5;
        } else if (mese.getValue().equals(Mesi.GIUGNO)) {
            month = 6;
        } else if (mese.getValue().equals(Mesi.LUGLIO)) {
            month = 7;
        } else if (mese.getValue().equals(Mesi.AGOSTO)) {
            month = 8;
        } else if (mese.getValue().equals(Mesi.SETTEMBRE)) {
            month = 9;
        } else if (mese.getValue().equals(Mesi.OTTOBRE)) {
            month = 10;
        } else if (mese.getValue().equals(Mesi.NOVEMBRE)) {
            month = 11;
        } else if (mese.getValue().equals(Mesi.DICEMBRE)) {
            month = 12;
        }
        if (month != -1) {
            cssHelper.toValid(mese);
        }

        // Controllo e imposto il campo giorno
        if (giorno.getText().trim().length() < 1) {
            check = false;
            cssHelper.toError(giorno, new Tooltip("Immettere almeno 1 numero"));
        } else if (giorno.getText().trim().length() > 2) {
            check = false;
            cssHelper.toError(giorno, new Tooltip("Immettere massimo 2 numeri"));
        } else {
            try {
                day = Integer.parseInt(giorno.getText().trim());
                cssHelper.toValid(giorno);
            } catch (NumberFormatException nfe) {
                cssHelper.toError(giorno, new Tooltip("Ammessi solo numeri"));
                check = false;
            }
        }

        // Controllo che la data sia coerente
        if (check & !isDateValid(day, month, year)) {
            cssHelper.toError(giorno, new Tooltip("Data non coerente"));
            cssHelper.toError(mese, new Tooltip("Data non coerente"));
            cssHelper.toError(anno, new Tooltip("Data non coerente"));
        }

        // Controllo e imposto il campo ora
        if (ora.getText().trim().length() < 1) {
            check = false;
            cssHelper.toError(ora, new Tooltip("Immettere almeno 1 numero"));
        } else if (ora.getText().trim().length() > 2) {
            check = false;
            cssHelper.toError(ora, new Tooltip("Immettere massimo 2 numeri"));
        } else {
            try {
                hour = Integer.parseInt(ora.getText().trim());
                if (hour >= 0 & hour <= 23) {
                    cssHelper.toValid(ora);
                } else {
                    cssHelper.toError(ora, new Tooltip("Deve essere compreso tra 0 e 23"));
                    check = false;
                }
            } catch (NumberFormatException nfe) {
                cssHelper.toError(ora, new Tooltip("Ammessi solo numeri"));
                check = false;
            }
        }

        // Controllo e imposto il campo minuto
        if (minuto.getText().trim().length() < 1) {
            check = false;
            cssHelper.toError(minuto, new Tooltip("Immettere almeno 1 numero"));
        } else if (minuto.getText().trim().length() > 2) {
            check = false;
            cssHelper.toError(minuto, new Tooltip("Immettere massimo 2 numeri"));
        } else {
            try {
                minute = Integer.parseInt(minuto.getText().trim());
                if (minute >= 0 & minute <= 59) {
                    cssHelper.toValid(minuto);
                } else {
                    cssHelper.toError(minuto, new Tooltip("Deve essere compreso tra 0 e 59"));
                    check = false;
                }
            } catch (NumberFormatException nfe) {
                cssHelper.toError(minuto, new Tooltip("Ammessi solo numeri"));
                check = false;
            }
        }

        // Imposto la string data
        String timestamp = year + "/";
        // Controllo mese
        if (month < 10) {
            timestamp += "0" + month + "/";
        } else {
            timestamp += month + "/";
        }
        // Controllo giorno
        if (day < 10) {
            timestamp += "0" + day + " ";
        } else {
            timestamp += day + " ";
        }
        // Controllo ora
        if (hour < 10) {
            timestamp += "0" + hour + ":";
        } else {
            timestamp += hour + ":";
        }
        // Controllo minuto
        if (minute < 10) {
            timestamp += "0" + minute + ":00";
        } else {
            timestamp += minute + ":00";
        }

        manutenzione.setData(timestamp);
        return check;
    }

    @SuppressWarnings("all")
    private boolean isDateValid(int day, int month, int year) {
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        calendar.setLenient(false);
        try {
            calendar.get(Calendar.DATE);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public String timestamp() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
        return calendar.get(Calendar.DATE) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.YEAR) + " " + calendar.get(Calendar.HOUR) + "-" + calendar.get(Calendar.MINUTE);
    }
}
