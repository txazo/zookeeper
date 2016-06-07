package test.zookeeper.junit4;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ZooConfig {

    String config() default "/zookeeper.properties";

    int timeout() default 5000;

    String[] initNodes() default {};

}
