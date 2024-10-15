import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            SAXParserFactory fabrica = SAXParserFactory.newInstance();
            SAXParser parser = fabrica.newSAXParser();
            ManejadorVentas manejador = new ManejadorVentas();
            parser.parse("sales.xml", manejador);
            manejador.imprimirInforme();
        } catch (FileNotFoundException e) {
            System.err.println("Error: El archivo no existe.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ManejadorVentas extends DefaultHandler {
    private Map<String, Double> ventas = new HashMap<>();
    private String departamento;
    private double monto;

    private boolean parsingDepartamento = true;
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals("registro_venta")) {
            parsingDepartamento = true;
        } else if (qName.equals("departamento")) {
            parsingDepartamento = true;
        } else if (qName.equals("monto")) {
            parsingDepartamento = false;
        }
    }


    public void endElement(String uri, String localName, String qName) {
        if (qName.equals("registro_venta")) {
            ventas.put(departamento, ventas.getOrDefault(departamento, 0.0) + monto);
        }
    }

    public void characters(char[] ch, int start, int length) {
        String valor = new String(ch, start, length).trim();
        if (!valor.isEmpty()) {
            if (departamento == null) {
                departamento = valor;
            } else {
                try {
                    monto = Double.parseDouble(valor);
                } catch (NumberFormatException e) {
                    System.err.println("Error: El valor '" + valor + "' no es un número válido para el monto.");
                    monto = 0;
                }
            }
        }
    }

    public void imprimirInforme() {
        double total = ventas.values().stream().mapToDouble(Double::doubleValue).sum();
        System.out.println("Informe de ventas por departamento:");
        ventas.forEach((k, v) -> System.out.println("Departamento: " + k + ", Ventas: " + v));
        System.out.println("Ventas totales: " + total);

    }
}