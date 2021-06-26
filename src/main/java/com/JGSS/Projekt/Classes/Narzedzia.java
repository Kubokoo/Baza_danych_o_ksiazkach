package com.JGSS.Projekt.Classes;

public class Narzedzia {
    public static String filtrujStrone(String wejscie, String prawidlowe)
    {
        String wyjscie = "main";
        String[] strony = prawidlowe.split(";");
        if (wejscie == null) wejscie = "main";

        for (String poprawna: strony)
        {
            if (wejscie.equals(poprawna)) {
                wyjscie = wejscie;
                break;
            }
        }
        return wyjscie;
    }

}