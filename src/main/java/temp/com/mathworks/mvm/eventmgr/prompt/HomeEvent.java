   package temp.com.mathworks.mvm.eventmgr.prompt;

   import com.mathworks.mvm.MvmImpl;
   import com.mathworks.mvm.eventmgr.FirableMvmEvent;














   public final class HomeEvent
     implements FirableMvmEvent
   {
     public static String getStaticEventType()
     {
       MvmImpl.loadLibrary();
       return nativeGetCppEventType();
     }

     public boolean equals(Object paramObject)
     {
       return ((paramObject instanceof HomeEvent)) && (equals((HomeEvent)paramObject));
     }

     public boolean equals(HomeEvent paramHomeEvent) {
       return paramHomeEvent != null;
     }

     public int hashCode()
     {
       return 0;
     }

     public String toString()
     {
       return HomeEvent.class.getSimpleName();
     }

     private static native String nativeGetCppEventType();

     private static native HomeEvent nativeFactory(long paramLong);

     private static native long nativeFactory(HomeEvent paramHomeEvent);
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\eventmgr\prompt\HomeEvent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */