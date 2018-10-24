   package temp.com.mathworks.util;

   import java.util.ArrayList;
   import java.util.List;
   import java.util.StringTokenizer;
   import java.util.regex.Matcher;
   import java.util.regex.Pattern;


















   public final class StringUtils
   {
     public static final String LINE_SEPARATOR = System.getProperty("line.separator");











     public static String stripChar(String paramString, char paramChar)
     {
       int i = paramString.length();
       StringBuilder localStringBuilder = new StringBuilder(i);
       for (int j = 0; j < i; j++) {
         char c = paramString.charAt(j);
         if (c != paramChar) {
           localStringBuilder.append(c);
         }
       }
       return localStringBuilder.toString();
     }






     public static String stripChars(String paramString1, String paramString2)
     {
       StringBuilder localStringBuilder = new StringBuilder(paramString1.length());
       for (int i = 0; i < paramString1.length(); i++)
       {
         char c = paramString1.charAt(i);
         if (paramString2.indexOf(c) == -1)
           localStringBuilder.append(c);
       }
       return localStringBuilder.toString();
     }






















     public static String insertLineBreaks(String paramString, int paramInt)
     {
       if (paramInt <= 0)
         throw new IllegalArgumentException("'maxCharsPerLine' must be >= 1");
       if (paramString == null) {
         return null;
       }


       StringBuilder localStringBuilder = new StringBuilder();
       while (paramString.length() > paramInt)
       {


         int i = 0;
         for (int j = paramInt; j >= 0; j--)
         {
           if (Character.isWhitespace(paramString.charAt(j)))
           {
             localStringBuilder.append(paramString.substring(0, j));
             localStringBuilder.append(LINE_SEPARATOR);



             paramString = paramString.substring(j + 1);
             i = 1;
             break;
           }
         }


         if (i == 0)
         {
           localStringBuilder.append(paramString.substring(0, paramInt));
           localStringBuilder.append(LINE_SEPARATOR);

           paramString = paramString.substring(paramInt);
         }
       }


       localStringBuilder.append(paramString);

       return localStringBuilder.toString();
     }








     public static String quoteSingleQuotes(String paramString)
     {
       if (paramString == null) {
         return null;
       }
       int i = paramString.indexOf('\'', 0);
       if (i != -1)
       {
         StringBuilder localStringBuilder = new StringBuilder(paramString);
         int j = 0;

         while (i != -1)
         {
           localStringBuilder.insert(i + j, '\'');
           j++;
           i = paramString.indexOf('\'', i + 1);
         }


         paramString = localStringBuilder.toString();
       }

       return paramString;
     }






























     public static int indexOf(String paramString1, String paramString2, int paramInt, boolean paramBoolean)
     {
       if (paramBoolean) {
         return paramString1.indexOf(paramString2, paramInt);
       }


       int i = paramString2.length();
       if (paramInt >= paramString1.length())
       {
         if ((paramString1.length() == 0) && (paramInt == 0) && (i == 0)) {
           return 0;
         }
         return -1;
       }
       if (paramInt < 0)
       {
         paramInt = 0; }
       if (i == 0) {
         return paramInt;
       }
       char c1 = Character.toUpperCase(paramString2.charAt(0));
       int j = Character.toLowerCase(c1);
       int k = paramString1.length() - i;
       int m = paramInt;







       while (m <= k)
       {
         c2 = paramString1.charAt(m);
         if ((c2 == j) || (c2 == c1))
           break;
         m++;
       }
       if (m > k) {
         return -1;
       }

       char c2 = '\001';
       int n = m + 1;
       int i1 = n + i - 1;
       for (;;) { if (n >= i1)
           break label239;
         char c3 = paramString1.charAt(n++);
         char c4 = paramString2.charAt(c2++);
         if (c3 != c4)
         {
           c3 = Character.toUpperCase(c3);
           c4 = Character.toLowerCase(c4);
           if (c3 != c4)
           {
             c3 = Character.toLowerCase(c3);
             c4 = Character.toLowerCase(c4);
             if (c3 != c4)
             {
               m++;

               break;
             }
           }
         }
       }
       label239:
       return m;
     }































     public static int lastIndexOf(String paramString1, String paramString2, int paramInt, boolean paramBoolean)
     {
       if (paramBoolean) {
         return paramString1.lastIndexOf(paramString2, paramInt);
       }

       int i = paramString2.length();
       if (paramInt < 0)
         return -1;
       int j = paramString1.length() - i;
       if (paramInt > j)
       {
         paramInt = j; }
       if (i == 0) {
         return paramInt;
       }
       int k = i - 1;
       int m = k + paramInt;
       char c1 = Character.toUpperCase(paramString2.charAt(k));
       int n = Character.toLowerCase(c1);






       while (m >= k)
       {
         char c2 = paramString1.charAt(m);
         if ((c2 == n) || (c2 == c1))
           break;
         m--;
       }
       if (m < k) {
         return -1;
       }

       int i1 = m - 1;
       int i2 = k - 1;
       int i3 = i1 - k;
       for (;;) {
         if (i1 <= i3)
           break label231;
         char c3 = paramString1.charAt(i1--);
         char c4 = paramString2.charAt(i2--);
         if (c3 != c4)
         {
           c3 = Character.toUpperCase(c3);
           c4 = Character.toUpperCase(c4);
           if (c3 != c4)
           {
             c3 = Character.toLowerCase(c3);
             c4 = Character.toLowerCase(c4);
             if (c3 != c4)
             {
               m--;

               break;
             }
           }
         }
       }
       label231:
       return i3 + 1;
     }

























     public static boolean startsWith(String paramString1, String paramString2, int paramInt, boolean paramBoolean)
     {
       if (paramBoolean) {
         return paramString1.startsWith(paramString2, paramInt);
       }


       if ((paramInt < 0) || (paramInt > paramString1.length() - paramString2.length())) {
         return false;
       }
       int i = paramInt;
       int j = 0;
       int k = paramString2.length();
       while (j < k)
       {
         char c1 = paramString1.charAt(i++);
         char c2 = paramString2.charAt(j++);
         if (c1 != c2)
         {
           c1 = Character.toUpperCase(c1);
           c2 = Character.toUpperCase(c2);
           if (c1 != c2)
           {
             c1 = Character.toLowerCase(c1);
             c2 = Character.toLowerCase(c2);
             if (c1 != c2) {
               return false;
             }
           }
         }
       }
       return true;
     }

















     public static boolean endsWith(String paramString1, String paramString2, boolean paramBoolean)
     {
       if (paramBoolean) {
         return paramString1.endsWith(paramString2);
       }
       return startsWith(paramString1, paramString2, paramString1.length() - paramString2.length(), paramBoolean);
     }












     public static boolean isWholeWord(String paramString1, String paramString2)
     {
       return isWholeWord(paramString1, paramString2, true);
     }











     public static boolean isWholeWord(String paramString1, String paramString2, boolean paramBoolean)
     {
       boolean bool = false;
       int i = indexOf(paramString1, paramString2, 0, false);
       int j = paramString2.length();
       int k = paramString1.length();
       int m = lastIndexOf(paramString1, paramString2, k, false);


       while ((!bool) && (i > -1) && (i <= m))
       {

         if (!paramBoolean)
         {
           if (((i > 0) && ((Character.isLetterOrDigit(paramString1.charAt(i - 1))) || (paramString1.charAt(i - 1) == '_') || (paramString1.charAt(i - 1) == '.'))) || ((i + j < k) && ((Character.isLetterOrDigit(paramString1.charAt(i + j))) || (paramString1.charAt(i + j) == '_') || (paramString1.charAt(i + j) == '.'))))
           {




             if (((i > 0) && (paramString1.charAt(i - 1) == '.')) || ((i + j < k) && (paramString1.charAt(i + j) == '.')))
             {
               int n = indexOf(paramString1, ".", 0, false);
               if (n + 1 < k)
               {
                 bool = Character.isWhitespace(paramString1.charAt(n + 1));
               }
               else
               {
                 bool = (i - 1 <= 0) || (Character.isWhitespace(paramString1.charAt(i - 1)));
               }

             }
             else
             {
               bool = false;
             }


           }
           else {
             bool = true;

           }


         }
         else
         {
           bool = ((i <= 0) || ((!Character.isLetterOrDigit(paramString1.charAt(i - 1))) && (paramString1.charAt(i - 1) != '_'))) && ((i + j >= k) || ((!Character.isLetterOrDigit(paramString1.charAt(i + j))) && (paramString1.charAt(i + j) != '_')));
         }

         i = indexOf(paramString1, paramString2, i + j, false);
       }
       return bool;
     }

















     public static String repeat(String paramString, int paramInt)
     {
       if (paramString == null) {
         return null;
       }
       StringBuffer localStringBuffer = new StringBuffer();
       for (int i = 0; i < paramInt; i++) {
         localStringBuffer = localStringBuffer.append(paramString);
       }
       return localStringBuffer.toString();
     }













     public static String replaceAllStrings(String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
     {
       if ((paramString1 == null) || (paramString2 == null) || (paramString2.length() == 0) || (paramString3 == null)) {
         throw new IllegalArgumentException("null or empty String");
       }
       StringBuffer localStringBuffer = new StringBuffer();
       int i = 0;
       int j = indexOf(paramString1, paramString2, i, paramBoolean2);

       while (j >= 0)
       {

         String str = getStringToCheck(j, paramString1, paramString2.length());

         boolean bool = shouldModify(paramString1, str, paramString2, j, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4);

         if (bool)
         {


           localStringBuffer.append(paramString1.substring(i, j));
           localStringBuffer.append(paramString3);

         }
         else
         {
           localStringBuffer.append(paramString1.substring(i, j + paramString2.length()));
         }


         j += paramString2.length();
         i = j;

         j = indexOf(paramString1, paramString2, i, paramBoolean2);
       }


       if (i == 0)
       {

         return paramString1;
       }



       localStringBuffer.append(paramString1.substring(i));

       return new String(localStringBuffer);
     }

















     public static String replaceAllStrings(String paramString1, String paramString2, String paramString3)
     {
       return replaceAllStrings(paramString1, paramString2, paramString3, false, false);
     }











     public static String replaceAllStrings(String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2)
     {
       return replaceAllStrings(paramString1, paramString2, paramString3, paramBoolean1, paramBoolean2, true, false);
     }














     public static String surroundText(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
     {
       if ((paramString1 == null) || (paramString2 == null) || (paramString2.length() == 0) || (paramString3 == null) || (paramString4 == null)) {
         throw new IllegalArgumentException("null or empty String");
       }
       StringBuffer localStringBuffer = new StringBuffer();
       int i = 0;
       int j = indexOf(paramString1, paramString2, i, paramBoolean2);


       while (j >= 0)
       {
         String str = getStringToCheck(j, paramString1, paramString2.length());

         boolean bool = shouldModify(paramString1, str, paramString2, j, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4);
         if (bool)
         {


           localStringBuffer.append(paramString1.substring(i, j));
           localStringBuffer.append(paramString3);
           localStringBuffer.append(paramString1.substring(j, paramString2.length() + j));
           localStringBuffer.append(paramString4);

         }
         else
         {
           localStringBuffer.append(paramString1.substring(i, j + paramString2.length()));
         }



         j += paramString2.length();
         i = j;

         j = indexOf(paramString1, paramString2, i, paramBoolean2);
       }


       if (i == 0)
       {

         return paramString1;
       }


       localStringBuffer.append(paramString1.substring(i));


       return new String(localStringBuffer);
     }






     private static String getStringToCheck(int paramInt1, String paramString, int paramInt2)
     {
       String str;




       if (paramInt1 + paramInt2 + 2 <= paramString.length())
       {
         if (paramInt1 != 0) {
           str = paramString.substring(paramInt1 - 1, paramInt1 + paramInt2 + 2);
         } else {
           str = paramString.substring(paramInt1, paramInt1 + paramInt2 + 2);
         }
       } else if (paramInt1 + paramInt2 + 1 <= paramString.length())
       {
         if (paramInt1 != 0) {
           str = paramString.substring(paramInt1 - 1, paramInt1 + paramInt2 + 1);
         } else {
           str = paramString.substring(paramInt1, paramInt1 + paramInt2 + 1);

         }


       }
       else if (paramInt1 != 0) {
         str = paramString.substring(paramInt1 - 1, paramInt1 + paramInt2);
       } else {
         str = paramString.substring(paramInt1, paramInt1 + paramInt2);
       }
       return str;
     }






     private static boolean shouldModify(String paramString1, String paramString2, String paramString3, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
     {
       boolean bool = false;

       if ((!paramBoolean1) || (isWholeWord(paramString2, paramString3, paramBoolean3)))
       {

         if ((!paramBoolean2) || (paramString1.substring(paramInt, paramInt + paramString3.length()).equals(paramString3)))
         {
           if (paramBoolean4)
           {
             int i = paramString1.indexOf('<', paramInt);
             int j = paramString1.indexOf('>', paramInt);
             if ((j != -1) && (i == -1))
             {

               bool = false;
             }
             else if ((j == -1) || (j > i))
             {

               bool = true;
             }
           }
           else
           {
             bool = true;
           }
         }
       }
       return bool;
     }











     public static int[] findPattern(String paramString, int paramInt, CharSequence paramCharSequence)
     {
       if (paramString == null) throw new IllegalArgumentException("Input pattern must not be null.");
       if (paramCharSequence == null) throw new IllegalArgumentException("Input sequence must not be null.");
       if ((paramInt < 0) || (paramInt > paramCharSequence.length())) {
         throw new IllegalArgumentException("Begin position must be inside the character sequence");
       }
       int i = -1;
       int j = -1;

       Pattern localPattern = Pattern.compile(paramString);
       Matcher localMatcher = localPattern.matcher(paramCharSequence);

       boolean bool = localMatcher.find(paramInt);
       if (bool) {
         i = localMatcher.start();
         j = localMatcher.end();
       }
       return new int[] { i, j };
     }









     public static String[] stringToArray(String paramString1, String paramString2)
     {
       StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, paramString2);
       String[] arrayOfString = new String[localStringTokenizer.countTokens()];
       int i = 0;
       while (localStringTokenizer.hasMoreTokens())
       {
         arrayOfString[(i++)] = localStringTokenizer.nextToken().trim();
       }
       return arrayOfString;
     }


















     public static String arrayToString(String[] paramArrayOfString, String paramString)
     {
       if (paramArrayOfString == null) return null;
       if (paramArrayOfString.length < 1) return "";
       String str = paramArrayOfString[0];
       for (int i = 1; i < paramArrayOfString.length; i++)
       {
         str = str.concat(paramString + paramArrayOfString[i]);
       }
       return str;
     }












     public static String convertToHTML(String paramString, boolean paramBoolean)
     {
       return convertToHTML(paramString, paramBoolean, true);
     }












     public static String convertToHTML(String paramString, boolean paramBoolean1, boolean paramBoolean2)
     {
       if (paramString.indexOf('\n') == -1) {
         return paramString;
       }

       StringTokenizer localStringTokenizer = new StringTokenizer(paramBoolean2 ? paramString : escapeASCIIEntitiesForHTML(paramString), "\n");
       String str = "";

       if (paramBoolean1) {
         str = str + "&nbsp ";
       }
       if (localStringTokenizer.hasMoreElements()) {
         str = str + localStringTokenizer.nextToken();
       }

       while (localStringTokenizer.hasMoreElements()) {
         if (paramBoolean1) {
           str = str + "&nbsp <br>&nbsp ";
         }
         else {
           str = str + "<br> ";
         }
         str = str + localStringTokenizer.nextToken();
       }
       str = "<html>" + str + "</html>";
       return str;
     }






     public static String fileNamePatternToRegex(String paramString)
     {
       StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "*");
       StringBuilder localStringBuilder = new StringBuilder();
       if (paramString.startsWith("*")) {
         localStringBuilder.append(".*");
       }
       while (localStringTokenizer.hasMoreTokens())
       {
         localStringBuilder.append("\\Q");
         String str = localStringTokenizer.nextToken();
         for (int i = 0; i < str.length(); i++)
         {
           if (str.charAt(i) == '\\') {
             localStringBuilder.append("\\\\");
           } else
             localStringBuilder.append(str.charAt(i));
         }
         localStringBuilder.append("\\E");
         if ((localStringTokenizer.hasMoreTokens()) || (paramString.endsWith("*"))) {
           localStringBuilder.append(".*");
         }
       }
       return localStringBuilder.toString();
     }
















     private static String escapeASCIIEntitiesForHTML(String paramString)
     {
       if (!hasSpecialChars(paramString)) {
         return paramString;
       }
       StringBuilder localStringBuilder = new StringBuilder(paramString.length());
       for (int i = 0; i < paramString.length(); i++) {
         char c = paramString.charAt(i);
         switch (c) {
         case '<':  localStringBuilder.append("&lt;"); break;
         case '>':  localStringBuilder.append("&gt;"); break;
         case '"':  localStringBuilder.append("&quot;"); break;
         case '&':  localStringBuilder.append("&amp;"); break;
         case '/':  localStringBuilder.append("&frasl;"); break;
         default:  localStringBuilder.append(c);
         }
       }
       return localStringBuilder.toString();
     }

     private static boolean hasSpecialChars(String paramString) {
       boolean bool = false;
       if ((paramString != null) && (paramString.length() > 0)) {
         for (int i = 0; i < paramString.length(); i++) {
           int j = paramString.charAt(i);
           switch (j) {
           case 60:  bool = true; break;
           case 62:  bool = true; break;
           case 34:  bool = true; break;
           case 38:  bool = true; break;
           case 47:  bool = true;
           }
         }
       }
       return bool;
     }





     public static boolean isWhitespace(String paramString)
     {
       char[] arrayOfChar = paramString.toCharArray();
       boolean bool = true;
       int i = 0;
       while ((i < arrayOfChar.length) && (bool)) {
         if (Character.isWhitespace(arrayOfChar[i])) {
           i++;
         } else
           bool = false;
       }
       return bool;
     }






     public static String escapeRegex(String paramString)
     {
       String[] arrayOfString1 = { "\\", "/", ".", "*", "+", "?", "|", "(", ")", "[", "]", "{", "}" };

       for (String str : arrayOfString1)
       {
         paramString = paramString.replace(str, '\\' + str);
       }
       return paramString;
     }







     public static List<String> split(String paramString1, String paramString2)
     {
       ArrayList localArrayList = new ArrayList();
       for (String str : paramString1.split(paramString2)) {
         str = str.trim();
         if (str.length() != 0)
           localArrayList.add(str);
       }
       return localArrayList;
     }



     public static boolean isEmptyString(Object paramObject)
     {
       return (paramObject == null) || (paramObject.toString().trim().length() == 0);
     }



     public static boolean isEmpty(String paramString)
     {
       return (paramString == null) || (paramString.trim().length() == 0);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\StringUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */