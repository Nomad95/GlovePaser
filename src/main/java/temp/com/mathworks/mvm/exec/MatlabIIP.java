   package temp.com.mathworks.mvm.exec;

   import com.mathworks.mvm.MvmImpl;
   import java.util.concurrent.atomic.AtomicBoolean;








   class MatlabIIP
   {
     static
     {
       MvmImpl.loadLibrary();
     }

     private AtomicBoolean fUsed = new AtomicBoolean();



     private final long fNativeIIP;




     private MatlabIIP(long paramLong)
     {
       assert (paramLong != 0L);
       this.fNativeIIP = paramLong;
     }





     private long getNativeIIP()
     {
       if (this.fUsed.compareAndSet(false, true)) return this.fNativeIIP;
       return 0L;
     }





     protected void finalize()
       throws Throwable
     {
       try
       {
         if (!this.fUsed.get()) nativeReleaseIIP(this.fNativeIIP);
       }
       finally
       {
         super.finalize();
       }
     }

     private native void nativeReleaseIIP(long paramLong);
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\exec\MatlabIIP.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */