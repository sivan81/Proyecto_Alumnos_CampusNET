package com.campus;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Util {
	
	//Utilizamos para abrir el fichero de credenciales y utilizando la librería XPath busca el NODO que se le especifica	
    public static NodeList parse(String rutaFichero, String expresion) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        File file = new File(rutaFichero);
        FileInputStream fileIS = new FileInputStream(file);
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document xmlDocument = builder.parse(fileIS);
        XPath xPath = XPathFactory.newInstance().newXPath();
        return (NodeList) xPath.compile(expresion).evaluate(xmlDocument, XPathConstants.NODESET);
    }

    //Lo utilizamos para escribir en el fichero y cerrarlo
    public static void writeInFile(String nombreFichero, String contenido) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(nombreFichero));
        writer.write(contenido);

        writer.close();
    }
}
