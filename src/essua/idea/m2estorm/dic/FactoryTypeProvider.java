package essua.idea.m2estorm.dic;

import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.Method;
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl;
import com.jetbrains.php.lang.psi.resolve.types.PhpType;
import com.jetbrains.php.lang.psi.resolve.types.PhpTypeProvider;
import essua.idea.m2estorm.M2EProjectComponent;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Map;

public class FactoryTypeProvider implements PhpTypeProvider {

    private FactoryMap factoryMap = null;

    @Nullable
    @Override
    public PhpType getType(PsiElement e) {
        if (DumbService.getInstance(e.getProject()).isDumb()) {
            return null;
        }

        String calledMethodFQN = getCalledMethodFQN(e);

        if (null == calledMethodFQN) {
            return null;
        }

        Map<String, Integer> classMethods = getFactoryMap(e.getProject()).getClassMethods();

        if (!classMethods.containsKey(calledMethodFQN)) {
            return null;
        }

        String modelName = getModelName((MethodReference) e, classMethods.get(calledMethodFQN));

        if (null == modelName) {
            return null;
        }

        return new PhpType().add("\\Ess_M2ePro_Model_" + modelName);
    }

    @Nullable
    private String getCalledMethodFQN(PsiElement e) {
        if (!(e instanceof MethodReference)) {
            return null;
        }

        MethodReference methodReference = (MethodReference) e;

        HashSet<String> factoryMethods = getFactoryMap(e.getProject()).getMethods();

        String methodName = methodReference.getName();
        if (!factoryMethods.contains(methodName)) {
            return null;
        }

        PsiReference psiReference = methodReference.getReference();
        if (null == psiReference) {
            return null;
        }

        PsiElement resolvedReference = psiReference.resolve();
        if (!(resolvedReference instanceof Method)) {
            return null;
        }

        // i.e. \Ess_M2ePro_Helper_Component_Ebay.getModel
        return ((Method)resolvedReference).getFQN();
    }

    private String getModelName(MethodReference e, Integer factoryEntityArgumentPosition) {
        String modelName = null;

        PsiElement[] parameters = e.getParameters();

        if (parameters.length >= factoryEntityArgumentPosition && parameters[factoryEntityArgumentPosition] instanceof StringLiteralExpressionImpl) {
            modelName = parameters[factoryEntityArgumentPosition].getText(); // quoted string
            modelName = modelName.substring(1, modelName.length() - 1);
        }

        return modelName;
    }

    private FactoryMap getFactoryMap(Project p) {
        if (null == factoryMap) {
            factoryMap = p.getComponent(M2EProjectComponent.class).getFactoryMap();
        }

        return factoryMap;
    }
}
