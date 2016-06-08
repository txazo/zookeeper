package test;

import org.junit.Assert;

import java.lang.reflect.Field;

public abstract class FieldTraverser extends Assert {

    protected void traverseField(Object obj, FieldCallback callback) {
        if (obj != null) {
            Field[] fields = obj.getClass().getDeclaredFields();
            try {
                for (Field field : fields) {
                    field.setAccessible(true);
                    callback.doField(field.getName(), field.get(obj));
                }
            } catch (Exception e) {
                throw new RuntimeException("traverseField failed", e);
            }
        }
    }

}
