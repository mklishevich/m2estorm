package essua.idea.m2estorm.dic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class FactoryMap {

    private Map<String, Integer> classMethods;
    private HashSet<String> methods;

    public FactoryMap(Map<String, Integer> classMethods, HashSet<String> set) {
        this.classMethods = classMethods;
        this.methods = set;
    }

    public FactoryMap() {
        this.classMethods = new HashMap<String, Integer>();
        this.methods = new HashSet<String>();
    }

    public HashSet<String> getMethods() {
        return methods;
    }

    public Map<String, Integer> getClassMethods() {
        return classMethods;
    }
}
