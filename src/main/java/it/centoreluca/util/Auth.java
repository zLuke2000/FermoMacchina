package it.centoreluca.util;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Auth {

    private static Auth instance = null;
    private Preferences auth;

    private Auth() {
        auth = Preferences.userRoot().node("ADMIN");
        try {
            auth.clear();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
        auth.put("admin", "pYg4u4EpNs80qPfu");
        auth = Preferences.userRoot().node("AUTH");
    }

    public static Auth getInstance() {
        if(instance == null) {
            instance = new Auth();
        }
        return instance;
    }

    /**
     * @param oldUsername vecchio username
     * @param newUsername nuovo username
     * @return 0 utente inesistente, 1 ok, 2 newUsername already in use, 3 username too short min 4 char
     */
    public int aggiornamentoNome(String oldUsername, String newUsername) {
        auth = Preferences.userRoot().node("AUTH");
        if(newUsername.length() >= 4) {
            if (!auth.get(oldUsername, "").equals("")) {
                if (auth.get(newUsername, "").equals("")) {
                    auth.put(newUsername, auth.get(oldUsername, ""));
                    auth.remove(oldUsername);
                    return 1;
                }
                return 2;
            }
            return 0;
        }
        return 3;
    }

    /**
     * TODO da sistemare
     * @return 0 utente inesistente, 1 ok, 2 oldPassword != newPassword, 3 password too short min 4 char
     */
    public int aggiornamentoPassword(String username, String newPassword1, String newPassword2) {
        auth = Preferences.userRoot().node("AUTH");
        if(newPassword1.length() >= 4) {
            if (newPassword1.equals(newPassword2)) {
                if (!auth.get(username, "").equals("")) {
                    auth.remove(username);
                    auth.put(username, newPassword1);
                    return 1;
                }
                return 0;
            }
            return 2;
        }
        return 3;
    }

    /**
     * @param username username operatore
     * @param password password operatore
     * @return 0 auth ok, 1 wrong password, 2 username not found, 3 admin
     */
    public int autenticazione(String username, String password) {
        // ADMIN
        auth = Preferences.userRoot().node("ADMIN");
        if(username.equals("admin") && password.equals(auth.get("admin", ""))) {
            return 3;
        }
        // USER
        auth = Preferences.userRoot().node("AUTH");
        if(!auth.get(username, "").equals("")) {
            if(auth.get(username, "").equals(password)) {
                return 0;
            }
            return 1;
        }
        return 2;
    }

    public void reset() {
        auth = Preferences.userRoot().node("AUTH");
        try {
            auth.clear();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
    }

    public void aggiungiUtente(String user, String password) {
        auth = Preferences.userRoot().node("AUTH");
        auth.put(user, password);
    }
}
