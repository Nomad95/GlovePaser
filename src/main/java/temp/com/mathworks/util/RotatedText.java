   package temp.com.mathworks.util;

   import java.awt.Color;
   import java.awt.Component;
   import java.awt.FontMetrics;
   import java.awt.Graphics;
   import java.awt.Image;
   import java.awt.MediaTracker;
   import java.awt.Point;
   import java.awt.image.PixelGrabber;
   import java.util.Hashtable;















   public class RotatedText
   {
     private static Hashtable sCache = new Hashtable();


     private String fText;


     private int fAngle;


     private transient Point fOrigin;


     private transient Image fImage;


     public RotatedText(String paramString, int paramInt)
     {
       this.fText = paramString;
       this.fAngle = paramInt;
       this.fImage = null;
     }











     public void draw(Graphics paramGraphics, int paramInt1, int paramInt2, Component paramComponent)
     {
       if (this.fAngle == 0) {
         paramGraphics.drawString(this.fText, paramInt1, paramInt2);
       }
       else {
         if (this.fImage == null) {
           buildImage(paramGraphics, paramComponent);
         }
         if (this.fImage != null)
         {
           if (this.fOrigin == null)
           {
             FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
             int i = localFontMetrics.getAscent();
             int j = localFontMetrics.stringWidth(this.fText);
             int k = localFontMetrics.getHeight();

             calcOriginOffset(j, k, i);
           }

           paramGraphics.drawImage(this.fImage, paramInt1 + this.fOrigin.x, paramInt2 + this.fOrigin.y, paramComponent);
         }
       }
     }


     private void calcOriginOffset(int paramInt1, int paramInt2, int paramInt3)
     {
       switch (this.fAngle)
       {
       case 90:
         this.fOrigin = new Point(-paramInt3, -paramInt1);
         break;
       case 180:
         this.fOrigin = new Point(-paramInt1, paramInt3 - paramInt2);
         break;
       case 270:
         this.fOrigin = new Point(paramInt3 - paramInt2, 0);
         break;
       default:
         this.fOrigin = new Point(0, -paramInt3);
       }

     }






     public void setAngle(int paramInt)
     {
       if (paramInt != this.fAngle)
       {
         this.fAngle = paramInt;
         this.fImage = null;
         this.fOrigin = null;
       }
     }






     public void setText(String paramString)
     {
       if (!paramString.equals(this.fText))
       {
         this.fText = paramString;
         this.fImage = null;
         this.fOrigin = null;
       }
     }


     public void flushImage()
     {
       if (this.fImage != null) {
         this.fImage.flush();
       }
     }

     private void buildImage(Graphics paramGraphics, Component paramComponent)
     {
       FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
       int i = localFontMetrics.stringWidth(this.fText);
       int j = localFontMetrics.getHeight();
       int k = localFontMetrics.getAscent();



       int[] arrayOfInt = new int[i * j];


       boolean bool = false;


       int m = 0xFFFFFF & paramGraphics.getColor().getRGB();
       if (m == 16777215) {
         m = -16777216;
       } else {
         m = -1;
       }

       Image localImage = paramComponent.createImage(i, j);
       Graphics localGraphics = localImage.getGraphics();

       localGraphics.setColor(new Color(m));
       localGraphics.fillRect(0, 0, i, j);

       localGraphics.setFont(paramGraphics.getFont());
       localGraphics.setColor(paramGraphics.getColor());
       localGraphics.drawString(this.fText, 0, k);
       localGraphics.dispose();

       PixelGrabber localPixelGrabber = new PixelGrabber(localImage, 0, 0, i, j, arrayOfInt, 0, i);
       try
       {
         bool = localPixelGrabber.grabPixels();
       }
       catch (Exception localException1) {}



       if (bool)
       {
         localImage.flush();

         m &= 0xFFFFFF;
         for (int n = 0; n < arrayOfInt.length; n++)
         {
           if ((arrayOfInt[n] & 0xFFFFFF) == m) {
             arrayOfInt[n] = 0;
           }
         }
         this.fImage = ImageRotator.rotateImage(arrayOfInt, i, j, this.fAngle);
       }
       else {
         this.fImage = ImageRotator.rotateImage(localImage, this.fAngle);
       }
       MediaTracker localMediaTracker = new MediaTracker(paramComponent);
       localMediaTracker.addImage(this.fImage, 1);
       try
       {
         localMediaTracker.waitForID(1);
       }
       catch (Exception localException2) {}
     }
















     public static void drawRotatedText(Graphics paramGraphics, String paramString, int paramInt1, int paramInt2, int paramInt3, Component paramComponent)
     {
       String str = paramString + paramInt3;

       RotatedText localRotatedText;
       if (sCache.containsKey(str)) {
         localRotatedText = (RotatedText)sCache.get(str);
       }
       else {
         localRotatedText = new RotatedText(paramString, paramInt3);
         sCache.put(str, localRotatedText);
       }

       localRotatedText.draw(paramGraphics, paramInt1, paramInt2, paramComponent);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\RotatedText.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */