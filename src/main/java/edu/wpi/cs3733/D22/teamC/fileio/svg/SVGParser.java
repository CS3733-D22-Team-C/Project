package edu.wpi.cs3733.D22.teamC.fileio.svg;

import edu.wpi.cs3733.D22.teamC.App;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class SVGParser {
    public String getPath(String svgFilePath) {
        try {
            Document document = openDocument(svgFilePath);
            NodeList nodeList = document.getElementsByTagName("path");
            if (nodeList.getLength() > 0) {
                return nodeList.item(0).getAttributes().getNamedItem("d").getTextContent();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Document openDocument(String svgFilePath) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(App.class.getResourceAsStream(svgFilePath));
        document.getDocumentElement().normalize();

        return document;
    }
}
