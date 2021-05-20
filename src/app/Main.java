package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


import java.util.Objects;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{


      /*  Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("app.fxml")));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 700, 300));
        primaryStage.show();*/

    }


    public static void main(String[] args) throws FileNotFoundException
    {
       // launch(args);

       parsingTest(); // XML dosyasindaki verilere ulasilamadi !! gozden gecirilmesi gerekiyor...
    }



     static void parsingTest() throws FileNotFoundException
    {
        //Locate the file

        File xmlFile = new File("D:/country_populations.xml");

        //Create the parser instance
        ReadUsingSAX parser = new ReadUsingSAX();

        //Parse the file
        ArrayList countries = parser.parseXML(new FileInputStream(xmlFile));

        //Verify the result
        System.out.println(countries);

    }



}
