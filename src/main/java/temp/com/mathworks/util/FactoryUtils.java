   package temp.com.mathworks.util;

   import java.awt.EventQueue;
   import java.lang.reflect.InvocationTargetException;












   public class FactoryUtils
   {
     private static FactoryUtilSupplier sDirSeed;
     private static FactoryUtilSupplier sRestoreMatlabWindows;
     private static FactoryUtilSupplier sDisableMatlabWindows;
     private static FactoryUtilSupplier sProcessPendingEvents;
     private static FactoryUtilSupplier sProcessExposeEvents;
     private static FactoryUtilSupplier sProcessSynchronousSystemEvents;
     private static FactoryUtilSupplier sIsMatlabThread;
     private static FactoryUtilSupplier sInvokeAndWait;
     private static String sMatlabStaticClasspath;

     public static String getDirectorySeed()
     {
       String str = null;






       if (sDirSeed != null)
       {
         str = (String)sDirSeed.getValue();
       }


       if (str == null) {
         str = System.getProperty("user.dir");
       }
       return str;
     }






     public static String getMatlabStaticClasspath()
     {
       String str = null;
       if (sMatlabStaticClasspath != null)
         str = sMatlabStaticClasspath;
       if (str == null)
         str = System.getProperty("java.class.path");
       return str;
     }



     public static void disableMatlabWindows()
     {
       if (sDisableMatlabWindows != null) {
         sDisableMatlabWindows.callVoidMethod(null);
       }
     }


     public static void restoreMatlabWindows()
     {
       if (sRestoreMatlabWindows != null) {
         sRestoreMatlabWindows.callVoidMethod(null);
       }
     }

     public static boolean processPendingEvents() {
       boolean bool = false;


       if (sProcessPendingEvents != null)
         bool = sProcessPendingEvents.callBooleanMethod(null);
       return bool;
     }

     public static boolean processExposeEvents()
     {
       boolean bool = false;


       if (sProcessExposeEvents != null)
         bool = sProcessExposeEvents.callBooleanMethod(null);
       return bool;
     }














     public static boolean processSynchronousSystemEvents()
       throws MatlabThreadException
     {
       if (sProcessSynchronousSystemEvents == null) {
         return false;
       }

       boolean bool = sProcessSynchronousSystemEvents.callBooleanMethod(null);
       if (!bool)
       {

         Object localObject = sProcessSynchronousSystemEvents.getValue();
         if (localObject != null) {
           assert ((localObject instanceof String));
           if ((localObject instanceof String)) {
             throw new MatlabThreadException((String)localObject);
           }
         }
       }
       return bool;
     }

     public static boolean isMatlabThread()
     {
       boolean bool = false;


       if (sIsMatlabThread != null)
         bool = sIsMatlabThread.callBooleanMethod(null);
       return bool;
     }










     public static void invokeAndWait(Runnable paramRunnable)
     {
       if (sInvokeAndWait != null) {
         sInvokeAndWait.callVoidMethod(paramRunnable);
       }
       else
       {
         try
         {

           EventQueue.invokeAndWait(paramRunnable);

         }
         catch (InterruptedException localInterruptedException)
         {

           if (!$assertionsDisabled) throw new AssertionError();
         } catch (InvocationTargetException localInvocationTargetException) {
           localInvocationTargetException.printStackTrace();
         }
       }
     }

     public static void setDirectorySeed(FactoryUtilSupplier paramFactoryUtilSupplier)
     {
       sDirSeed = paramFactoryUtilSupplier;
     }





     public static FactoryUtilSupplier getDirectorySeedSupplier()
     {
       return sDirSeed;
     }

     public static void setRestoreMatlabWindows(FactoryUtilSupplier paramFactoryUtilSupplier)
     {
       sRestoreMatlabWindows = paramFactoryUtilSupplier;
     }

     public static void setDisableMatlabWindows(FactoryUtilSupplier paramFactoryUtilSupplier)
     {
       sDisableMatlabWindows = paramFactoryUtilSupplier;
     }

     public static void setProcessPendingEvents(FactoryUtilSupplier paramFactoryUtilSupplier)
     {
       sProcessPendingEvents = paramFactoryUtilSupplier;
     }

     public static void setProcessExposeEvents(FactoryUtilSupplier paramFactoryUtilSupplier)
     {
       sProcessExposeEvents = paramFactoryUtilSupplier;
     }

     public static void setProcessSynchronousSystemEvents(FactoryUtilSupplier paramFactoryUtilSupplier)
     {
       sProcessSynchronousSystemEvents = paramFactoryUtilSupplier;
     }

     public static void setIsMatlabThread(FactoryUtilSupplier paramFactoryUtilSupplier)
     {
       sIsMatlabThread = paramFactoryUtilSupplier;
     }

     public static void setInvokeAndWait(FactoryUtilSupplier paramFactoryUtilSupplier)
     {
       sInvokeAndWait = paramFactoryUtilSupplier;
     }






     public static void setMatlabStaticClasspath(String paramString)
     {
       sMatlabStaticClasspath = paramString;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\FactoryUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */