   package temp.com.mathworks.util.jarloader;

   import java.io.IOException;
   import java.io.InputStream;
   import java.io.PrintWriter;





















   public class MessageHeader
   {
     private String[] keys;
     private String[] values;
     private int nkeys;

     public MessageHeader()
     {
       grow();
     }

     public MessageHeader(InputStream paramInputStream) throws IOException {
       parseHeader(paramInputStream);
     }



     public String findValue(String paramString)
     {
       int i;


       if (paramString == null) {
         i = this.nkeys; do { i--; if (i < 0) break;
         } while (this.keys[i] != null);
         return this.values[i];
       } else {
         i = this.nkeys; do { i--; if (i < 0) break;
         } while (!paramString.equalsIgnoreCase(this.keys[i]));
         return this.values[i];
       }
       return null;
     }

     public String getKey(int paramInt) {
       if ((paramInt < 0) || (paramInt >= this.nkeys)) return null;
       return this.keys[paramInt];
     }

     public String getValue(int paramInt) {
       if ((paramInt < 0) || (paramInt >= this.nkeys)) return null;
       return this.values[paramInt];
     }









     public String findNextValue(String paramString1, String paramString2)
     {
       int i = 0;
       int j; if (paramString1 == null) {
         j = this.nkeys; for (;;) { j--; if (j < 0) break;
           if (this.keys[j] == null) {
             if (i != 0)
               return this.values[j];
             if (this.values[j] == paramString2)
               i = 1;
           }
         } } else { j = this.nkeys; for (;;) { j--; if (j < 0) break;
           if (paramString1.equalsIgnoreCase(this.keys[j])) {
             if (i != 0)
               return this.values[j];
             if (this.values[j] == paramString2)
               i = 1; } } }
       return null;
     }



     public void print(PrintWriter paramPrintWriter)
     {
       for (int i = 0; i < this.nkeys; i++) {
         if (this.keys[i] != null)
           paramPrintWriter.print(this.keys[i] + (this.values[i] != null ? ": " + this.values[i] : "") + "\r\n");
       }
       paramPrintWriter.print("\r\n");
       paramPrintWriter.flush();
     }


     public void add(String paramString1, String paramString2)
     {
       grow();
       this.keys[this.nkeys] = paramString1;
       this.values[this.nkeys] = paramString2;
       this.nkeys += 1;
     }


     public void prepend(String paramString1, String paramString2)
     {
       grow();
       for (int i = this.nkeys; i > 0; i--) {
         this.keys[i] = this.keys[(i - 1)];
         this.values[i] = this.values[(i - 1)];
       }
       this.keys[0] = paramString1;
       this.values[0] = paramString2;
       this.nkeys += 1;
     }





     public void set(int paramInt, String paramString1, String paramString2)
     {
       grow();
       if (paramInt < 0)
         return;
       if (paramInt > this.nkeys) {
         add(paramString1, paramString2);
       } else {
         this.keys[paramInt] = paramString1;
         this.values[paramInt] = paramString2;
       }
     }



     private void grow()
     {
       if ((this.keys == null) || (this.nkeys >= this.keys.length)) {
         String[] arrayOfString1 = new String[this.nkeys + 4];
         String[] arrayOfString2 = new String[this.nkeys + 4];
         if (this.keys != null)
           System.arraycopy(this.keys, 0, arrayOfString1, 0, this.nkeys);
         if (this.values != null)
           System.arraycopy(this.values, 0, arrayOfString2, 0, this.nkeys);
         this.keys = arrayOfString1;
         this.values = arrayOfString2;
       }
     }




     public void set(String paramString1, String paramString2)
     {
       int i = this.nkeys; do { i--; if (i < 0) break;
       } while (!paramString1.equalsIgnoreCase(this.keys[i]));
       this.values[i] = paramString2;
       return;

       add(paramString1, paramString2);
     }


     public static String canonicalID(String paramString)
     {
       if (paramString == null)
         return "";
       int i = 0;
       int j = paramString.length();
       int k = 0;
       int m;
       while ((i < j) && (((m = paramString.charAt(i)) == '<') || (m <= 32)))
       {
         i++;
         k = 1;
       }
       while ((i < j) && (((m = paramString.charAt(j - 1)) == '>') || (m <= 32)))
       {
         j--;
         k = 1;
       }
       return k != 0 ? paramString.substring(i, j) : paramString;
     }

     public void parseHeader(InputStream paramInputStream) throws IOException
     {
       this.nkeys = 0;
       if (paramInputStream == null)
         return;
       Object localObject1 = new char[10];
       int i = paramInputStream.read();
       while ((i != 10) && (i != 13) && (i >= 0)) {
         int j = 0;
         int k = -1;

         int n = i > 32 ? 1 : 0;
         localObject1[(j++)] = ((char)i);
         int m;
         Object localObject2; while ((m = paramInputStream.read()) >= 0)
           switch (m) {
           case 58:
             if ((n != 0) && (j > 0))
               k = j;
             n = 0;
             break;
           case 9:
             m = 32;
           case 32:
             n = 0;
             break;
           case 10:
           case 13:
             i = paramInputStream.read();
             if ((m == 13) && (i == 10)) {
               i = paramInputStream.read();
               if (i == 13)
                 i = paramInputStream.read();
             }
             if ((i == 10) || (i == 13)) break label256; if (i <= 32) {}
             break;


           default:
             if (j >= localObject1.length) {
               localObject2 = new char[localObject1.length * 2];
               System.arraycopy(localObject1, 0, localObject2, 0, j);
               localObject1 = localObject2;
             }
             localObject1[(j++)] = ((char)m);
           }
         i = -1;
         label256:
         while ((j > 0) && (localObject1[(j - 1)] <= ' ')) {
           j--;
         }
         if (k <= 0) {
           localObject2 = null;
           k = 0;
         } else {
           localObject2 = String.copyValueOf((char[])localObject1, 0, k);
           if ((k < j) && (localObject1[k] == ':'))
             k++;
           while ((k < j) && (localObject1[k] <= ' '))
             k++;
         }
         String str;
         if (k >= j) {
           str = new String();
         } else
           str = String.copyValueOf((char[])localObject1, k, j - k);
         add((String)localObject2, str);
       }
     }

     public String toString() {
       String str = super.toString();
       for (int i = 0; i < this.keys.length; i++) {
         str = str + "{" + this.keys[i] + ": " + this.values[i] + "}";
       }
       return str;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\jarloader\MessageHeader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */