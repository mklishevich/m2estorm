package essua.idea.m2estorm.dic;

import essua.idea.m2estorm.dic.FactoryMap;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class FactoryConfigParser {

    private DocumentBuilder documentBuilder;

    public FactoryConfigParser() throws ParserConfigurationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        documentBuilder = dbFactory.newDocumentBuilder();
    }

    public FactoryMap parse(InputStream stream) throws IOException, SAXException {
        return parse(documentBuilder.parse(stream));
    }

    public FactoryMap parse(File file) throws IOException, SAXException {
        return parse(documentBuilder.parse(file));
    }

    public FactoryMap parse(Document document) {
        HashSet<String> set = new HashSet<String>();
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

            if (entityArgumentPosition < 0) {
                entityArgumentPosition = 0;
            }

            set.add(methodName);
            map.put("\\" + className + "." + methodName, entityArgumentPosition);
        }

        return new FactoryMap(map, set);
    }

}
