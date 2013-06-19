package essua.idea.m2estorm;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import essua.idea.m2estorm.dic.FactoryConfigParser;
import org.jetbrains.annotations.NotNull;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class M2EProjectComponent implements ProjectComponent {

    private static final String COMPONENT_NAME = "M2EProjectComponent";
    private static final String FACTORIES_FILE_PATH = "m2estorm.xml";

    private Project project;

    private Map<String, Integer> factoryMap;
    private Long factoryMapLastModified;

    public M2EProjectComponent(Project project) {
        this.project = project;
    }

    @Override
    public void projectOpened() {
    }

    @Override
    public void projectClosed() {
    }

    @Override
    public void initComponent() {
    }

    @Override
    public void disposeComponent() {
    }

    @NotNull
    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    public Map<String, Integer> getFactoryMap() {
        if (null != factoryMap) {
            return factoryMap;
        }

        String defaultFactoriesFilePath = getPath(project, FACTORIES_FILE_PATH);

        File xmlFile = new File(defaultFactoriesFilePath);
        if (!xmlFile.exists()) {
            factoryMap = new HashMap<String, Integer>();
            return factoryMap;
        }

        Long xmlFileLastModified = xmlFile.lastModified();
        if (xmlFileLastModified.equals(factoryMapLastModified)) {
            return factoryMap;
        }

        try {
            FactoryConfigParser factoryConfigParser = new FactoryConfigParser();
            factoryMap = factoryConfigParser.parse(xmlFile);
            factoryMapLastModified = xmlFileLastModified;

            return factoryMap;
        } catch (SAXException ignored) {
        } catch (IOException ignored) {
        } catch (ParserConfigurationException ignored) {
        }

        return new HashMap<String, Integer>();
    }

    private String getPath(Project project, String path) {
        if (!FileUtil.isAbsolute(path)) { // Project relative path
            path = project.getBasePath() + "/" + path;
        }

        return path;
    }
}
