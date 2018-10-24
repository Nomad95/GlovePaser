   package temp.com.mathworks.matlab.api.editor.actions;

   import com.mathworks.matlab.api.editor.Editor;














   public abstract interface SelectionDelegate
     extends Prioritizable
   {
     public abstract boolean wouldLikeToHandle(ActionType paramActionType, Editor paramEditor, String paramString);

     public abstract void handle(ActionType paramActionType, Editor paramEditor, String paramString);

     public static enum ActionType
     {
       OPEN,



       HELP,




       EVALUATE;

       private ActionType() {}
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\editor\actions\SelectionDelegate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */