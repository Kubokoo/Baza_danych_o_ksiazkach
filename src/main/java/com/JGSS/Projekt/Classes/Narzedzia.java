package com.JGSS.Projekt.Classes;

import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Narzedzia {
    public static String pobierzSzablon(String plik, ServletContext context) throws IOException
    {
        StringBuffer wyjscie = new StringBuffer("");

        String tekst = "";
        InputStream is = context.getResourceAsStream("/WEB-INF/View/"+plik);
        if (is != null)
        {
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr);
            while (  (tekst = reader.readLine()) != null )
            {
                wyjscie.append(tekst).append("\n");
            }
        }
        else wyjscie.append("Brak pliku "+plik);
        return wyjscie.toString();
    }

    public static String uzupelnij(String szablon, String znacznik,
                                   String plik, ServletContext context) throws IOException
    {
        StringBuffer wyjscie = new StringBuffer("");

        String tekst = "";
        InputStream is = context.getResourceAsStream("/WEB-INF/View/"+plik+".html");
        if (is != null)
        {
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr);
            while (  (tekst = reader.readLine()) != null )
            {
                wyjscie.append(tekst).append("\n");
            }
        }
        else wyjscie.append("Brak pliku " + plik + ".html");

        return szablon.replace("[["+znacznik+"]]", wyjscie.toString());
    }

    public static int parsujInteger(String wejscie, int domyslna)
    {
        int wyjscie = domyslna;
        try {
            wyjscie = Integer.parseInt(wejscie);
        }
        catch (NumberFormatException nfe) { // null lub zły format
            wyjscie = domyslna;
        }
        return wyjscie;
    }

    public static String dodajOnLoad(String wejscie, String onLoad)
    {
        String wynik="";
        if (onLoad.length()>0)
            wynik = "onLoad=\""+onLoad+"\"";
        return wejscie.replace("[[ONLOAD]]", wynik);
    }

    public static String dodajJS(String wejscie, String js)
    {
        StringBuffer wynik = new StringBuffer();
        if (js.length()>0)
        {
            String[] jsy = js.split(";");
            for (String javascript: jsy)
                wynik.append("<script type=\"text/javascript\" src=\""+
                        javascript+"\"></script>\n");
        }

        return wejscie.replace("[[JS]]", wynik.toString());
    }

    public static String filtrujStrone(String wejscie, String prawidlowe)
    {
        String wyjscie = "main";
        String[] strony = prawidlowe.split(";");
        if (wejscie==null) wejscie="main";

        for (String poprawna: strony)
        {
            if (wejscie.equals(poprawna)) wyjscie = wejscie;
        }
        return wyjscie;
    }

}