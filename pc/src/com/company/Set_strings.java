package com.company;

import java.util.HashMap;
import java.util.Hashtable;

/**
 * Programda bulunan t�m stringleri strings.properties dosyalar�ndan dile g�re �ekip setlemek i�in yaz�lm��t�r.

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
