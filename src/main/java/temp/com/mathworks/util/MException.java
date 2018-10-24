   package temp.com.mathworks.util;

   import java.text.MessageFormat;
   import java.util.Arrays;
   import java.util.ResourceBundle;
   import org.jetbrains.annotations.Nullable;















   public class MException
     extends RuntimeException
     implements MatlabIdentified
   {
     private final String fMessageID;
     private final String fKey;
     private final String fResourceBaseName;
     private final Object[] fArgs;

     public MException(String paramString1, String paramString2)
     {
       this(paramString1, paramString2, null);
     }









     public MException(String paramString1, String paramString2, @Nullable Throwable paramThrowable)
     {
       this(paramString1, true, paramThrowable, null, paramString2, new Object[0]);
     }









     public MException(String paramString1, String paramString2, String paramString3, Object... paramVarArgs)
     {
       this(paramString1, null, paramString2, paramString3, paramVarArgs);
     }









     public MException(String paramString1, @Nullable Throwable paramThrowable, String paramString2, String paramString3, Object... paramVarArgs)
     {
       this(paramString1, false, paramThrowable, paramString2, paramString3, paramVarArgs);
     }











     private MException(String paramString1, boolean paramBoolean, @Nullable Throwable paramThrowable, @Nullable String paramString2, String paramString3, Object... paramVarArgs)
     {
       super(paramString2 == null ? paramString3 : getLocalizedMessage(paramString2, paramString3, paramVarArgs), paramThrowable);
       if ((!paramBoolean) && (paramString2 == null))
         throw new NullPointerException("Resource must not be null");
       if (paramString3 == null) {
         throw new IllegalArgumentException((paramString2 == null ? "Message" : "Key") + " must not be null");
       }
       assert ((!paramBoolean) || (paramString2 != null) || (paramVarArgs.length == 0));
       this.fMessageID = paramString1;
       this.fKey = paramString3;
       this.fResourceBaseName = paramString2;
       this.fArgs = Arrays.copyOf(paramVarArgs, paramVarArgs.length);
     }

     public String getMessageID() {
       return this.fMessageID;
     }

     public String getLocalizedMessage() {
       if (this.fResourceBaseName == null) return super.getLocalizedMessage();
       return getLocalizedMessage(this.fResourceBaseName, this.fKey, this.fArgs);
     }

     private static String getLocalizedMessage(String paramString1, String paramString2, Object... paramVarArgs) {
       ResourceBundle localResourceBundle = ResourceBundle.getBundle(paramString1);
       String str = localResourceBundle.getString(paramString2);
       if (paramVarArgs.length != 0) {
         str = MessageFormat.format(str, paramVarArgs);
       }
       return str;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\MException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */