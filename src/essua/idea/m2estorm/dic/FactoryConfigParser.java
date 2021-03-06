package essua.idea.m2estorm.dic;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class FactoryConfigParser {

    private DocumentBuilder documentBuilder;

    public FactoryConfigParser() throws ParserConfigurationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        documentBuilder = dbFactory.newDocumentBuilder();
    }

    public Map<String, Integer> parse(InputStream stream) throws IOException, SAXException {
        return parse(documentBuilder.parse(stream));
    }

    public Map<String, Integer> parse(File file) throws IOException, SAXException {
        return parse(documentBuilder.parse(file));
    }

    public Map<String, Integer> parse(Document document) {
        Map<String, Integer> map = new HashMap<String, Integer>();

        NodeList servicesNodes = document.getElementsByTagName("factory");
        for (int i = 0; i < servicesNodes.getLength(); i++) {
            Element node = (Element) servicesNodes.item(i);

            if (!node.hasAttribute("signature")) {
                continue;
            }

            String signature = node.getAttribute("signature");

            Integer entityArgumentPosition = 0;
            if (node.hasAttribute("entity_argument_position")) {
                entityArgumentPosition = Integer.parseInt(node.getAttribute("entity_argument_position"));
            }

            map.put(signature, entityArgumentPosition);
        }

        return map;
    }

}
