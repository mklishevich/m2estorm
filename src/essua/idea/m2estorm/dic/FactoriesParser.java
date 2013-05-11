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

public class FactoriesParser {

    private DocumentBuilder documentBuilder;

    public FactoriesParser() throws ParserConfigurationException {
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

            if (!node.hasAttribute("entity_argument_position")
                || !node.hasAttribute("class")
                || !node.hasAttribute("method")
            ) {
                continue;
            }

            String className = node.getAttribute("class");
            String methodName = node.getAttribute("method");
            int entityArgumentPosition = Integer.parseInt(node.getAttribute("entity_argument_position"));

            map.put("\\" + className + "." + methodName, entityArgumentPosition);
        }

        return map;
    }

}
