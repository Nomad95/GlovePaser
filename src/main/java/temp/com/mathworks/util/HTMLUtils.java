   package temp.com.mathworks.util;

   import java.awt.Color;
   import java.util.List;
   import java.util.Vector;
   import java.util.regex.Matcher;
   import java.util.regex.Pattern;













   public class HTMLUtils
   {
     public static String convertColorToHex(Color paramColor)
     {
       int i = paramColor.getRed();
       int j = paramColor.getGreen();
       int k = paramColor.getBlue();

       return String.format("#%02X%02X%02X", new Object[] { Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k) });
     }








     public static List<Integer> findMatchingInText(String paramString1, String paramString2)
     {
       Vector localVector = new Vector();

       Pattern localPattern = Pattern.compile("((?!>))" + StringUtils.escapeRegex(paramString2) + "(?=[^<>]*(?:<|$))");

       Matcher localMatcher = localPattern.matcher(paramString1);
       while (localMatcher.find()) {
         localVector.add(Integer.valueOf(localMatcher.start()));
       }
       return localVector;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\HTMLUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */