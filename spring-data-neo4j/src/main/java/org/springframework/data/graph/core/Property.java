package org.springframework.data.graph.core;

import java.util.Map;

/**
 * @author mh
 * @since 31.03.11
 */
public class Property {
    public final String name;
    public final Object value;

    public Property(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public static Property _(String name, Object value) {
        return new Property(name, value);
    }

    public static Property[] _(String name1, Object value1, String name2, Object value2) {
        return new Property[]{new Property(name1, value1), new Property(name2, value2)};
    }

    public static Property[] _(String name1, Object value1, String name2, Object value2, String name3, Object value3) {
        return new Property[]{new Property(name1, value1), new Property(name2, value2), new Property(name3, value3)};
    }

    public static Property[] _(Object... nameValuePairs) {
        if (nameValuePairs.length % 2 != 0)
            throw new IllegalArgumentException("there must be an even number of name value pairs");
        Property[] result = new Property[nameValuePairs.length / 2];
        for (int i = 0; i < result.length; i++) {
            result[i] = new Property(nameValuePairs[i * 2].toString(), nameValuePairs[i * 2 + 1]);
        }
        return result;
    }

    public static Property[] _(Map<String, Object> properties) {
        Property[] result = new Property[properties.size()];
        int i = 0;
        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            result[i++] = new Property(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
