   package temp.com.mathworks.util;

   import java.awt.event.ComponentAdapter;
   import java.awt.event.ComponentEvent;















   public class MatlabWindowBlocker
     extends ComponentAdapter
   {
     private static int sDialogCount = 0;
     private int hWnd = 0;



     public void componentShown(ComponentEvent paramComponentEvent)
     {
       if (sDialogCount++ == 0)
       {
         FactoryUtils.disableMatlabWindows();
       }
     }

     public void componentHidden(ComponentEvent paramComponentEvent) {
       if (--sDialogCount == 0)
       {



         FactoryUtils.restoreMatlabWindows();
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\MatlabWindowBlocker.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */