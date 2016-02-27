package com.company;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Update mevcut mu diye kontrol eder ve mevcutsa indirir eski versiyonu siler.
 */
public class update_control_and_install{

    public static void control() throws IOException, ParserConfigurationException, SAXException, TransformerException, InterruptedException {
            reading_class xml_oku = new reading_class();
            server_requests server = new server_requests();
            int this_versiyon = Integer.parseInt(xml_oku.get_versiyon());
            if(server.server_versiyon()>this_versiyon){ //update var
                update_download();
            }
        server_requests.http_get_request("pc_requests.php","pc_is_open=1");
        delete_old_versiyon();

    }
    private static void update_download() throws IOException, ParserConfigurationException, SAXException, TransformerException {
        reading_class read_xml = new reading_class();
        Integer new_versiyon = Integer.parseInt(read_xml.get_versiyon())+1;

        URL url =new URL("http://46.101.231.241/full_control.zip");
        String file= "C:\\users\\"+System.getProperty("user.name")+"\\full_control\\full_control.zip";
        HttpURLConnection c = (HttpURLConnection) url.openConnection();
        c.getResponseCode();
        InputStream inputStream = c.getInputStream();
        FileOutputStream outputStream = new FileOutputStream(file);
        int bytesRead = -1;
        byte[] buffer = new byte[4096];
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        outputStream.close();
        inputStream.close();
        File filem = new File(file);

        if(filem.exists() && !filem.isDirectory()) {//Update indi demektir.
            //Burada indirilen jar �al��t�r�lacak �al��an jar durdurulacak ve indirilen jar ilk a��l��ta eski jar� silecek
            ZipFile zip = null;
            try {
                zip = new ZipFile(filem);
            } catch (ZipException e) {
            }
            try {
                zip.extractAll("C:\\users\\"+System.getProperty("user.name")+"\\full_control");
            } catch (ZipException e) {
            }
            filem.delete();//indirilen zip siliniyor.
            read_xml.update_xml_value("versiyon",new_versiyon.toString());
            Process p = Runtime.getRuntime().exec("full_control_v"+new_versiyon+".exe");
            System.exit(0);
        }

    }
    private static void delete_old_versiyon(){
        reading_class read_xml = new reading_class();
        int old_versiyon = Integer.parseInt(read_xml.get_versiyon())-1;
        while (old_versiyon>0) {
            File filem = new File("C:\\users\\" + System.getProperty("user.name") + "\\full_control\\full_control_v" + old_versiyon + ".exe");
            if (filem.exists() && !filem.isDirectory()) {//Update indi demektir.
                filem.delete();
            }//Eski versiyonu silme i�lemi
            old_versiyon--;
        }

    }

}
