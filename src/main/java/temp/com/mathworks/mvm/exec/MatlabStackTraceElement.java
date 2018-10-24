   package temp.com.mathworks.mvm.exec;

   import java.io.Serializable;













   class MatlabStackTraceElement
     implements Serializable
   {
     final String fFileName;
     final String fFuncName;
     final int fLineNum;
     private static final long serialVersionUID = 1L;

     MatlabStackTraceElement(String paramString1, String paramString2, int paramInt)
     {
       this.fFileName = paramString1;
       this.fFuncName = paramString2;
       this.fLineNum = paramInt;
     }

     public String toString()
     {
       return "Error in ==> " + this.fFileName + ">" + this.fFuncName + " at " + this.fLineNum;
     }

     public int hashCode()
     {
       int i = 31 * this.fFileName.hashCode() + this.fFuncName.hashCode();
       return 31 * i + this.fLineNum;
     }






     public boolean equals(Object paramObject)
     {
       if (paramObject == this)
         return true;
       if (!(paramObject instanceof MatlabStackTraceElement))
         return false;
       MatlabStackTraceElement localMatlabStackTraceElement = (MatlabStackTraceElement)paramObject;
       return (equ(this.fFileName, localMatlabStackTraceElement.fFileName)) && (localMatlabStackTraceElement.fLineNum == this.fLineNum) && (equ(this.fFuncName, localMatlabStackTraceElement.fFuncName));
     }



     private static boolean equ(Object paramObject1, Object paramObject2)
     {
       return (paramObject1 == paramObject2) || ((paramObject1 != null) && (paramObject1.equals(paramObject2)));
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\exec\MatlabStackTraceElement.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */