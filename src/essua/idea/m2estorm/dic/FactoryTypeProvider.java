package essua.idea.m2estorm.dic;

import com.intellij.openapi.project.DumbService;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.impl.MethodImpl;
import com.jetbrains.php.lang.psi.elements.impl.MethodReferenceImpl;
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl;
import com.jetbrains.php.lang.psi.resolve.types.PhpType;
import com.jetbrains.php.lang.psi.resolve.types.PhpTypeProvider;
import essua.idea.m2estorm.M2EProjectComponent;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class FactoryTypeProvider implements PhpTypeProvider {

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

        M2EProjectComponent projectComponent = e.getProject().getComponent(M2EProjectComponent.class);
        Map<String, Integer> factoriesMap = projectComponent.getFactoriesMap();

        if (null == factoriesMap) {
            return null;
        }

        if (!factoriesMap.containsKey(calledMethodFQN)) {
            return null;
        }

        String modelName = getModelName((MethodReferenceImpl) e, factoriesMap.get(calledMethodFQN));

        if (null == modelName) {
            return null;
        }

        return new PhpType().add("\\Ess_M2ePro_Model_" + modelName);
    }

    @Nullable
    private String getCalledMethodFQN(PsiElement e) {
        if (!(e instanceof MethodReferenceImpl)) {
            return null;
        }

        if (null == e.getReference()) {
            return null;
        }

        MethodReferenceImpl methodRefImpl = (MethodReferenceImpl) e;

        PsiElement resolvedReference = methodRefImpl.getReference().resolve();

        if (!(resolvedReference instanceof MethodImpl)) {
            return null;
        }

        MethodImpl methodImpl = (MethodImpl)resolvedReference;

        // i.e. \Ess_M2ePro_Helper_Component_Ebay.getModel
        return methodImpl.getFQN();
    }

    private String getModelName(MethodReferenceImpl e, Integer factoryEntityArgumentPosition) {
        String modelName = null;

        PsiElement[] parameters = e.getParameters();

        if (parameters.length >= factoryEntityArgumentPosition && parameters[factoryEntityArgumentPosition] instanceof StringLiteralExpressionImpl) {
            modelName = parameters[factoryEntityArgumentPosition].getText(); // quoted string
            modelName = modelName.substring(1, modelName.length() - 1);
        }

        return modelName;
    }

}
