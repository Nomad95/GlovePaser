   package temp.com.mathworks.mvm.eventmgr.prompt;

   import com.mathworks.mvm.MvmImpl;
   import com.mathworks.mvm.eventmgr.FirableMvmEvent;










   public final class IqmInputRequestEvent
     implements FirableMvmEvent
   {
     private final InputRequester fPrompt;

     public IqmInputRequestEvent(InputRequester paramInputRequester)
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
       return ((paramObject instanceof IqmInputRequestEvent)) && (equals((IqmInputRequestEvent)paramObject));
     }

     public boolean equals(IqmInputRequestEvent paramIqmInputRequestEvent) {
       return (paramIqmInputRequestEvent != null) && (getPrompt() == paramIqmInputRequestEvent.getPrompt());
     }


     public int hashCode()
     {
       return this.fPrompt == null ? 0 : this.fPrompt.hashCode();
     }

     public String toString()
     {
       String str = this.fPrompt == null ? "null" : this.fPrompt.toString();
       return IqmInputRequestEvent.class.getSimpleName() + ": " + str;
     }

     private static native String nativeGetCppEventType();

     private static native IqmInputRequestEvent nativeFactory(long paramLong);

     private static native long nativeFactory(IqmInputRequestEvent paramIqmInputRequestEvent);
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\eventmgr\prompt\IqmInputRequestEvent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */