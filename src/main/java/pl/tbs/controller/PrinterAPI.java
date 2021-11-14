package pl.tbs.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXMLLoader;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import pl.tbs.model.Student;

public enum PrinterAPI {
    INSTANCE;

    Printer defaultPrinter;
    DateTimeFormatter formatter;
    GridPane node;

    public void setupPrinter() {
        defaultPrinter = Printer.getDefaultPrinter();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            node = FXMLLoader.load(getClass().getResource("printTemplate.fxml"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //print student password sheet
    public void print(Student student) {
        // print
        if (defaultPrinter != null && node != null) {
            PrinterJob job = PrinterJob.createPrinterJob(defaultPrinter);
            if (job != null) {
                    //set parameters
                    ((Label) node.lookup("#datetime")).setText(LocalDateTime.now().format(formatter));
                    ((Label) node.lookup("#name")).setText(student.getDisplayName());
                    ((Label) node.lookup("#email")).setText(student.getEmail());
                    ((Label) node.lookup("#password")).setText(student.getPassword());
                    boolean success = job.printPage(node);
                    if (success) {
                        job.endJob();
                    }
            }
        }
    }

}
