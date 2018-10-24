   package temp.com.mathworks.util;

   import java.awt.Component;
   import java.awt.Image;
   import java.awt.MediaTracker;
   import java.awt.Toolkit;
   import java.awt.image.ImageConsumer;
   import java.awt.image.ImageObserver;
   import java.awt.image.ImageProducer;
   import java.net.URL;
   import sun.awt.image.ByteArrayImageSource;

























   public class ResLoader
   {
     private Class fOwnerClass;
     private Component fOwner;
     private MediaTracker fTracker;
     private int fNewID;

     public ResLoader(Component paramComponent)
     {
       this.fOwner = paramComponent;
       if (paramComponent != null) {
         this.fOwnerClass = this.fOwner.getClass();
       } else
         this.fOwnerClass = Component.class;
       this.fNewID = 0;
       this.fTracker = null;
     }











     public ResLoader(Class paramClass)
     {
       this.fOwner = null;
       if (paramClass != null) {
         this.fOwnerClass = paramClass;
       } else
         this.fOwnerClass = Object.class;
       this.fNewID = 0;
       this.fTracker = null;
     }







     public Image loadImage(String paramString)
     {
       Image localImage = null;

       try
       {
         URL localURL = this.fOwnerClass.getResource(paramString);
         if (localURL != null) {
           localImage = loadImage(localURL);
         }
       }
       catch (Exception localException) {}


       return localImage;
     }







     public Image loadImage(URL paramURL)
     {
       Image localImage = null;
       int i = 0;
       if (paramURL != null)
       {
         try
         {
           Toolkit localToolkit = Toolkit.getDefaultToolkit();
           Object localObject = (ImageProducer)paramURL.getContent();
           if ((localObject instanceof ByteArrayImageSource))
           {


             byte[] arrayOfByte = NativeJava.getImageData(localObject);
             if (arrayOfByte != null)
             {
               if ((arrayOfByte.length == 1) && (arrayOfByte[0] == -1)) {
                 i = 1;
               } else
                 localObject = new BypassImageFetcherSource(arrayOfByte);
             }
           }
           if (i != 0)
           {
             localImage = localToolkit.createImage((ImageProducer)localObject);
             addImage(localImage);


             if ((localObject instanceof BypassImageFetcherSource)) {
               localToolkit.prepareImage(localImage, -1, -1, (ImageObserver)localObject);
             }
           }
           else {
             localImage = Toolkit.getDefaultToolkit().getImage(paramURL);
           }
         }
         catch (Exception localException)
         {
           localImage = Toolkit.getDefaultToolkit().getImage(paramURL);
         }
       }

       return localImage;
     }









     public Image loadImage(ImageProducer paramImageProducer)
     {
       Image localImage = null;

       try
       {
         Toolkit localToolkit = Toolkit.getDefaultToolkit();
         localImage = localToolkit.createImage(paramImageProducer);

         addImage(localImage);


         localToolkit.prepareImage(localImage, -1, -1, (ImageObserver)paramImageProducer);
       }
       catch (Exception localException) {}



       return localImage;
     }

     private class BypassImageFetcherSource extends ByteArrayImageSource implements ImageObserver
     {
       public BypassImageFetcherSource(byte[] paramArrayOfByte)
       {
         super();
       }

       public boolean imageUpdate(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
       {
         return true;
       }

       public void startProduction(ImageConsumer paramImageConsumer)
       {
         addConsumer(paramImageConsumer);
         doFetch();
       }
     }






     public void addImage(Image paramImage)
     {
       if (this.fOwner != null)
       {

         if (this.fTracker == null)
         {
           this.fNewID = 0;
           this.fTracker = new MediaTracker(this.fOwner);
         }

         this.fTracker.addImage(paramImage, this.fNewID++);
       }
     }






     public void waitForPendingImages()
     {
       if (this.fTracker != null)
       {
         try
         {
           this.fTracker.waitForAll();
         }
         catch (Exception localException) {}



         this.fTracker = null;
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\ResLoader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */