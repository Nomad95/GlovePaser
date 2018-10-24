package temp.com.mathworks.util.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.PARAMETER})
public @interface StaticImport {}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\annotations\StaticImport.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */