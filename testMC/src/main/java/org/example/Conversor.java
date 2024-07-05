package org.example;

import static spark.Spark.*;


public class Conversor {
    public static void main(String[] args) {
        get("/convertir", (req, res) -> {
            String importe = req.queryParams("importe");
            String monedaOrigen = req.queryParams("monedaorigen");
            String monedaDestino = req.queryParams("monedadestino");
            ServicioRemoto conversor = new ServicioRemoto();
            return conversor.llamaServicioRemoto(monedaOrigen, monedaDestino, importe);
        });
    }
}

