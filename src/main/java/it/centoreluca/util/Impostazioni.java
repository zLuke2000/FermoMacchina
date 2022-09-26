package it.centoreluca.util;

public class Impostazioni {

    private final java.util.prefs.Preferences settings;

    public Impostazioni() {
        settings = java.util.prefs.Preferences.userRoot().node("settings");
    }

    public void aggiornaImpostazione(String key, String value) {
        // Se la chiave esiste viene rimossa e aggiornata
        if(!settings.get(key, "").equals("")) {
            settings.remove(key);
        }
        settings.put(key, value);
    }

    public String leggiImpostazione(String key) {
        return settings.get(key, "");
    }

}
