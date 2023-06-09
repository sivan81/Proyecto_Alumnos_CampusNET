package com.campus.parser;

import com.campus.Util;
import com.campus.model.Credencial;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CredencialesParser {
    private static final String RUTA_FICHERO_CREDENCIALES = "src/resources/credentials.xml";

    public static List<Credencial> parse() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        String expression = "/credenciales/credencial";
        NodeList credenciales = Util.parse(RUTA_FICHERO_CREDENCIALES, expression);

        List<Credencial> resultado = new ArrayList<>();
        for (int n = 0; n < credenciales.getLength() - 1; n++) {
            Node nodoCredencial = credenciales.item(n);
            resultado.add(convertToCredencial(nodoCredencial));
        }

        return resultado;
    }

    private static Credencial convertToCredencial(Node nodoCredencial){
        Credencial credencial = new Credencial();
        credencial.setUsuario(((Element) nodoCredencial).getElementsByTagName("usuario").item(0).getTextContent());
        credencial.setPassword(((Element) nodoCredencial).getElementsByTagName("password").item(0).getTextContent());
        return credencial;
    }
}
