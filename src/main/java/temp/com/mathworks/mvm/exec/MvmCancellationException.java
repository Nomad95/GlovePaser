   package temp.com.mathworks.mvm.exec;

   import java.util.concurrent.CancellationException;




























   public class MvmCancellationException
     extends CancellationException
   {
     private final MvmInterruptedException fMvmCause;

     MvmCancellationException(MvmInterruptedException paramMvmInterruptedException)
     {
       this.fMvmCause = paramMvmInterruptedException;
     }



     public MvmInterruptedException getMvmCause()
     {
       return this.fMvmCause;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\exec\MvmCancellationException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */