   package temp.com.mathworks.mvm.context;

   import com.mathworks.mvm.MVM;
   import com.mathworks.mvm.MvmFactory;
   import java.util.concurrent.Callable;









































































   public final class MvmContext
   {
     public static final ThreadContext<MVM> CONTEXT = new ThreadContext("MVM Context");













     public static MVM get()
       throws IllegalStateException
     {
       MVM localMVM = (MVM)CONTEXT.get();

       if (localMVM == null) return MvmFactory.getCurrentMVM();
       return localMVM;
     }


     public static void runWithContext(MVM paramMVM, Runnable paramRunnable)
     {
       CONTEXT.runWithContext(paramMVM, paramRunnable);
     }

     public static <T> T callWithContext(MVM paramMVM, Callable<T> paramCallable)
       throws Exception
     {
       return (T)CONTEXT.callWithContext(paramMVM, paramCallable);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\context\MvmContext.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */