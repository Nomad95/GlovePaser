   package temp.com.mathworks.mvm.eventmgr.prompt;

   import com.mathworks.mvm.MvmImpl;
   import com.mathworks.mvm.eventmgr.FirableMvmEvent;










   public final class InputRequestEvent
     implements FirableMvmEvent
   {
     private final InputRequester fPrompt;

     public InputRequestEvent(InputRequester paramInputRequester)
     {
       this.fPrompt = paramInputRequester;
       assert (this.fPrompt != null);
     }



     public InputRequester getPrompt()
     {
       return this.fPrompt;
     }




     public static String getStaticEventType()
     {
       MvmImpl.loadLibrary();
       return nativeGetCppEventType();
     }










     public boolean equals(Object paramObject)
     {
       return ((paramObject instanceof InputRequestEvent)) && (equals((InputRequestEvent)paramObject));
     }

     public boolean equals(InputRequestEvent paramInputRequestEvent) {
       return (paramInputRequestEvent != null) && (getPrompt() == paramInputRequestEvent.getPrompt());
     }


     public int hashCode()
     {
       return this.fPrompt == null ? 0 : this.fPrompt.hashCode();
     }

     public String toString()
     {
       String str = this.fPrompt == null ? "null" : this.fPrompt.toString();
       return InputRequestEvent.class.getSimpleName() + ": " + str;
     }

     private static native String nativeGetCppEventType();

     private static native InputRequestEvent nativeFactory(long paramLong);

     private static native long nativeFactory(InputRequestEvent paramInputRequestEvent);
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\eventmgr\prompt\InputRequestEvent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */