   package temp.com.mathworks.util;




   public final class InitializationHelper
     implements InitializeBeforeAccess
   {
     private boolean fInitializationFinished = false;

     public void initializationFinished() {
       this.fInitializationFinished = true;
     }

     public void checkMutable() throws IllegalStateException {
       verifyInitialized(false, "Attempt to change state of after initialization has been completed.");
     }

     public void checkImmutable() throws IllegalStateException
     {
       verifyInitialized(true, "Attempt to access before initialization has been completed.");
     }

     private void verifyInitialized(boolean paramBoolean, String paramString) throws IllegalStateException {
       if (this.fInitializationFinished != paramBoolean) {
         Log.printLn(paramString);
         throw new IllegalStateException(paramString);
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\InitializationHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */