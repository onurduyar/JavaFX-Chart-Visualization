package sample;

import java.io.File;
import java.io.FileNotFoundException;

public class XMLParser extends Parser{
    static Data Parse(File file) throws FileNotFoundException {
        System.out.println("xml parser");
        return new Data();
    }
}
