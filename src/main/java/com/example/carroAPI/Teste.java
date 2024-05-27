package com.example.carroAPI;

public class Teste {
    public static void main(String[] args) {
        String st = "R$ 104.933,00";
        double dou = Double.valueOf(st
                                .replaceAll("R\\$ ", "")
                //.substring(3, st.length())
                .replaceAll("\\.", "")
                .replaceAll(",", "."));

        System.out.println(dou);

    }
}
