   package temp.com.mathworks.engine;

   import java.util.concurrent.ExecutionException;

   public class EngineException
     extends ExecutionException
   {
     public EngineException(String paramString)
     {
       super(paramString);
     }

     public EngineException(String paramString, Throwable paramThrowable) {
       super(paramString, paramThrowable);
     }

     public EngineException(Throwable paramThrowable) {
       super(paramThrowable);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\engine\EngineException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */