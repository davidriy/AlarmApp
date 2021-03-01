package com.example.alarmapp_v1;

import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlManager {
    private Context context;
    private final String FILE_NAME = "alarms.xml";

    // DOM
    Document doc;
    Transformer transformer;

    public XmlManager(Context context){
        this.context = context;
        this.inicializar();
    }
    // Comprueba si el archivo existe
    private boolean existe(){
        boolean existe = false;
        try {
            context.openFileInput(FILE_NAME);
            context.openFileInput(FILE_NAME).close();
            existe = true;
        } catch (FileNotFoundException e) {
            existe =  false;
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return existe;
    }

    // Crea un nuevo documento
    private void crearDocumento(){
        try {
            OutputStreamWriter alarm = new OutputStreamWriter(context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE));
            alarm.write("<alarms></alarms>");
            alarm.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Inicializa la configuración y el documento
    private void inicializar() {
        if(existe()){
            try {
//		Cargar el documento
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                this.doc = db.parse(new File(context.getFilesDir(), FILE_NAME));
            } catch(Exception e) {
//			Imprime un mensaje de error para informar dónde se ha producido
                System.out.println("---------------------------------");
                System.out.println("HA OCURRIDO UN PROBLEMA DURANTE");
                System.out.println("     LA CARGA DEL COUMENTO");
                System.out.println("---------------------------------");
                System.out.println(e.getMessage());
            }
            try {
//		Definir configuración de output
                this.transformer = TransformerFactory.newInstance().newTransformer();
                this.transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                this.transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            } catch(Exception e) {
//			Imprime un mensaje de error para informar dónde se ha producido
                System.out.println("---------------------------------");
                System.out.println("HA OCURRIDO UN PROBLEMA DURANTE");
                System.out.println(" LA CONFIGURACIÓN DE LA SALIDA");
                System.out.println("---------------------------------");
                System.out.println(e.getMessage());
            }
        } else{
            crearDocumento();
            inicializar();
        }
    }

    //	Método que añade un videojuego al XML
    private void addAlarm(Alarm alarm) {
        // Creamos el elemento raíz
        Node nodeAlarm = this.doc.createElement("alarm");
        // Definimos el titulo
        Node nodeTitle = this.doc.createElement("title");
        nodeTitle.appendChild(this.doc.createTextNode(alarm.getTitle()));
        // Definimos el hour
        Node nodeHour = this.doc.createElement("hour");
        nodeHour.appendChild(this.doc.createTextNode(alarm.getHour()));
        // Definimos minute
        Node nodeMinute = this.doc.createElement("minute");
        nodeMinute.appendChild(this.doc.createTextNode(alarm.getMinute()));
        // Definimos active
        Node nodeActive = this.doc.createElement("active");
        nodeActive.appendChild(this.doc.createTextNode(alarm.isActive() ? "1" : "0"));
        // Definimos daysOfWeek
        Node nodeDaysOfWeek = this.doc.createElement("daysOfWeek");
        // Definimos subNodos daysOfWeek
        Node nodeDay1 = this.doc.createElement("day1");
        nodeDay1.appendChild(this.doc.createTextNode(alarm.getDaysOfWeek().isDay1() ? "1" : "0"));
        Node nodeDay2 = this.doc.createElement("day2");
        nodeDay2.appendChild(this.doc.createTextNode(alarm.getDaysOfWeek().isDay2() ? "1" : "0"));
        Node nodeDay3 = this.doc.createElement("day3");
        nodeDay3.appendChild(this.doc.createTextNode(alarm.getDaysOfWeek().isDay3() ? "1" : "0"));
        Node nodeDay4 = this.doc.createElement("day4");
        nodeDay4.appendChild(this.doc.createTextNode(alarm.getDaysOfWeek().isDay4() ? "1" : "0"));
        Node nodeDay5 = this.doc.createElement("day5");
        nodeDay5.appendChild(this.doc.createTextNode(alarm.getDaysOfWeek().isDay5() ? "1" : "0"));
        Node nodeDay6 = this.doc.createElement("day6");
        nodeDay6.appendChild(this.doc.createTextNode(alarm.getDaysOfWeek().isDay6() ? "1" : "0"));
        Node nodeDay7 = this.doc.createElement("day7");
        nodeDay7.appendChild(this.doc.createTextNode(alarm.getDaysOfWeek().isDay7() ? "1" : "0"));
        // Añadimos todos los subnodos daysOfWeek
        nodeDaysOfWeek.appendChild(nodeDay1);
        nodeDaysOfWeek.appendChild(nodeDay2);
        nodeDaysOfWeek.appendChild(nodeDay3);
        nodeDaysOfWeek.appendChild(nodeDay4);
        nodeDaysOfWeek.appendChild(nodeDay5);
        nodeDaysOfWeek.appendChild(nodeDay6);
        nodeDaysOfWeek.appendChild(nodeDay7);
        // Añadir todos los nodos a la raíz
        nodeAlarm.appendChild(nodeTitle);
        nodeAlarm.appendChild(nodeHour);
        nodeAlarm.appendChild(nodeMinute);
        nodeAlarm.appendChild(nodeDaysOfWeek);
        nodeAlarm.appendChild(nodeActive);
        // Añadir nodo alarm al doc
        this.doc.getLastChild().appendChild(nodeAlarm);
    }
    //	Método que devuelve la lista de alarmas
    private NodeList obtenerListaNodos() {
        return this.doc.getElementsByTagName("alarm");
    }
    public ArrayList<Alarm> obtenerListaAlarma(){
        NodeList nList = this.obtenerListaNodos();
        ArrayList<Alarm> alarms = new ArrayList<Alarm>();
        Alarm alarm;
        DaysOfWeek days;
        for(int i = 0; i < nList.getLength(); i++){
            // Recorro la lista y obtengo el nodo en el que estoy actualmente
            alarm = new Alarm();
            days = new DaysOfWeek();
            Node node = nList.item(i);
            Element element = (Element)node;
            alarm.setTitle(element.getElementsByTagName("title").item(0).getTextContent());
            alarm.setHour(element.getElementsByTagName("hour").item(0).getTextContent());
            alarm.setMinute(element.getElementsByTagName("minute").item(0).getTextContent());
            alarm.setActive(binaryToBoolean(element.getElementsByTagName("active").item(0).getTextContent()));
            // Obtener nodo dias
            Element dias = (Element) element.getElementsByTagName("daysOfWeek").item(0);
            // Obtener valor de cada día
            days.setDay1(binaryToBoolean(dias.getElementsByTagName("day1").item(0).getTextContent()));
            days.setDay2(binaryToBoolean(dias.getElementsByTagName("day2").item(0).getTextContent()));
            days.setDay3(binaryToBoolean(dias.getElementsByTagName("day3").item(0).getTextContent()));
            days.setDay4(binaryToBoolean(dias.getElementsByTagName("day4").item(0).getTextContent()));
            days.setDay5(binaryToBoolean(dias.getElementsByTagName("day5").item(0).getTextContent()));
            days.setDay6(binaryToBoolean(dias.getElementsByTagName("day6").item(0).getTextContent()));
            days.setDay7(binaryToBoolean(dias.getElementsByTagName("day7").item(0).getTextContent()));
            // Setear dias a alarma
            alarm.setDaysOfWeek(days);
            alarms.add(alarm);
        }
        return alarms;
    }
    private boolean binaryToBoolean(String value){
        return "1".equals(value) ? true : false;
    }
    private void save(){
        DOMSource src = new DOMSource(this.doc);
        StreamResult file = new StreamResult(new File(context.getFilesDir(), FILE_NAME));
        try {
            this.transformer.transform(src, file);
        } catch (TransformerException e) {
//			Imprime un mensaje de error para informar dónde se ha producido
            System.out.println("---------------------------------");
            System.out.println("HA OCURRIDO UN PROBLEMA DURANTE");
            System.out.println("   LA OPERACIÓN DE GUARDADO");
            System.out.println("---------------------------------");
            System.out.println(e.getMessage());
        }
    }
    public void newAlarm(Alarm alarm){
        this.addAlarm(alarm);
        this.save();
    }
    //	Método que obtiene el nodo coincidente al título
    private Node obtenerNodoCoincidente(String titulo) {
//		Guardo en una variable la lista de nodos videojuego
        NodeList nList = this.obtenerListaNodos();
//		Recorre la lista de nodos y busca una coincidencia
        Node nodeTitulo = null;
        for(int cont = 0; cont < nList.getLength(); cont++ ) {
            Node node = nList.item(cont);
            Element element = (Element)node;
            nodeTitulo = element.getElementsByTagName("title").item(0);
            String thisTitulo = nodeTitulo.getTextContent();
            // Asigna el nuevo título
            if(thisTitulo.equals(titulo)) {
                return nodeTitulo;
            }
        }
        return nodeTitulo;
    }
    //	Método que elimina un elemento según el título
    private void eliminarAlarma(String titulo) {
//		Obtengo el elemento <videojuego> coincidente con el título
        Element elemento = (Element) this.obtenerNodoCoincidente(titulo).getParentNode();
//		Elimino el elemento <videojuego> de <videojuegos>
        elemento.getParentNode().removeChild(elemento);
    }
    public void deleteAlarm(Alarm alarm){
        eliminarAlarma(alarm.getTitle());
        save();
    }
}
