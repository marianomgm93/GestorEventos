package Utilidades;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public class JSONUtiles {


    public static void uploadJSON(JSONArray jsonArray, String archive) {
        try {
            BufferedWriter salida = new BufferedWriter(new FileWriter(archive));
            salida.write(jsonArray.toString());
            salida.flush();
            salida.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void uploadJSON(JSONObject jsonObject, String archive) {
        try {
            BufferedWriter salida = new BufferedWriter(new FileWriter(archive));
            salida.write(jsonObject.toString());
            salida.flush();
            salida.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String downloadJSON(String archive) {
        StringBuilder contenido = new StringBuilder();
        String lectura = "";
        try {
            BufferedReader entrada = new BufferedReader(new FileReader(archive));
            while ((lectura = entrada.readLine()) != null) {
                contenido.append(lectura);
            }
            entrada.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return contenido.toString();
    }


}
