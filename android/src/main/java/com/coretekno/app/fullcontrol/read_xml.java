package com.coretekno.app.fullcontrol;
/*
Created by Msoner
Bu sınıf uygulamamızın ana ayar ve kullanıcı bilgilerinin tutulduğu config.xml dosyasını ve tüm stringlerin tutulduğu strings.properties dosyasını
okumak ve istenilen değişkenlere ulaşmak için yaratılmıştır.

 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.apache.http.HttpException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class read_xml extends AppCompatActivity{
    private static File fxmlFile;
    private static String name;
    private String phone_brand;
    private static String connect_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);
        fxmlFile =new File(getFilesDir()+"/config.xml");
        String[] values;
        values=config_xml_oku();
        name = values[0];
        phone_brand = values[1];
        connect_id = values[2];
        server_requests server = null;
        try {
            server = new server_requests(connect_id);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);


    }
    private static File get_xml_file_name(){
        return fxmlFile;
    }
    public static String get_connect_id(){
        return connect_id;
    }
    public static String get_user_name(){
        return name;
    }
    public static void set_username(String incomming){
        name = incomming;
    }

    private String[] config_xml_oku(){
        String name1=null;
        String phone_brand1=null;
        String connect_id1=null;

        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(get_xml_file_name());
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("app_info");
            Node nNode = nList.item(0);
            Element eElement = (Element) nNode;

            name1 = eElement.getElementsByTagName("name").item(0).getTextContent();
            phone_brand1 = eElement.getElementsByTagName("phone_brand").item(0).getTextContent();
            connect_id1 = eElement.getElementsByTagName("connect_id").item(0).getTextContent();



        } catch (Exception e) {
            Intent intend = new Intent(this,Login.class);
            startActivity(intend);

        }

        return new String[]{name1,phone_brand1,connect_id1};
    }
    public static void update_xml_value(String value,String new_value) throws ParserConfigurationException, IOException, SAXException, TransformerException {

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
    public static String[] read_sign_up(String respond) throws ParserConfigurationException, IOException, SAXException {
        String[] veriler = new String[1];
        veriler[0]="hata_bos";
            if (respond.indexOf("hata") == 0) {
                veriler[0] = respond;
                return veriler;
            } else {
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(respond));
                Document doc = db.parse(is);
                doc.getDocumentElement().normalize();
                NodeList nList = doc.getElementsByTagName("app_info");
                Node nNode = nList.item(0);
                Element eElement = (Element) nNode;
                veriler[0] = eElement.getElementsByTagName("connect_id").item(0).getTextContent();
                return veriler;
            }
    }

    public static String[] parse(String respond) throws ParserConfigurationException, IOException, SAXException {
        String[] veriler = new String[3];

            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(respond));
            Document doc = db.parse(is);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("usage_values");
            Node nNode = nList.item(0);
            Element eElement = (Element) nNode;
            veriler[0] = eElement.getElementsByTagName("cpu").item(0).getTextContent();
            veriler[1] = eElement.getElementsByTagName("ram").item(0).getTextContent();
            veriler[2] = eElement.getElementsByTagName("temp").item(0).getTextContent();
            return veriler;

    }


}

