package sample;
import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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


}
