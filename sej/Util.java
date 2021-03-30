package com.example.myapplication;

public class Util {

    public static boolean equalStringBytes(String str1, String str2)
    {
        byte[] s1 = str1.getBytes();
        byte[] s2 = str2.getBytes();

        if(s1.length != s2.length) return false;
        for(int i=0; i<s1.length; i++)
        {
            if(s1[i] != s2[i]) return false;
        }
        return true;
    }

    public static String cutChar(String s, char c)
    {
        String ns = "";
        for(int i=0; i<s.length(); i++)
        {
            if(s.charAt(i) == c) ;
            else ns += s.charAt(i);
        }
        return ns;
    }

    public static int search(String[] array, String str)
    {
        for(int i=0; i<array.length; i++)
        {
            if(array[i] == str)
            {
                return i;
            }
            else if(equalStringBytes(array[i], str))
            {

                return i;
            }
        }
        return -1;

    }
}
