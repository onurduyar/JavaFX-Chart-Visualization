package app;

import java.util.ArrayList;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParserHandler extends DefaultHandler
{
    //This is the list which shall be populated while parsing the XML.
    private ArrayList countryList = new ArrayList();

    //As we read any XML element we will push that in this stack
    private Stack elementStack = new Stack();


    //As we complete one user block in XML, we will push the Country instance in countryList
    private Stack objectStack = new Stack();

    /*

    public void startDocument() throws SAXException
    {
        //System.out.println("start of the document   : ");
    }

    public void endDocument() throws SAXException
    {
        //System.out.println("end of the document document     : ");
    }
*/

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {

        //Push it in element stack
        this.elementStack.push(qName);

        //If this is start of 'country' element then prepare a new Country instance and push it in object stack
        if ("record".equals(qName))
        {
            //New User instance
            Country country = new Country();

            //Set all required attributes in any XML element here itself
            if(attributes != null && attributes.getLength() == 1)
            {
                country.setName(attributes.getValue(0));
            }
            this.objectStack.push(country);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        //Remove last added  element
        this.elementStack.pop();

        //User instance has been constructed so pop it from object stack and push in userList
        if ("record".equals(qName))
        {
            Country object = (Country) this.objectStack.pop();
            this.countryList.add(object);
        }
    }


     //This will be called everytime parser encounter a value node
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        String value = new String(ch, start, length).trim();

        if (value.length() == 0)
        {
            return; // ignore white space
        }

        //handle the value based on to which element it belongs

        if ("key".equals(currentElement()))
        {
            Country country = (Country) this.objectStack.peek();
            country.setKey(value);
        }
        if ("name".equals(currentElement()))
        {
            Country country = (Country) this.objectStack.peek();
            country.setName(value);
        }
        if ("country".equals(currentElement()))
        {
            Country country = (Country) this.objectStack.peek();
            country.setCountry(value);
        }
        if ("year".equals(currentElement()))
        {
            Country country = (Country) this.objectStack.peek();
            country.setYear(Integer.parseInt(value));
        }
        if ("value".equals(currentElement()))
        {
            Country country = (Country) this.objectStack.peek();
            country.setValue(Integer.parseInt(value));
        }
        if ("category".equals(currentElement()))
        {
            Country country = (Country) this.objectStack.peek();
            country.setCategory(value);
        }

    }

    // Utility method for getting the current element in processing

    private String currentElement()
    {
        return this.elementStack.peek().toString();
    }

    //Accessor for countryList object
    public ArrayList getCountries()
    {
        return countryList;
    }


}
