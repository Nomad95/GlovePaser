   package temp.com.mathworks.util.jarloader;

   import java.io.ByteArrayInputStream;
   import java.io.File;
   import java.io.IOException;
   import java.io.InputStream;
   import java.io.OutputStream;
   import java.io.PrintWriter;
   import java.util.Enumeration;
   import java.util.Vector;
















   public class Manifest
   {
     private Vector entries = new Vector();

     static final boolean debug = false;

     static final void debug(String paramString) {}

     public Manifest() {}

     public Manifest(byte[] paramArrayOfByte)
       throws IOException
     {
       this(new ByteArrayInputStream(paramArrayOfByte));
     }


     public Manifest(InputStream paramInputStream)
       throws IOException
     {
       while (paramInputStream.available() != 0) {
         MessageHeader localMessageHeader = new MessageHeader(paramInputStream);
         this.entries.addElement(localMessageHeader);
       }
     }

     public Manifest(String[] paramArrayOfString)
       throws IOException
     {
       MessageHeader localMessageHeader = new MessageHeader();
       localMessageHeader.add("Manifest-Version", "1.0");
       addEntry(localMessageHeader);
       addFiles(null, paramArrayOfString);
     }

     public void addEntry(MessageHeader paramMessageHeader) {
       this.entries.addElement(paramMessageHeader);
     }

     public MessageHeader getEntry(String paramString) {
       Enumeration localEnumeration = entries();
       while (localEnumeration.hasMoreElements()) {
         MessageHeader localMessageHeader = (MessageHeader)localEnumeration.nextElement();
         String str = localMessageHeader.findValue("Name");
         if ((str != null) && (str.equals(paramString))) {
           return localMessageHeader;
         }
       }
       return null;
     }

     public MessageHeader entryAt(int paramInt) {
       return (MessageHeader)this.entries.elementAt(paramInt);
     }

     public Enumeration entries() {
       return this.entries.elements();
     }

     public void addFiles(File paramFile, String[] paramArrayOfString) throws IOException {
       if (paramArrayOfString == null)
         return;
       for (int i = 0; i < paramArrayOfString.length; i++) {
         File localFile;
         if (paramFile == null) {
           localFile = new File(paramArrayOfString[i]);
         } else {
           localFile = new File(paramFile, paramArrayOfString[i]);
         }
         if (localFile.isDirectory()) {
           addFiles(localFile, localFile.list());
         } else {
           addFile(localFile);
         }
       }
     }

     public void addFile(File paramFile) throws IOException {
       MessageHeader localMessageHeader = new MessageHeader();
       localMessageHeader.add("Name", paramFile.getPath());
       addEntry(localMessageHeader);
     }






     public void stream(OutputStream paramOutputStream, Vector paramVector)
       throws IOException
     {
       MessageHeader localMessageHeader1 = (MessageHeader)this.entries.elementAt(0);
       if (localMessageHeader1.findValue("Manifest-Version") == null) {
         throw new IOException("Manifest file requires Manifest-Version: 1.0 in 1st header");
       }


       PrintWriter localPrintWriter = new PrintWriter(paramOutputStream);
       localMessageHeader1.print(localPrintWriter);

       for (int i = 1; i < this.entries.size(); i++) {
         MessageHeader localMessageHeader2 = (MessageHeader)this.entries.elementAt(i);

         localMessageHeader2.print(localPrintWriter);


         String str = localMessageHeader2.findValue("name");
         if ((paramVector != null) && (str != null)) {
           paramVector.addElement(str);
         }
       }
     }


     public static boolean isManifestName(String paramString)
     {
       if (paramString.charAt(0) == '/') {
         paramString = paramString.substring(1, paramString.length());
       }

       paramString = paramString.toUpperCase();

       if (paramString.equals("META-INF/MANIFEST.MF")) {
         return true;
       }
       return false;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\jarloader\Manifest.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */