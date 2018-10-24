   package temp.com.mathworks.util;

   import java.awt.Image;
   import java.awt.Toolkit;
   import java.awt.image.MemoryImageSource;


























   public class ImageRotator
   {
     public static Image rotateImage(Image paramImage, int paramInt)
     {
       Image localImage = paramImage;

       return localImage;
     }




     public static Image rotateImage(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
     {
       int m;



       int k;



       int[] arrayOfInt;


       int i;


       int j;


       if (paramInt3 == 180)
       {

         m = 0;
         int n = paramInt1 * paramInt2 - 1;

         while (m < n)
         {
           k = paramArrayOfInt[m];
           paramArrayOfInt[m] = paramArrayOfInt[n];
           paramArrayOfInt[n] = k;
           m++;
           n--;
         }

         arrayOfInt = paramArrayOfInt;
         i = paramInt1;
         j = paramInt2;
       }
       else if (paramInt3 == 90)
       {
         arrayOfInt = new int[paramInt2 * paramInt1];
         i = paramInt2;
         j = paramInt1;

         for (k = 0; k < paramInt2; k++) {
           for (m = 0; m < paramInt1; m++)
             arrayOfInt[((paramInt1 - m - 1) * paramInt2 + k)] = paramArrayOfInt[(k * paramInt1 + m)];
         }
       } else if (paramInt3 == 270)
       {
         arrayOfInt = new int[paramInt2 * paramInt1];
         i = paramInt2;
         j = paramInt1;

         for (k = 0; k < paramInt2; k++) {
           for (m = 0; m < paramInt1; m++)
             arrayOfInt[(m * paramInt2 + (paramInt2 - k - 1))] = paramArrayOfInt[(k * paramInt1 + m)];
         }
       } else if (paramInt3 == 0)
       {
         arrayOfInt = paramArrayOfInt;
         i = paramInt1;
         j = paramInt2;
       }
       else
       {
         arrayOfInt = paramArrayOfInt;
         i = paramInt1;
         j = paramInt2;
       }

       MemoryImageSource localMemoryImageSource = new MemoryImageSource(i, j, arrayOfInt, 0, i);
       Image localImage = Toolkit.getDefaultToolkit().createImage(localMemoryImageSource);

       return localImage;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\ImageRotator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */