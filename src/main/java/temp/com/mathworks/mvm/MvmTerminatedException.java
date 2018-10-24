   package temp.com.mathworks.mvm;



   public class MvmTerminatedException
     extends RuntimeException
   {
     public MvmTerminatedException()
     {
       this(false);
     }



     public MvmTerminatedException(boolean paramBoolean)
     {
       super("The " + (paramBoolean ? "MVM" : "MvmFactory") + " has terminated");
     }

     public MvmTerminatedException(String paramString) {
       super(paramString);
     }

     public MvmTerminatedException(String paramString, Throwable paramThrowable)
     {
       super(paramString, paramThrowable);
     }

     public MvmTerminatedException(Throwable paramThrowable)
     {
       super(paramThrowable);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\MvmTerminatedException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */