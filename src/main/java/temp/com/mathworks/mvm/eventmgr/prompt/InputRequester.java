   package temp.com.mathworks.mvm.eventmgr.prompt;

   import java.util.EnumSet;








   public enum InputRequester
   {
     NO_PROMPT(-1),
     BASE_PROMPT(0),
     DEBUG_PROMPT(1),
     INPUT_PROMPT(2),
     KEYBOARD_PROMPT(3),
     PAUSE_PROMPT(4),
     BANG_PROMPT(5),
     SIMULINK_DEBUG_PROMPT(6),
     INCOMPLETE_BLOCK_PROMPT(7),
     MORE_PROMPT(8),
     EMERGENCY_EXIT_PROMPT(9),
     SIMEVENTS_DEBUG_PROMPT(10),
     SF_EML_DEBUG_PROMPT(11);




     private final int fCppValue;



     public int getNativeValue()
     {
       return this.fCppValue;
     }

     public static EnumSet<InputRequester> getDebugPrompts()
     {
       return EnumSet.of(DEBUG_PROMPT, KEYBOARD_PROMPT, SIMULINK_DEBUG_PROMPT, SIMEVENTS_DEBUG_PROMPT, SF_EML_DEBUG_PROMPT);
     }





     private InputRequester(int paramInt)
     {
       this.fCppValue = paramInt;
     }






     static InputRequester fromCppValue(int paramInt)
     {
       for (InputRequester localInputRequester : ) {
         if (localInputRequester.fCppValue == paramInt) {
           return localInputRequester;
         }
       }
       return null;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\eventmgr\prompt\InputRequester.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */