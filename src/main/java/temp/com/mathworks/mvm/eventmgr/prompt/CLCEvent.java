   package temp.com.mathworks.mvm.eventmgr.prompt;

   import com.mathworks.mvm.MvmImpl;
   import com.mathworks.mvm.eventmgr.FirableMvmEvent;














   public final class CLCEvent
     implements FirableMvmEvent
   {
     public static String getStaticEventType()
     {
       MvmImpl.loadLibrary();
       return nativeGetCppEventType();
     }

     public boolean equals(Object paramObject)
     {
       return ((paramObject instanceof CLCEvent)) && (equals((CLCEvent)paramObject));
     }

     public boolean equals(CLCEvent paramCLCEvent) {
       return paramCLCEvent != null;
     }

     public int hashCode()
     {
       return 0;
     }

     public String toString()
     {
       return CLCEvent.class.getSimpleName();
     }

     private static native String nativeGetCppEventType();

     private static native CLCEvent nativeFactory(long paramLong);

     private static native long nativeFactory(CLCEvent paramCLCEvent);
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\eventmgr\prompt\CLCEvent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */