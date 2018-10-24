   package temp.com.mathworks.mvm.eventmgr;

   import com.mathworks.mvm.MvmImpl;



















   public class SinkTextEvent
     implements FirableMvmEvent
   {
     private final String fText;
     private final StreamTypeEnum fStreamType;

     public static enum StreamTypeEnum
     {
       OUTPUT_STREAM,
       ERROR_STREAM,
       GENERIC_STREAM;



       private StreamTypeEnum() {}
     }


     public SinkTextEvent(StreamTypeEnum paramStreamTypeEnum, String paramString)
     {
       this.fStreamType = paramStreamTypeEnum;
       this.fText = paramString;
     }

     public String getText()
     {
       return this.fText;
     }

     public StreamTypeEnum getStreamType()
     {
       return this.fStreamType;
     }





     public static String getStaticEventType()
     {
       MvmImpl.loadLibrary();
       return nativeGetCppEventType();
     }


     public boolean equals(Object paramObject)
     {
       return ((paramObject instanceof SinkTextEvent)) && (equals((SinkTextEvent)paramObject));
     }

     public boolean equals(SinkTextEvent paramSinkTextEvent)
     {
       return (paramSinkTextEvent != null) && (getStreamType() == paramSinkTextEvent.getStreamType()) && (getText().equals(paramSinkTextEvent.getText()));
     }




     public int hashCode()
     {
       int i = this.fStreamType == null ? 0 : this.fStreamType.hashCode();
       int j = this.fText == null ? 0 : this.fText.hashCode();

       return j ^ i;
     }


     public String toString()
     {
       return SinkTextEvent.class.getSimpleName() + ": " + this.fStreamType.toString() + " " + this.fText;
     }

     private static native String nativeGetCppEventType();

     private static native SinkTextEvent nativeFactory(long paramLong);

     private static native long nativeFactory(SinkTextEvent paramSinkTextEvent);
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\eventmgr\SinkTextEvent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */