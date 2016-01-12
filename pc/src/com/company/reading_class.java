package com.company;
/*
Created by Msoner
Bu sınıf uygulamamızın ana ayar ve kullanıcı bilgilerinin tutulduğu config.xml dosyasını ve tüm stringlerin tutulduğu strings.properties dosyasını
okumak ve istenilen değişkenlere ulaşmak için yaratılmıştır.

 */

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class reading_class {
    private final File fxmlFile;
    private final String versiyon;
    private final String language;
    private final String name;
    private final String pc_name;
    private final String phone_brand;
    private final String computer_id;
    private final String connect_id;
    private final String mode;

    public reading_class() throws NullPointerException{
        fxmlFile =new File("config.xml");
        String[] values = config_xml_oku();
        versiyon = values[0];
        language = values[1];
        name = values[3];
        pc_name = values[4];
        phone_brand = values[5];
        computer_id = values[2];
        connect_id = values[6];
        mode = values[7];
    }
    private File get_xml_file_name(){
        return this.fxmlFile;
    }
    public String get_versiyon(){
        return this.versiyon;
    }
    public String get_computer_id(){
        return this.computer_id;
    }
    public String get_connect_id(){
        return this.connect_id;
    }
    public String get_name(){
        return this.name;
    }
    public String get_pc_name(){
        return this.pc_name;
    }
    public String get_phone_brand(){
        return this.phone_brand;
    }
    public String get_mode(){
        return this.mode;
    }
    public String get_language(){
        return this.language;
    }

    public void set_strings(String lang){
        Set_strings set = new Set_strings();
        ResourceBundle rb;
        if(lang.equals("en")){
            rb = ResourceBundle.getBundle("com.company.properties.strings_en_US");
        }
        else {
            rb = ResourceBundle.getBundle("com.company.properties.strings_tr_TR");
        }

        Enumeration <String> keys =rb.getKeys();
        while (keys.hasMoreElements()){
            String key = keys.nextElement();
            set.Set_strings(key,rb.getString(key));
        }

    }
    private String[] config_xml_oku(){
        String versiyon1=null;
        String computer_id1=null;
        String pc_name1=null;
        String name1=null;
        String language1=null;
        String phone_brand1=null;
        String connect_id1=null;
        String mode1=null;

        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(get_xml_file_name());
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("app_info");
            Node nNode = nList.item(0);
            Element eElement = (Element) nNode;

            versiyon1 = eElement.getElementsByTagName("versiyon").item(0).getTextContent();
            computer_id1 = eElement.getElementsByTagName("computer_id").item(0).getTextContent();
            name1 = eElement.getElementsByTagName("name").item(0).getTextContent();
            pc_name1 = eElement.getElementsByTagName("pc_name").item(0).getTextContent();
            language1 = eElement.getElementsByTagName("language").item(0).getTextContent();
            phone_brand1 = eElement.getElementsByTagName("phone_brand").item(0).getTextContent();
            connect_id1 = eElement.getElementsByTagName("connect_id").item(0).getTextContent();
            mode1 = eElement.getElementsByTagName("mode").item(0).getTextContent();

        } catch (Exception e) {
            exception_messages.show_message("Error","Not Found Config.xml file.Please Re-install this application.");
            System.exit(0);
        }

        return new String[]{versiyon1,language1,computer_id1,name1,pc_name1,phone_brand1,connect_id1,mode1};
    }
    public void update_xml_value(String value,String new_value) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(get_xml_file_name());
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName(value);
        Node nNode = nList.item(0);
        Element eElement = (Element) nNode;
        eElement.setTextContent(new_value);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(get_xml_file_name());
        transformer.transform(source, result);
    }
}
