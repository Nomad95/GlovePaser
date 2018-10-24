   package temp.com.mathworks.util;

   import java.awt.Component;
   import java.awt.Dimension;
   import java.awt.Graphics;
   import java.awt.Image;
   import java.awt.Rectangle;
   import java.io.PrintStream;




















   public class Offscreen
   {
     public static boolean sDebug = false;

     private static int sDepth;

     private static int sWidth;

     private static int sHeight;
     private static Image sImage;
     private static Graphics sGraphics;
     private Component fComponent;
     private Dimension fComponentSize;
     private int fRender;

     private static synchronized void releaseResources()
     {
       if (sGraphics != null)
       {
         sGraphics.dispose();
         sGraphics = null;
       }

       if (sImage != null)
       {
         sImage.flush();
         sImage = null;
       }
     }








     public static boolean isRendering()
     {
       return sDepth > 0;
     }







     public Offscreen(Component paramComponent)
     {
       this.fComponent = paramComponent;
     }








     public synchronized Graphics startRender(Graphics paramGraphics)
     {
       if (sDebug) {
         acquire();
       }
       sDepth += 1;

       if (sDepth > 1) {
         return paramGraphics;
       }


       this.fComponentSize = this.fComponent.getSize();
       if ((sImage == null) || (this.fComponentSize.width > sWidth) || (this.fComponentSize.height > sHeight))
       {
         releaseResources();

         sWidth = Math.max(this.fComponentSize.width, sWidth);
         sHeight = Math.max(this.fComponentSize.height, sHeight);

         if (sDebug) {
           System.out.println("resizing offscreen to " + sWidth + ", " + sHeight);
         }
         sImage = this.fComponent.createImage(sWidth, sHeight);
         if (sImage == null)
         {


           sDepth -= 1;
           if (sDebug)
             release();
           return null;
         }
         sGraphics = sImage.getGraphics();
       }

       Rectangle localRectangle = paramGraphics.getClipBounds();
       if (localRectangle != null) {
         sGraphics.setClip(localRectangle);

       }
       else
       {
         sGraphics.setClip(0, 0, sWidth, sHeight);
       }
       return sGraphics;
     }







     public synchronized void finishRender(Graphics paramGraphics)
     {
       sDepth -= 1;

       if (sDepth == 0)
       {

         Rectangle localRectangle = paramGraphics.getClipBounds();
         if (localRectangle == null) {
           localRectangle = new Rectangle(0, 0, this.fComponentSize.width, this.fComponentSize.height);
         }
         paramGraphics.drawImage(sImage, localRectangle.x, localRectangle.y, localRectangle.x + localRectangle.width, localRectangle.y + localRectangle.height, localRectangle.x, localRectangle.y, localRectangle.x + localRectangle.width, localRectangle.y + localRectangle.height, this.fComponent);
       }




       if (sDebug) {
         release();
       }
     }

     private void acquire() {
       if (this.fRender > 0)
         System.out.println("startRender re-entered " + this.fRender);
       this.fRender += 1;
     }

     private void release()
     {
       this.fRender -= 1;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\Offscreen.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */