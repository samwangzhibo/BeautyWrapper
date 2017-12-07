package com.samnie.beautypic.util;

/**
 * Created by samwangzhibo on 2017/12/6.
 */

public enum  PageIndexPreference implements PreferenceUtils.DefaultValueInterface {
    KEY_PAGEINDEX("");

    private Object defaultValue;
    static String namespace;

    PageIndexPreference(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public Object getDefaultValue() {
        return defaultValue;
    }

    @Override
    public String getNameSpace() {
        namespace = namespace == null ?this.getDeclaringClass().getSimpleName():namespace;
        return namespace;
    }

}
