package essua.idea.m2estorm;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import essua.idea.m2estorm.dic.FactoriesParser;
import org.jetbrains.annotations.NotNull;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class M2EProjectComponent implements ProjectComponent {

    private static final String COMPONENT_NAME = "M2EProjectComponent";
    private static final String FACTORIES_FILE_PATH = "factories.xml";

    private Project project;

    private Map<String, Integer> factoriesMap;
    private Long factoriesMapLastModified;

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

    public Map<String, Integer> getFactoriesMap()
    {
        if (factoriesMap != null) {
            return factoriesMap;
        }

        String defaultFactoriesFilePath = getPath(project, FACTORIES_FILE_PATH);

        File xmlFile = new File(defaultFactoriesFilePath);
        if (!xmlFile.exists()) {
            return new HashMap<String, Integer>();
        }

        Long xmlFileLastModified = xmlFile.lastModified();
        if (xmlFileLastModified.equals(factoriesMapLastModified)) {
            return factoriesMap;
        }

        try {
            FactoriesParser factoriesParser = new FactoriesParser();
            factoriesMap = factoriesParser.parse(xmlFile);
            factoriesMapLastModified = xmlFileLastModified;

            return factoriesMap;
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
