   package temp.com.mathworks.mvm.context;

   import java.util.concurrent.Callable;















































   public class ThreadContext<T>
   {
     private final String fName;
     private final InheritableThreadLocal<T> fContext = new InheritableThreadLocal();

     ThreadContext(String paramString) {
       this.fName = paramString;
     }









     public void runWithContext(T paramT, final Runnable paramRunnable)
       throws RuntimeException
     {
       try
       {
         callWithContext(paramT, new Callable()
         {
           public Object call() throws Exception {
             paramRunnable.run();
             return null;
           }
         });
       } catch (RuntimeException localRuntimeException) {
         throw localRuntimeException;
       } catch (Exception localException) {
         throw new RuntimeException(localException);
       }
     }



     public T get()
     {
       return (T)this.fContext.get();
     }






     public <R> R callWithContext(T paramT, Callable<R> paramCallable)
       throws Exception
     {
       Object localObject1 = this.fContext.get();
       this.fContext.set(paramT);
       try {
         return (R)paramCallable.call();
       } finally {
         this.fContext.set(localObject1);
       }
     }


     public String toString()
     {
       return this.fName;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\context\ThreadContext.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */