package com.company;

import java.util.HashMap;
import java.util.Hashtable;

/**
 * Programda bulunan tüm stringleri strings.properties dosyalarýndan dile göre çekip setlemek için yazýlmýþtýr.

 */
public class Set_strings {

    private static Hashtable <String,String> source = new Hashtable<String,String>();
    private static HashMap <String,String> map = new HashMap<>(source);

    public void Set_strings(String key,String value){
        map.put(key,value);
    }
    public static String get_value(String key){

        return map.get(key).toString();
    }

}
