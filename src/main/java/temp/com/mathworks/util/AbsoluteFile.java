   package temp.com.mathworks.util;

   import java.io.File;
   import java.io.IOException;
   import java.net.URI;

















   public class AbsoluteFile
   {
     private final File fFile;

     public AbsoluteFile(String paramString)
     {
       this(new File(paramString));
     }




     public AbsoluteFile(URI paramURI)
     {
       this(new File(paramURI));
     }








     public AbsoluteFile(File paramFile)
     {
       if (paramFile == null) {
         throw new IllegalArgumentException("File cannot be null.");
       }
       this.fFile = new File(FileUtils.normalizePathname(paramFile.getAbsolutePath()));
     }



     public void deleteOnExit()
     {
       this.fFile.deleteOnExit();
     }


     public boolean createNewFile()
       throws IOException
     {
       return this.fFile.createNewFile();
     }


     public static AbsoluteFile createTempFile(String paramString1, String paramString2)
       throws IOException
     {
       return new AbsoluteFile(File.createTempFile(paramString1, paramString2));
     }



     public boolean exists()
     {
       return this.fFile.exists();
     }



     public boolean isDirectory()
     {
       return this.fFile.isDirectory();
     }



     public String getName()
     {
       return this.fFile.getName();
     }



     public String getPath()
     {
       return this.fFile.getAbsolutePath();
     }



     public URI toURI()
     {
       return this.fFile.toURI();
     }





     public File toFile()
     {
       return this.fFile;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\AbsoluteFile.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */