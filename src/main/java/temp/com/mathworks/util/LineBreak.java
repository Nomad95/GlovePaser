   package temp.com.mathworks.util;










   public enum LineBreak
   {
     WINDOWS("\r\n"),




     UNIX("\n"),





     MAC("\r");




     private String fLineBreakString;



     private LineBreak(String paramString)
     {
       this.fLineBreakString = paramString;
     }




     public String getLineBreakString()
     {
       return this.fLineBreakString;
     }






     public static LineBreak getPlatformPreferred()
     {
       return lookup(System.getProperty("line.separator"));
     }








     public static LineBreak lookup(String paramString)
     {
       Object localObject = null;



       for (LineBreak localLineBreak : values()) {
         if (localLineBreak.getLineBreakString().equals(paramString)) {
           localObject = localLineBreak;
           break;
         }
       }

       return (LineBreak)localObject;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\LineBreak.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */