   package temp.com.mathworks.mvm.eventmgr;

   import javax.swing.SwingUtilities;










   public class SwingMvmListener<V extends MvmEvent>
     implements MvmListener<V>
   {
     private final MvmListener<V> fListener;

     public SwingMvmListener(MvmListener<V> paramMvmListener)
     {
       this.fListener = paramMvmListener;
     }





     public void mvmChanged(final V paramV)
     {
       if (this.fListener != null) {
         SwingUtilities.invokeLater(new Runnable()
         {
           public void run()
           {
             SwingMvmListener.this.fListener.mvmChanged(paramV);
           }
         });
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\eventmgr\SwingMvmListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */