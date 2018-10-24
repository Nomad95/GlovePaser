   package temp.com.mathworks.mvm.exec;

   import java.lang.reflect.Array;
   import java.util.concurrent.CancellationException;

















   public class FutureFevalResult<V>
     extends FutureResult<V>
   {
     private boolean fScalar;

     FutureFevalResult(NativeFutureResult<V> paramNativeFutureResult, boolean paramBoolean, int paramInt)
     {
       super(paramNativeFutureResult, paramInt);
       assert ((!paramBoolean) || (paramInt == 1));
       this.fScalar = paramBoolean;
     }





     public boolean isScalar()
     {
       return this.fScalar;
     }




     protected V getInternal()
       throws InterruptedException, MvmExecutionException, CancellationException
     {
       Object localObject1 = super.getInternal();






       if ((localObject1 != null) && (this.fScalar) && (getNlhs() == 1) && (localObject1.getClass().isArray()) && (Array.getLength(localObject1) == 1))
       {


         Object localObject2 = Array.get(localObject1, 0);
         localObject1 = localObject2;
       } else if (getNlhs() == 0) {
         localObject1 = null;
       }
       return (V)localObject1;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\exec\FutureFevalResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */