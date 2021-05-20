package app;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class ReadUsingSAX {

    public ArrayList parseXML(InputStream inp) {

        //Create a empty link of countries initially
        ArrayList<Country> countries = new ArrayList<>();

        try {
            //Create default handler instance
            ParserHandler handler = new ParserHandler();

            //Create parser from factory
            XMLReader parser = XMLReaderFactory.createXMLReader();

            //Register handler with parser
            parser.setContentHandler(handler);

            //Create an input source from the XML input stream
            InputSource source = new InputSource(inp);

            //parse the document
            parser.parse(source);

            //populate the parsed countries list in above created empty list; You can return from here also.
            countries = handler.getCountries();

        }
        catch (SAXException ex)
        {
            ex.printStackTrace();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally {}
        return countries;
    }
}

