package org.example;

//import com.jayway.restassured.path.json.JsonPath;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class ServicioRemoto {

    public String llamaServicioRemoto(String monedaOrigen, String monedaDestino, String Importe) {
        String urlString = "http://api.exchangeratesapi.io/v1/latest?access_key=b70940928ce3726ef24a35fdb80e2caf";
        StringBuilder result = new StringBuilder();
        JSONObject joSalida = new JSONObject();
        if (!esNumerico(Importe)){
            try {
                joSalida.put("Error","El importe ingresado no es tipo numerico");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            return joSalida.toString();
        }


        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String JSONR = result.toString();
        String FecConv = new String();
        double tasaOrigen = 0;
        double tasaDestino = 0;
        double tasa = 0;
        double importeEUR = 0;
        double importeDest = 0;
        DecimalFormat formateador = new DecimalFormat("0.00");
        try {
            JSONObject joEntrada = new JSONObject(new JSONTokener(JSONR));
            JSONObject rates = joEntrada.getJSONObject("rates");
            FecConv = joEntrada.optString("date");
            tasaOrigen = rates.optDouble(monedaOrigen);
            tasaDestino = rates.optDouble(monedaDestino);
            importeEUR = Double.parseDouble(Importe) / tasaOrigen;
            importeDest = importeEUR * tasaDestino;
            tasa = Double.parseDouble(Importe) / importeDest;

            joSalida.put("FechaConversion",FecConv);
            joSalida.put("TasaConversion", tasa);
            joSalida.put("MontoOrig", Importe);
            joSalida.put("MontoResu", formateador.format(importeDest));

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return joSalida.toString();
    }

    public static boolean esNumerico(String valor){
        try{
            if(valor!= null){
                Integer.parseInt(valor);
            }
        }catch(NumberFormatException nfe){
            return false;
        }
        return true;
    }
}
