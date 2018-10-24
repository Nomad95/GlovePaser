   package temp.com.mathworks.util;

   import java.awt.Graphics;
   import java.awt.Graphics2D;
   import java.awt.Image;
   import java.awt.RenderingHints;
   import java.awt.image.BufferedImage;
   import java.io.File;
   import java.io.IOException;
   import java.io.InputStream;
   import java.net.URL;
   import java.util.Iterator;
   import javax.imageio.IIOImage;
   import javax.imageio.ImageIO;
   import javax.imageio.ImageReader;
   import javax.imageio.ImageWriteParam;
   import javax.imageio.ImageWriter;
   import javax.imageio.stream.FileImageOutputStream;
   import javax.imageio.stream.ImageInputStream;
   import javax.swing.Icon;
   import javax.swing.ImageIcon;
   import javax.swing.JLabel;
   import org.apache.batik.transcoder.TranscoderException;
   import org.apache.batik.transcoder.TranscoderInput;
   import org.apache.batik.transcoder.TranscoderOutput;
   import org.apache.batik.transcoder.image.ImageTranscoder;










   public class IconUtils
   {
     public static ImageIcon createScaledIcon(Icon paramIcon, int paramInt1, int paramInt2)
     {
       int i = paramIcon.getIconWidth();
       int j = paramIcon.getIconHeight();
       Object localObject;
       if ((paramIcon instanceof ImageIcon)) {
         localObject = ((ImageIcon)paramIcon).getImage();
       } else {
         localObject = new BufferedImage(i, j, 2);
         Graphics localGraphics = ((Image)localObject).getGraphics();
         paramIcon.paintIcon(new JLabel(), localGraphics, 0, 0);
         localGraphics.dispose();
       }
       return new ImageIcon(createScaledImage((Image)localObject, i, j, paramInt1, paramInt2));
     }

     public static Icon scaleForDPI(Icon paramIcon) {
       if (ResolutionUtils.scalingEnabled()) {
         return createScaledIcon(paramIcon, ResolutionUtils.scaleSize(paramIcon.getIconWidth()), ResolutionUtils.scaleSize(paramIcon.getIconHeight()));
       }

       return paramIcon;
     }

     public static ImageIcon createScaledAnimatedIcon(URL paramURL, int paramInt1, int paramInt2)
     {
       try {
         ImageReader localImageReader = (ImageReader)ImageIO.getImageReadersBySuffix("gif").next();
         ImageInputStream localImageInputStream = ImageIO.createImageInputStream(paramURL.openStream());
         localImageReader.setInput(localImageInputStream);

         File localFile = File.createTempFile("scaledIcon", ".gif");
         FileImageOutputStream localFileImageOutputStream = new FileImageOutputStream(localFile);
         ImageWriter localImageWriter = (ImageWriter)ImageIO.getImageWritersBySuffix("gif").next();
         ImageWriteParam localImageWriteParam = localImageWriter.getDefaultWriteParam();
         localImageWriter.setOutput(localFileImageOutputStream);
         localImageWriter.prepareWriteSequence(null);

         int i = 0; for (int j = localImageReader.getNumImages(true); i < j; i++) {
           BufferedImage localBufferedImage1 = localImageReader.read(i);
           BufferedImage localBufferedImage2 = createScaledImage(localBufferedImage1, localBufferedImage1.getWidth(), localBufferedImage1.getHeight(), paramInt1, paramInt2);
           localImageWriter.writeToSequence(new IIOImage(localBufferedImage2, null, localImageReader.getImageMetadata(i)), localImageWriteParam);
         }
         localImageInputStream.close();
         localFileImageOutputStream.close();
         return new ImageIcon(localFile.getPath());
       } catch (IOException localIOException) {}
       return null;
     }

     public static boolean isAnimatedGif(URL paramURL)
     {
       if (paramURL.getFile().endsWith(".gif")) {
         try {
           ImageReader localImageReader = (ImageReader)ImageIO.getImageReadersBySuffix("gif").next();
           ImageInputStream localImageInputStream = ImageIO.createImageInputStream(paramURL.openStream());
           localImageReader.setInput(localImageInputStream);
           boolean bool = localImageReader.getNumImages(true) > 1;
           localImageInputStream.close();
           return bool;
         } catch (Exception localException) {
           return false;
         }
       }
       return false;
     }

     private static BufferedImage createScaledImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
       BufferedImage localBufferedImage = new BufferedImage(paramInt3, paramInt4, 2);
       Graphics2D localGraphics2D = localBufferedImage.createGraphics();
       localGraphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
       localGraphics2D.drawImage(paramImage, 0, 0, paramInt3, paramInt4, 0, 0, paramInt1, paramInt2, null);
       localGraphics2D.dispose();
       return localBufferedImage;
     }



     public static ImageIcon createImageIconFromSVG(InputStream paramInputStream, int paramInt1, int paramInt2)
     {
       TranscoderInput localTranscoderInput = new TranscoderInput(paramInputStream);

       ImageIconTranscoder localImageIconTranscoder = new ImageIconTranscoder(null);
       localImageIconTranscoder.addTranscodingHint(ImageTranscoder.KEY_WIDTH, Float.valueOf(paramInt1));
       localImageIconTranscoder.addTranscodingHint(ImageTranscoder.KEY_HEIGHT, Float.valueOf(paramInt2));

       ImageIconOutput localImageIconOutput = new ImageIconOutput(null);
       try
       {
         localImageIconTranscoder.transcode(localTranscoderInput, localImageIconOutput);
         return localImageIconOutput.getIcon();
       } catch (Exception localException) {
         Log.logException(localException);
       }
       return null;
     }



     private static class ImageIconOutput
       extends TranscoderOutput
     {
       private ImageIcon fIcon;



       private void setIcon(ImageIcon paramImageIcon) { this.fIcon = paramImageIcon; }
       private ImageIcon getIcon() { return this.fIcon; }
     }


     private static class ImageIconTranscoder
       extends ImageTranscoder
     {
       public BufferedImage createImage(int paramInt1, int paramInt2)
       {
         return new BufferedImage(paramInt1, paramInt2, 2);
       }

       public void writeImage(BufferedImage paramBufferedImage, TranscoderOutput paramTranscoderOutput) throws TranscoderException {
         ImageIcon localImageIcon = new ImageIcon(paramBufferedImage);
         if ((paramTranscoderOutput instanceof ImageIconOutput)) {
           ImageIconOutput.access$300((ImageIconOutput)paramTranscoderOutput, localImageIcon);
         }
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\IconUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */