   package temp.com.mathworks.util;

   import java.lang.reflect.Array;
   import java.lang.reflect.Constructor;
   import java.lang.reflect.InvocationTargetException;
   import java.lang.reflect.ParameterizedType;
   import java.lang.reflect.Type;





   public abstract class TypeToken<T>
   {
     private final Type fType;
     private volatile Constructor<?> fConstructor;

     protected TypeToken()
     {
       Type localType = getClass().getGenericSuperclass();
       if ((localType instanceof Class)) {
         throw new RuntimeException("Missing type parameter.");
       }
       this.fType = ((ParameterizedType)localType).getActualTypeArguments()[0];
     }





     public T newInstance()
       throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
     {
       if (this.fConstructor == null) {
         this.fConstructor = getRawClass().getConstructor(new Class[0]);
       }
       return (T)this.fConstructor.newInstance(new Object[0]);
     }

     public T[] newArray(int paramInt) {
       return (Object[])Array.newInstance(getRawClass(), paramInt);
     }



     public Type getType()
     {
       return this.fType;
     }

     public Class<T> getRawClass() {
       return (this.fType instanceof Class) ? (Class)this.fType : (Class)((ParameterizedType)this.fType).getRawType();
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\TypeToken.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */