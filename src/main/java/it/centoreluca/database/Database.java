package it.centoreluca.database;

import it.centoreluca.MainApp;
import it.centoreluca.enumerator.Macchine;
import it.centoreluca.models.Manutenzione;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Database {

    private static final String path = System.getProperty("user.dir") + "/Manutenzione.accdb";
    private final static String url = "jdbc:ucanaccess://" + path;
    private static Connection con;
    private static Statement st;
    private static Database instance = null;

    private Database() {
        if(Files.notExists(Path.of(path))) {
            try {
                Files.copy(Objects.requireNonNull(MainApp.class.getResourceAsStream("database/Manutenzione.accdb")), Path.of(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
                con = DriverManager.getConnection(url);
                st = con.createStatement();
            } catch (SQLException e) {
                System.err.println("[sql] ->" + e);
            } catch (ClassNotFoundException e) {
                System.err.println("[class] ->" + e);
            }
        }
    }

    public static Database getInstance() {
        if(instance == null) {
            instance = new Database();

        }
        return instance;
    }

    public void save(Manutenzione manutenzione) {
        try {
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO manutenzioni_macchina_" + manutenzione.getId()
                    + " (Data, "
                    + "Macchina, "
                    + "Filtro_1, "
                    + "Filtro_2, "
                    + "Operatore) "
                    + "VALUES (?, ?, ?, ?, ?)");
            pstmt.setString(1, manutenzione.getData());
            pstmt.setString(2, manutenzione.getMacchina().toString());
            pstmt.setBoolean(3, manutenzione.isFiltro1());
            pstmt.setBoolean(4, manutenzione.isFiltro2());
            pstmt.setString(5, manutenzione.getOperatore());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("[nuovaManutenzione] -> " + e);
        }
    }

    public void close() {
        if(con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println("[conn close] -> " + e);
            }
        }
    }

    public List<Manutenzione> getManutenzioni(int indexMacchina) {
        List<Manutenzione> manutenzioni = new ArrayList<>();
        Manutenzione manutenzioneCorrente;
        ResultSet rs;
        String sql;
        if(indexMacchina != 0) {
            sql = "SELECT *" +
                    "FROM manutenzioni_macchina_" + indexMacchina;
            try {
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    manutenzioni.add(new Manutenzione(rs.getInt("ID"), Macchine.values()[indexMacchina-1], rs.getBoolean("Filtro_1"), rs.getBoolean("Filtro_2"), rs.getString("Data"), rs.getString("Operatore")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            for(int i=1; i<Macchine.values().length+1; i++) {
                sql = "SELECT *" +
                      "FROM manutenzioni_macchina_" + i;
                try {
                    rs = st.executeQuery(sql);
                    while (rs.next()) {
                        manutenzioneCorrente = new Manutenzione();
                        manutenzioneCorrente.setId(rs.getInt("ID"));
                        manutenzioneCorrente.setData(rs.getString("Data"));
                        System.out.println(rs.getString("Macchina"));
                        for (Macchine mach : Macchine.values()) {
                            if(Objects.equals(rs.getString("Macchina"), mach.toString())) {
                                manutenzioneCorrente.setMacchina(mach);
                            }
                        }
                        manutenzioneCorrente.setFiltro1(rs.getBoolean("Filtro_1"));
                        manutenzioneCorrente.setFiltro2(rs.getBoolean("Filtro_2"));
                        manutenzioneCorrente.setOperatore(rs.getString("Operatore"));
                        manutenzioni.add(manutenzioneCorrente);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return manutenzioni;
    }

    public void cancellaManutenzione(int macchinaSelezionata, int id) {
        try {
            st.executeUpdate("DELETE FROM manutenzioni_macchina_" + macchinaSelezionata
                               + " WHERE ID = '" + id + "'");
        } catch (Exception e) {
            System.err.println("[cancellaManutenzione] -> " + e);
        }
    }
}
