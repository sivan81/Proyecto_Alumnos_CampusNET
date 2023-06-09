package com.campus.parser;

import com.campus.Util;
import com.campus.model.Alumno;
import com.campus.model.Asignatura;
import com.campus.model.CampusNet;
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

public class CampusNetParser {
	
	//Crear el fichero xml por primera vez cuando no existe
    public static final String RUTA_FICHERO = "src/resources/campus.xml";

    public static CampusNet parse() throws ParserConfigurationException, SAXException, XPathExpressionException {
        CampusNet resultado = new CampusNet();

        try{
            String expression = "/campusNet";
            NodeList campusNetNodeList = Util.parse(RUTA_FICHERO, expression);

            Node nodoCampus = campusNetNodeList.item(0);

            //Recorremos los nodos hijos y los convertimos en objeto Asignatura
            NodeList nodosHijos = nodoCampus.getChildNodes();
            for (int n = 0; n < nodosHijos.getLength(); n++) {
                Node nodoHijo = nodosHijos.item(n);
                //Como pueden existir nodos asignatura y al mismo nivel nodos alumno, vamos
                //preguntando si el nodo es alumno o asignatura
                if(nodoHijo.getNodeName().equals("asignatura")){
                    resultado.getAsignaturas().add(convertToAsignatura(nodoHijo));
                }else if(nodoHijo.getNodeName().equals("alumno")){
                    resultado.getAlumnos().add(convertToAlumno(nodoHijo));
                }
            }
        }catch (IOException exception){
            System.out.println("El fichero " + RUTA_FICHERO + " no existe, por lo que será creado");
        }

        return resultado;
    }

    private static Asignatura convertToAsignatura(Node nodoAsignatura){
        Asignatura asignatura = new Asignatura();
        asignatura.setId(((Element) nodoAsignatura).getAttribute("id"));
        NodeList nodosHijos = nodoAsignatura.getChildNodes();
        //Recorremos todos los nodos "alumno" que están dentro de este nodoAsignatura y
        //creamos un alumno por cada uno y lo añadimos a la lista de alumnos de la clase Asignatura
        for (int n = 0; n < nodosHijos.getLength(); n++) {
            Node nodoHijo = nodosHijos.item(n);
            if (nodoHijo.getNodeType() == Node.ELEMENT_NODE) {
                asignatura.getAlumnos().add(convertToAlumno(nodoHijo));
            }
        }
        return asignatura;
    }

    //Funcion que convierte un nodo <alumno>...</alumno> en un objeto de tipo Alumno
    private static Alumno convertToAlumno(Node nodoAlumno){
        Alumno alumno = new Alumno();
        alumno.setNombre(((Element) nodoAlumno).getElementsByTagName("nombre").item(0).getTextContent());
        alumno.setApellidos(((Element) nodoAlumno).getElementsByTagName("apellidos").item(0).getTextContent());
        if(((Element) nodoAlumno).getElementsByTagName("nota").getLength() > 0){
            Integer nota = new Integer(((Element) nodoAlumno).getElementsByTagName("nota").item(0).getTextContent());
            alumno.setNota(nota);
        }
        return alumno;
    }
}
