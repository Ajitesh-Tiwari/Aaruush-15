package webarch.aaruush15.BackEnd;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by AJITESH on 16-08-2015.
 */
public class DOMParser {
    String Desc;
    String Detail="",Cost="",Contact="",Criteria="";
    Document doc = null;
    public DOMParser(String Desc){
        this.Desc=Desc;
    }
    public void ParseXML(){
        DocumentBuilderFactory dbFactory
                = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        try {
            doc = dBuilder.parse(new InputSource(new StringReader(Desc)));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        doc.getDocumentElement().normalize();
    }
    public String getDesc()
    {
        return doc.getElementsByTagName("Desc").item(0).getTextContent().trim();
    }
    public String getCost()
    {
        return doc.getElementsByTagName("Cost").item(0).getTextContent().trim();
    }
    public String getContact()
    {
       return doc.getElementsByTagName("Contact").item(0).getTextContent().trim();
    }
    public String getCrit()
    {
        return doc.getElementsByTagName("Criteria").item(0).getTextContent().trim();
    }
}
