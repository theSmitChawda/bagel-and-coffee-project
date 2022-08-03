package com.example.sheridanbagelhouseproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javax.print.*;
import javax.print.attribute.*;




public class  SheridanBagelHouseController {
    @FXML
    private Button btnCalculate;
    @FXML
    private Button btnExit;
    @FXML
    private MenuItem btnFile;
    @FXML
    private MenuButton btnPrint;
    @FXML
    private MenuItem btnPrinter;
    @FXML
    private Button btnReset;
    @FXML
    private TextField cafetxt;
    @FXML
    private TextField cappuccinotxt;
    @FXML
    private CheckBox chkBlueberry;
    @FXML
    private CheckBox chkButter;
    @FXML
    private CheckBox chkCreamChesse;
    @FXML
    private CheckBox chkPeach;
    @FXML
    private CheckBox chkRaspberry;
    @FXML
    private RadioButton radCafeAuLait;
    @FXML
    private RadioButton radCappuccino;
    @FXML
    private RadioButton radNoCoffee;
    @FXML
    private RadioButton radRegCoffee;
    @FXML
    private RadioButton radWheat;
    @FXML
    private RadioButton radWhite;
    @FXML
    private TextField regulartxt;
    @FXML
    private TextField subtotaltxt;
    @FXML
    private TextField taxtxt;
    @FXML
    private TextField totaltxt;
    @FXML
    private TextField whitetxt;
    @FXML
    private TextField wholetxt;
    @FXML
    private AnchorPane toppingPane;

    @FXML
    private ToggleGroup bagel;
    @FXML
    private ToggleGroup coffee;

    private static double price;
    private static double tax;
    private static double total;
    private static int bagelQuantity;
    private static int coffeeQuantity;
    private static double bagelPrice;

    private static double coffeePrice;
    //user methods

    public void bagelSelection(){

        if(radWhite.isSelected() && whitetxt.getText() != null && whitetxt.getText() != "" ){
            bagelQuantity = Integer.parseInt(whitetxt.getText());
            bagelPrice = (1.25* bagelQuantity);
            price += bagelPrice;
        }
        else if(radWheat.isSelected() && wholetxt.getText() != null && wholetxt.getText() != "" ){
            bagelQuantity = Integer.parseInt(wholetxt.getText());
            bagelPrice = (1.50 * bagelQuantity);
            price += bagelPrice;
        }
        else{
            whitetxt.setText("required");
            wholetxt.setText("required");

        }
        if(bagelQuantity > 0) {
            toppingPane.setDisable(false);
            for (int i = 1; i <= bagelQuantity; i++) {
                toppingSelection();
            }
        }

    }
    public void bagelSelectionListner(){
        if(radWhite.isSelected() || radWheat.isSelected()){
            toppingPane.setDisable(false);
        }
    }

    public void coffeeSelection(){
        if(radCappuccino.isSelected()){
            coffeeQuantity = Integer.parseInt(cappuccinotxt.getText());
            coffeePrice = (2.00 * coffeeQuantity);
            price += coffeePrice;
        }
        else if(radRegCoffee.isSelected()){
            coffeeQuantity = Integer.parseInt(regulartxt.getText());
            coffeePrice = (1.25 * coffeeQuantity);
            price += coffeePrice;
        }
        else if(radCafeAuLait.isSelected()){
            coffeeQuantity = Integer.parseInt(cafetxt.getText());
            coffeePrice = (1.75 * coffeeQuantity);
            price += coffeePrice;
        }
        else if(radNoCoffee.isSelected()){
            coffeePrice= 0.0;
            price += coffeePrice;
        }
        else{
            cappuccinotxt.setText("required");
            regulartxt.setText("required");
            cafetxt.setText("required");

        }

    }

    public void toppingSelection(){
        if(chkBlueberry.isSelected()){
            bagelPrice += 0.75;
        }
        else if(chkButter.isSelected()){
            bagelPrice += 0.25;
        }
        else if(chkCreamChesse.isSelected()){
            bagelPrice += 0.50;
        }
        else if(chkRaspberry.isSelected()){
            bagelPrice += 0.75;
        }
        else if(chkPeach.isSelected()){
            bagelPrice += 0.75;
        }
    }

    public void calculateButtonListner(){
            bagelSelection();
            coffeeSelection();
            price = Math.round(price*100.0)/100.0;
            subtotaltxt.setText(Double.toString(price));

            tax = price * 0.13;
            tax = Math.round(tax*100.0)/100.0;
            taxtxt.setText(Double.toString(tax));

            total = tax + price;
            total = Math.round(total*100.0)/100.0;
            totaltxt.setText(Double.toString(total));

    }
    public void resetButtonListner(){
        radWhite.setSelected(false);
        radWheat.setSelected(false);
        radNoCoffee.setSelected(false);
        radRegCoffee.setSelected(false);
        radCappuccino.setSelected(false);
        radCafeAuLait.setSelected(false);
        chkPeach.setSelected(false);
        chkRaspberry.setSelected(false);
        chkButter.setSelected(false);
        chkBlueberry.setSelected(false);
        chkCreamChesse.setSelected(false);
        whitetxt.setText("");
        wholetxt.setText("");
        regulartxt.setText("");
        cappuccinotxt.setText("");
        cafetxt.setText("");
        totaltxt.setText("");
        taxtxt.setText("");
        subtotaltxt.setText("");
        price = 0.0;
        tax =0.0;
        total=0.0;
        bagelQuantity=0;
        coffeeQuantity=0;
        coffeePrice=0.0;
        bagelPrice=0.0;
    }

    public void exitButtonListner(){
        Platform.exit();
    }


    public void printToFileListner(){

            String grandTotal= bagelQuantity+" Bagel with toppings:    "+bagelPrice+
                    "\n"+coffeeQuantity+" Coffee:    "+coffeePrice+
                    "\nSubtotal:    "+ price +
                    "\ntax:    "+ tax+
                    "\nTotal:    "+ total;

        try {
            OutputStream outFile = new FileOutputStream("receipt.txt");

            byte[] byteArray= grandTotal.getBytes();

            outFile.write(byteArray);
            System.out.println("Recipt is ready.");

            outFile.close();
        }

        catch (Exception e) {
            e.getStackTrace();
        }
    }
    public static void printToPrinter() {
        //printToFile();  // generate receipt file
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
//        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        PrintService ps[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
        PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
        PrintService service = ServiceUI.printDialog(null, 200, 200, ps, defaultService, flavor, pras);
        if (service != null) {
            try {
                DocPrintJob job = service.createPrintJob();
                DocAttributeSet das = new HashDocAttributeSet();
                FileInputStream fis = new FileInputStream("receipt.txt");
                Doc doc = new SimpleDoc(fis, flavor, das);
                try {
                    job.print(doc, pras);
                    System.out.println("Job sent to printer.");
                    fis.close();
                } catch (PrintException e) {
                    System.out.println("Print error!" + e.getMessage());
                } catch (IOException e) {
                    System.out.println("File error!" + e.getMessage());
                }
                finally {
                    fis.close();
                }
            } catch (IOException e) {
                System.out.println("File not found!" + e.getMessage());
            }
        }
    }
    public void printToPrinter(ActionEvent actionEvent){
        printToPrinter();
    }

}
