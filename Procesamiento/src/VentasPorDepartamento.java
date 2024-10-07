import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class VentasPorDepartamento {
    public static void main(String[] args) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File("sales.xml"));

        Element root = document.getDocumentElement();
        NodeList nodeList = root.getElementsByTagName("sale_record");

        Map<String, Double> ventasPorDepartamento = new HashMap<>();
        double ventasTotales = 0;

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element saleRecord = (Element) nodeList.item(i);
            String departamento = saleRecord.getElementsByTagName("department").item(0).getTextContent();
            double ventas = Double.parseDouble(saleRecord.getElementsByTagName("sales").item(0).getTextContent());

            ventasPorDepartamento.put(departamento, ventasPorDepartamento.getOrDefault(departamento, 0.0) + ventas);
            ventasTotales += ventas;
        }

        System.out.println("Reporte de Ventas por Departamento:");
        for (Map.Entry<String, Double> entry : ventasPorDepartamento.entrySet()) {
            System.out.println(entry.getKey() + ": $" + entry.getValue());
        }
        System.out.println("Ventas Totales: $" + ventasTotales);

    }
}


