package sample;

import javafx.geometry.Orientation;
import javafx.scene.layout.*;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.animation.*;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class Controller {

    private File selectedFile;
    private Data data;

    @FXML
    private Button selectFileButton;

    @FXML
    private TextField textURL;

    @FXML
    private Label flashText = new Label();

    @FXML
    private RadioButton barRadio = new RadioButton();

    @FXML
    private GridPane grid =new GridPane();

    @FXML
    private VBox vBox = new VBox();

    @FXML
    private VBox vBoxText = new VBox();


    public void readFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Data Files", "*.xml", "*.txt"));
        selectedFile = fileChooser.showOpenDialog(selectFileButton.getParent().getScene().getWindow());
        fileControl(selectedFile);
    }

    private void fileControl(File file) throws IOException {
        if (file != null) {
            getFileExtension(file);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("You haven't chose a file");
            alert.setContentText("You have to choose a file");
            alert.showAndWait();
        }
    }

    private void getFileExtension(File file) throws IOException{
        String extension;
        String fileName = file.toString();
        extension = fileName.substring(fileName.length() - 3);
        if (extension.equals("txt")) {
            data = TextParser.Parse(file);
            sortTxt();
        } else {
            data = XMLParser.Parse(file);
            sortXml();
        }
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(2);
        timeline.setAutoReverse(true);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000),
                new KeyValue(flashText.visibleProperty(), true)
        ));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2000),
                new KeyValue(flashText.visibleProperty(),false)
        ));
        timeline.play();
        data.createCategories();

        data.printRecords();
    }

    public void getURL() {
        String url = textURL.getText();
        if(url.matches("(?i)\\b(?:(?:https?|ftp)://)(?:\\S+(?::\\S*)?@)?(?:(?!(?:10|127)(?:\\.\\d{1,3}){3})(?!(?:169\\.254|192\\.168)(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)(?:\\.(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)*(?:\\.(?:[a-z\\u00a1-\\uffff]{2,}))\\.?)(?::\\d{2,5})?(?:[/?#]\\S*)?\\b")) {
            data = XMLParser.Parse(url);
            if(data.getSuccess() == 1 ) {
                final Timeline timeline = new Timeline();
                timeline.setCycleCount(2);
                timeline.setAutoReverse(true);
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000),
                        new KeyValue(flashText.visibleProperty(), true)
                        ));
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2000),
                        new KeyValue(flashText.visibleProperty(),false)
                        ));
                timeline.play();
                data.createCategories();
                sortXml();
                data.printRecords();
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText("Incoming data is not in suitable format.");
                alert.setContentText("Please enter url that can provide suitable format.");
                alert.showAndWait();
            }
         }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Please enter a valid URL.");
            alert.setContentText("The URL is not valid.");
            alert.showAndWait();
        }
    }

    public void actionBarRadio(){
        if (data == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Please enter a valid URL or choose a file.");
            alert.setContentText("File not found.");
            alert.showAndWait();
        }
    }

    public void sortTxt(){

        int count = 0;

        for (int i = 0, j = 0; j < data.recordsNumber.size() && i < data.recordsNumber.get(j);){
            for (int k = 0; k < data.recordsNumber.get(j) - (i + 1); k++) {
                if (data.records.get(k + count).getValue() < data.records.get(k + count + 1).getValue()) {
                    Collections.swap(data.records, k + count, k + count + 1);
                }
            }
            i++;
            if (i == data.recordsNumber.get(j) - 1){
                count = count + data.recordsNumber.get(j);
                j++;
                i = 0;
            }
        }
    }

    public void sortXml(){
        int counter = 0;
        ArrayList<Record> array = new ArrayList<>();
        String first = data.records.get(0).getName();
        int recordsNumber = 0;
        String year = data.records.get(0).getYear();


        for (Record record : data.records){

            if(year.equals(record.getYear())){
                recordsNumber++;
            }
        }

        data.recordsNumber.add(recordsNumber);

        for (Record record : data.records){
            if (record.getName().equals(first)){
                counter++;
            } else{
                break;
            }
        }
            data.recordsNumber.add(counter);
        for (int i = 1; i <= counter; i++){
            for (Record record : data.records){
                if (record.getYear().equals(year)){
                    array.add(record);
                }
            }

            year = data.records.get(i).getYear();
        }

        data.records = array;

        int count = 0;

        for (int i = 0, j = 0; j < counter && i < data.recordsNumber.get(0);){
            for (int k = 0; k < data.recordsNumber.get(0) - (i + 1); k++) {
                if (data.records.get(k + count).getValue() < data.records.get(k + count + 1).getValue()) {
                    Collections.swap(data.records, k + count, k + count + 1);
                }
            }
            i++;
            if (i == data.recordsNumber.get(0) - 1){
                count = count + data.recordsNumber.get(0);
                j++;
                i = 0;
            }
        }
    }
    public void createChart(){
        ArrayList<Record> topTen = new ArrayList<Record>();
        long  maxValue;
        maxValue = data.type.equals("txt") ?  (data.records.get(data.records.size()-data.recordsNumber.get(data.recordsNumber.size()-1)).getValue()) : (data.records.get(data.records.size()-data.recordsNumber.get(data.recordsNumber.size()-2)).getValue());
        int k = 0;
        Text barText;
        ArrayList<Bar> barList = new ArrayList<>();
        ArrayList<Rectangle> rect = new ArrayList<Rectangle>();
        ArrayList<Text> barTextList = new ArrayList<Text>();
        for (int i = 0;(data.type.equals("xml") && i < data.recordsNumber.get(1)) || i < data.recordsNumber.size();i++){
            if (i != 0){
                if (data.type.equals("txt")) {
                    k += data.recordsNumber.get(i-1);
                }
                else {
                    k += data.recordsNumber.get(0);
                }
            }
            for (int j = k; j < k + 10;j++) {
                Record record = data.records.get(j);
                Bar bar = new Bar(record.getName(),record.getCategory(),record.getValue(),record.getCountry(),record.getYear(), ((long) (record.getValue() * vBox.getWidth()) / maxValue));
                barList.add(bar);
                topTen.add(record);
                rect.add(bar.bar);
                barText = new Text(record.getName());
                barText.setFont(new Font(11));
                barTextList.add(barText);
            }
            for (Record record:topTen) {
                System.out.println(record.toString());
            }
            System.out.println("-----------------------------------------");
            topTen.clear();
            barList.clear();
            if ((data.type.equals("xml") && i != data.recordsNumber.get(1)-1) || data.type.equals("txt") && i != data.recordsNumber.size() -1 ) {
                rect.clear();
                barTextList.clear();
            }
        }
        vBox.setSpacing(20);
        vBox.setStyle("-fx-background-color: #89608E;");
        vBox.getChildren().addAll(rect);
        vBoxText.getChildren().addAll(barTextList);
        vBoxText.setSpacing(30);

        ArrayList<Text> categories = new ArrayList<Text>();
        for (int i = 0; i < data.categories.size();i++) {
            categories.add(new Text(data.categories.get(i)));
            categories.get(i).setFill(Color.rgb(new Random().nextInt(255),new Random().nextInt(255),new Random().nextInt(255)));
        }

    }
}
