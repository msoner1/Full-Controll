package com.company;

/**
 * Created by msone on 9.11.2015.
 */
public class math {

    public boolean isNumeric(String str) //g�nderilen stringin say�sal olup olmad���na bakar.
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
    public String convert_int(String str)  // g�derilen bir string ifadenin i�indeki rakamlar� al�r ve birle�tirir.
    {
        String returning_value="";
        for(int i =0; i<str.length();i++){
            if(isNumeric(String.valueOf(str.charAt(i)))){
                returning_value +=String.valueOf(str.charAt(i));
            }
        }

        return returning_value;
    }
}
