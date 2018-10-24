   package temp.com.mathworks.mvm.exec;








   public class MatlabConversionError
     extends Throwable
   {
     private final int fArgNum;






     private final boolean fWasExecuted;







     private MatlabConversionError(String paramString, int paramInt, boolean paramBoolean, Throwable paramThrowable)
     {
       super(paramString, paramThrowable);
       this.fArgNum = paramInt;
       this.fWasExecuted = ((paramInt < 0) || ((paramInt == 0) && (paramBoolean)));
     }




     public boolean wasExecuted()
     {
       return this.fWasExecuted;
     }



     public boolean isInputError()
     {
       return this.fArgNum > 0;
     }



     public boolean isOutputError()
     {
       return this.fArgNum < 0;
     }





     public int getArgNum()
     {
       return Math.abs(this.fArgNum);
     }



     public String toString()
     {
       String str1 = getClass().getName() + ": ";
       if (this.fArgNum != 0) {
         if (isInputError()) str1 = str1 + "argument "; else
           str1 = str1 + "return value ";
         str1 = str1 + Math.abs(this.fArgNum);
         str1 = str1 + ": ";
       }
       String str2 = getLocalizedMessage();
       if (str2 != null) str1 = str1 + str2;
       return str1 + (wasExecuted() ? " (was" : " (was not") + " executed)";
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\exec\MatlabConversionError.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */