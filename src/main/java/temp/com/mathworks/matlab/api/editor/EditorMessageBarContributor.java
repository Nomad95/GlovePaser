   package temp.com.mathworks.matlab.api.editor;

   import com.mathworks.matlab.api.menus.MenuBuilder;
   import java.awt.Paint;



































































   public abstract interface EditorMessageBarContributor
   {
     public abstract void configureModel(Editor paramEditor);

     public abstract Object getModel();

     public abstract void contributeToContextMenu(MenuBuilder paramMenuBuilder);

     public abstract boolean isPaintingSummary();

     public abstract Paint getSummaryPaint();

     public abstract String getSummaryText();

     public abstract Priority getPriority();

     public abstract boolean isValid();

     public static enum Priority
     {
       ALWAYS_ON,




       ALWAYS_ON_PROVIDING_SUMMARY,




       TRANSIENT;

       private Priority() {}
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\editor\EditorMessageBarContributor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */