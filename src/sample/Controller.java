package sample;
import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class Controller {
    private File selectedFile;
    private Data data;

    @FXML
    private Button selectFileButton;
    @FXML
    private Text titleText = new Text();
    @FXML
    private Text xAxisLabel = new Text();

    @FXML
    private TextField textURL;


    private final double titleX = titleText.getX();
    private final double labelX = xAxisLabel.getX();

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
        } else {
            data = XMLParser.Parse(file);
        }
        titleText.setText(data.title);
        titleText.setX(titleX - titleText.getLayoutBounds().getWidth() / 2);
        titleText.setVisible(true);
        xAxisLabel.setText(data.xAxisLabel);
        xAxisLabel.setX(labelX- xAxisLabel.getLayoutBounds().getWidth() / 2);
        xAxisLabel.setVisible(true);
        data.printRecords();
    }


    public void getURL() {
        String url = textURL.getText();
        if(url.matches("(?i)\\b(?:(?:https?|ftp)://)(?:\\S+(?::\\S*)?@)?(?:(?!(?:10|127)(?:\\.\\d{1,3}){3})(?!(?:169\\.254|192\\.168)(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)(?:\\.(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)*(?:\\.(?:[a-z\\u00a1-\\uffff]{2,}))\\.?)(?::\\d{2,5})?(?:[/?#]\\S*)?\\b")) {
            data = XMLParser.Parse(url);
            if(data.getSuccess() == 1 ) {
                titleText.setText(data.title);
                titleText.setX(titleX - titleText.getLayoutBounds().getWidth() / 2);
                titleText.setVisible(true);
                xAxisLabel.setText(data.xAxisLabel);
                xAxisLabel.setX(labelX- xAxisLabel.getLayoutBounds().getWidth() / 2);
                xAxisLabel.setVisible(true);
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

}
