$(document).ready(function() {
    $('#miFormulario').on('submit', function(event) {
        event.preventDefault();

        var importe = $('#importe').val();
        var monedaorigen = $('#monOrig').val();
        var monedadest = $('#monDest').val();

        // URL del servicio
        var apiUrl = 'http://localhost:4567/convertir';

        $.ajax({
            url: apiUrl,
            type: 'GET',
            data: {
                importe: importe,
                monedaorigen: monedaorigen,
                monedadestino: monedadest
            },
            dataType: 'json',
            success: function(response) {
                if (response["FechaConversion"]==null) {
                    $('#resultado').html('<p>Error: ' + response["Error"] + '</p>');
                } else {
                    $('#resultado').html('<p>Fecha de Conversion: ' + response["FechaConversion"] + '</p>\n<p>Monto Original: ' + response["MontoOrig"] + '</p>\n<p>Monto Convertido: ' + response["MontoResu"] + '</p>\n<p>Tasa Aplicada: ' + response["TasaConversion"] + '</p>\n ');
                }
            },
            error: function(error) {
                $('#resultado').html('<p>Error: ' + JSON.stringify(error) + '</p>');
            }
        });

    });
});