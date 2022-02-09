package it.centoreluca.util;

import it.centoreluca.enumerator.Macchine;
import it.centoreluca.models.Manutenzione;
import javafx.scene.control.Alert;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ExcelHelper {

    private static ExcelHelper instance = null;

    private ExcelHelper() {}

    public static ExcelHelper getInstance() {
        if(instance == null) {
            instance = new ExcelHelper();
        }
        return instance;
    }

    public boolean esportaXLSX(List<Manutenzione> manutenzioni, File file) {
        //Create blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        for(Macchine macchina: Macchine.values()) {
            //Create a blank sheet
            XSSFSheet spreadsheet = workbook.createSheet("Manutenzione " + macchina);

            //Create row object
            XSSFRow row;

            //This data needs to be written (Object[])
            Map<String, Object[]> info = new TreeMap<>();
            info.put("1", new Object[]{"Data", "Nome macchina", "Filtro 1", "Filtro 2", "Operatore"});
            for (int i = 0; i < manutenzioni.size(); i++) {
                if(manutenzioni.get(i).getMacchina().equals(macchina)) {
                    info.put(String.valueOf(i + 2), new Object[]{manutenzioni.get(i).getData(), manutenzioni.get(i).getMacchina().toString(), manutenzioni.get(i).getFiltro1(), manutenzioni.get(i).getFiltro2(), manutenzioni.get(i).getOperatore()});
                }
            }

            //Iterate over data and write to sheet
            Set<String> keyId = info.keySet();
            int rowId = 0;

            for (String key : keyId) {
                row = spreadsheet.createRow(rowId++);
                Object[] objectArr = info.get(key);
                int cellId = 0;

                for (Object obj : objectArr) {
                    Cell cell = row.createCell(cellId++);
                    cell.setCellValue((String) obj);
                }
            }
        }
        //Write the workbook in file system
        FileOutputStream out;
        try {
            out = new FileOutputStream(file.getAbsolutePath());
            workbook.write(out);
            out.close();
            return true;
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Attenzione");
            alert.setHeaderText("File in uso");
            alert.setContentText("Il file potrebbe essere aperto da un'altra applicazione");
            alert.showAndWait();
        }
        return false;
    }
}