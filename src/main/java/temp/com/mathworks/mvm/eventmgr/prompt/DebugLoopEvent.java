   package temp.com.mathworks.mvm.eventmgr.prompt;

   import com.mathworks.mvm.MvmImpl;
   import com.mathworks.mvm.eventmgr.FirableMvmEvent;
   import java.util.EnumSet;
   import org.jetbrains.annotations.Nullable;















   public final class DebugLoopEvent
     implements FirableMvmEvent
   {
     @Nullable
     private final InputRequester fLoop;
     private final LoopState fState;

     public static enum LoopState
     {
       ENTERED,
       EXITED;



       private LoopState() {}
     }


     public DebugLoopEvent(InputRequester paramInputRequester, LoopState paramLoopState)
     {
       this.fLoop = (getAllLoops().contains(paramInputRequester) ? paramInputRequester : null);
       this.fState = paramLoopState;
       assert (this.fState != null);
     }






     public InputRequester getWhichLoop()
     {
       return this.fLoop;
     }



     public LoopState getState()
     {
       return this.fState;
     }




     public static String getStaticEventType()
     {
       MvmImpl.loadLibrary();
       return nativeGetCppEventType();
     }



     public static EnumSet<InputRequester> getAllLoops()
     {
       return InputRequester.getDebugPrompts();
     }

     public boolean equals(Object paramObject)
     {
       return ((paramObject instanceof DebugLoopEvent)) && (equals((DebugLoopEvent)paramObject));
     }

     public boolean equals(DebugLoopEvent paramDebugLoopEvent) {
       return (paramDebugLoopEvent != null) && (getWhichLoop() == paramDebugLoopEvent.getWhichLoop()) && (getState() == paramDebugLoopEvent.getState());
     }



     public int hashCode()
     {
       int i = this.fLoop == null ? 0 : this.fLoop.hashCode();
       int j = this.fState == null ? 0 : this.fState.hashCode();

       return i ^ j;
     }

     public String toString()
     {
       String str1 = this.fLoop == null ? "null" : this.fLoop.toString();
       String str2 = this.fState == null ? "null" : this.fState.toString();
       return DebugLoopEvent.class.getSimpleName() + ": " + str1 + " " + str2;
     }

     private static native String nativeGetCppEventType();

     private static native DebugLoopEvent nativeFactory(long paramLong);

     private static native long nativeFactory(DebugLoopEvent paramDebugLoopEvent);
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\eventmgr\prompt\DebugLoopEvent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */