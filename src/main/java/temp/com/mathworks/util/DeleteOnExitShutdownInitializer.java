   package temp.com.mathworks.util;

   import com.mathworks.util.event.GlobalEventListener;
   import com.mathworks.util.event.GlobalEventManager;
   import java.io.File;
   import java.lang.reflect.Field;
   import java.util.Collections;
   import java.util.Iterator;
   import java.util.Set;






   public final class DeleteOnExitShutdownInitializer
   {
     static
     {
       GlobalEventManager.addListener("shutdown", new GlobalEventListener()
       {
         public void actionPerformed(String paramAnonymousString) {}
       });
     }














     public static synchronized void filterDeleteOnExitList()
     {
       Set localSet = getDeleteOnExitList();


       Iterator localIterator = localSet.iterator();
       while (localIterator.hasNext()) {
         File localFile = new File((String)localIterator.next());

         if (!localFile.isAbsolute()) {
           localIterator.remove();
         }
       }
     }




     public static Set<String> getDeleteOnExitList()
     {
       try
       {
         Class localClass = Class.forName("java.io.DeleteOnExitHook");
         Field localField = localClass.getDeclaredField("files");
         localField.setAccessible(true);
         return (Set)localField.get(null);

       }
       catch (ClassNotFoundException|NoSuchFieldException|IllegalAccessException localClassNotFoundException)
       {
         if (!$assertionsDisabled) { throw new AssertionError("Error in 'DeleteOnExitShutdownListener.getDeleteOnExitList'.  Possible compatibility issue from using reflection.  Exception: " + localClassNotFoundException.toString());
         }
       }
       return Collections.emptySet();
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\DeleteOnExitShutdownInitializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */