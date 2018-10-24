   package temp.com.mathworks.util;

   import java.util.Locale;
   import java.util.regex.Matcher;
   import java.util.regex.Pattern;



   public final class LanguageUtils
   {
     private static final boolean IS_JAPANESE = Locale.getDefault().getLanguage().equals(Locale.JAPANESE.getLanguage());

     private static final boolean IS_KOREAN = Locale.getDefault().getLanguage().equals(Locale.KOREAN.getLanguage());

     private static final boolean IS_ENGLISH = Locale.getDefault().getLanguage().equals(Locale.ENGLISH.getLanguage());






     public static boolean isJapanese()
     {
       return IS_JAPANESE;
     }





     public static boolean isEnglish()
     {
       return IS_ENGLISH;
     }





     public static boolean isKorean()
     {
       return IS_KOREAN;
     }




     public static boolean isCJK()
     {
       return (isJapanese()) || (isKorean()) || (Locale.getDefault().getLanguage().equals(Locale.CHINESE.getLanguage()));
     }











     public static Locale createLocaleForLangLocaleString(String paramString)
     {
       if ((paramString == null) || (paramString.isEmpty())) {
         return null;
       }



       Pattern localPattern = Pattern.compile("(^.*)[_](.*$)");
       Matcher localMatcher = localPattern.matcher(paramString);
       if (localMatcher.matches()) {
         return new Locale(localMatcher.group(1), localMatcher.group(2));
       }


       return new Locale(paramString);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\LanguageUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */