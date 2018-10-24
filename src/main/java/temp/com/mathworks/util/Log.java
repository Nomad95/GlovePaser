   package temp.com.mathworks.util;

   import java.awt.Container;
   import java.awt.Font;
   import java.awt.event.ActionEvent;
   import java.io.OutputStream;
   import java.io.PrintStream;
   import java.util.concurrent.atomic.AtomicBoolean;
   import javax.swing.Action;
   import javax.swing.JFrame;
   import javax.swing.JMenu;
   import javax.swing.JMenuBar;
   import javax.swing.JScrollPane;
   import javax.swing.JTextArea;
   import javax.swing.SwingUtilities;
   import javax.swing.text.BadLocationException;
   import javax.swing.text.Caret;
   import javax.swing.text.DefaultEditorKit.CopyAction;
   import javax.swing.text.Document;
   import javax.swing.text.TextAction;















   public final class Log
   {
     public static volatile boolean LOGGING = false;



     private static volatile PrintStream sPs = null;

     private static volatile boolean sExiting = false;


     private static final AtomicBoolean STARTED = new AtomicBoolean(false);



     private static boolean logging()
     {
       return (LOGGING) && (!sExiting);
     }




     private static synchronized void defaultSink()
     {
       if ((sPs == null) && (!sExiting))
       {
         TextAreaOutputStream localTextAreaOutputStream = new TextAreaOutputStream();Object localObject1 = null;
         try { setSink(localTextAreaOutputStream);
         }
         catch (Throwable localThrowable2)
         {
           localObject1 = localThrowable2;throw localThrowable2;
         } finally {
           if (localTextAreaOutputStream != null) { if (localObject1 != null) try { localTextAreaOutputStream.close(); } catch (Throwable localThrowable3) { ((Throwable)localObject1).addSuppressed(localThrowable3); } else { localTextAreaOutputStream.close();
             }
           }
         }
       }
     }




     public static synchronized void log(String paramString)
     {
       if (logging())
       {
         defaultSink();
         sPs.print(paramString);
       }
     }









     public static synchronized void logException(Exception paramException)
     {
       if (logging())
       {
         logThrowable(paramException);
       }
     }








     public static synchronized void dumpStack()
     {
       if (logging())
       {
         defaultSink();
         Exception localException = new Exception("Stack trace");
         logException(localException);
       }
     }







     public static synchronized void logThrowable(Throwable paramThrowable)
     {
       if (logging())
       {
         defaultSink();
         String str = paramThrowable.getMessage();
         if ((paramThrowable instanceof BadLocationException))
         {
           str = str + " at offset " + ((BadLocationException)paramThrowable).offsetRequested();
         }

         sPs.println(str);
         paramThrowable.printStackTrace(sPs);
       }
     }







     public static synchronized void printLn(String paramString)
     {
       if (logging())
       {
         defaultSink();
         sPs.println(paramString);
       }
     }





     public static void setLogging(boolean paramBoolean)
     {
       LOGGING = paramBoolean;



       if ((paramBoolean) && (STARTED.compareAndSet(false, true)) && (sPs == null)) {
         defaultSink();
       }
     }





     public static synchronized boolean isLogging()
     {
       return LOGGING;
     }




     public static synchronized boolean isVisible()
     {
       return TextAreaOutputStream.isVisible();
     }




     public static synchronized String getContent()
       throws BadLocationException
     {
       return TextAreaOutputStream.access$000();
     }





     public static synchronized void setSink(OutputStream paramOutputStream)
     {
       if (sPs != null)
       {
         sPs.flush();
         sPs.close();
       }



       sPs = new PrintStream(paramOutputStream, true);

       System.setErr(sPs);
     }




     public static synchronized void exiting()
     {
       if (sExiting) return;
       Runnable local1 = new Runnable()
       {
         public void run() {

           if (Log.sPs != null) {
             Log.sPs.close();
             Log.access$102(null);
           }
         }
       };
       if (SwingUtilities.isEventDispatchThread()) local1.run(); else
         SwingUtilities.invokeLater(local1);
       sExiting = true;
     }








     private static final class TextAreaOutputStream
       extends OutputStream
     {
       private static JFrame sFrame = null;


       private static volatile JTextArea sTestLog = null;




       private static String sConfigString = null;

       private static boolean sExitingCalled = false;

       TextAreaOutputStream()
       {
         try {
           Runnable local1 = new Runnable()
           {
             public void run() {
               TextAreaOutputStream.access$202(new JFrame("Log Window"));
               TextAreaOutputStream.access$302(new JTextArea(30, 60));

               TextAreaOutputStream.sTestLog.setEditable(false);
               TextAreaOutputStream.sTestLog.setWrapStyleWord(true);
               TextAreaOutputStream.sTestLog.setLineWrap(true);
               float f1 = PlatformInfo.getVirtualScreenDPI();


               Font localFont = new Font("Monospaced", 0, 12);
               float f2 = localFont.getSize() * (f1 / 72.0F);
               localFont = localFont.deriveFont(f2);
               TextAreaOutputStream.sTestLog.setFont(localFont);
               JScrollPane localJScrollPane = new JScrollPane(TextAreaOutputStream.sTestLog);

               JMenuBar localJMenuBar = new JMenuBar();
               localJMenuBar.add(TextAreaOutputStream.access$400());

               TextAreaOutputStream.sFrame.setName("Log Output Window");
               TextAreaOutputStream.sFrame.setJMenuBar(localJMenuBar);

               TextAreaOutputStream.sFrame.getContentPane().add(localJScrollPane);
               TextAreaOutputStream.sFrame.setLocation(10, 10);
               TextAreaOutputStream.sFrame.pack();
               TextAreaOutputStream.sFrame.setVisible(false);
             }
           };
           if (SwingUtilities.isEventDispatchThread()) local1.run(); else {
             SwingUtilities.invokeAndWait(local1);
           }
         }
         catch (Exception localException) {}
       }

       private static String getContent() throws BadLocationException {
         assert (sTestLog != null);
         return sTestLog.getDocument().getText(0, sTestLog.getDocument().getLength());
       }




       public void close()
       {
         if (!sExitingCalled) {
           flush();
         }
       }





       public void flush()
       {
         if (!sExitingCalled) {
           sTestLog.repaint();
         }
       }







       private static void lazyShow()
       {
         assert (sFrame != null);

         if (!sFrame.isVisible())
         {
           if (sConfigString == null)
           {

             String str1 = System.getProperty("os.name");
             str1 = str1 + ", " + System.getProperty("os.arch");
             str1 = str1 + ", " + System.getProperty("os.version");
             sConfigString = "Operating System: " + str1 + "\n";
             String str2 = System.getProperty("java.vendor");
             str2 = str2 + ", " + System.getProperty("java.version");
             sConfigString = sConfigString + "JRE Version: " + str2 + "\n\n";
           }
           assert (sTestLog != null);
           sTestLog.setText(null);
           sTestLog.append(sConfigString);

           sFrame.setVisible(true);
         }
       }



       static boolean isVisible() { return (sFrame != null) && (sFrame.isVisible()); }

       static void exiting() {
         sExitingCalled = true;
         if (sFrame != null) {
           sFrame.setVisible(false);
           sFrame.dispose();
         }
       }






       private static JMenu makeTheMenu()
       {
         JMenu localJMenu = new JMenu("Edit");

         localJMenu.add(new LogSelectAction());
         localJMenu.add(new LogClearAction());
         DefaultEditorKit.CopyAction localCopyAction = new DefaultEditorKit.CopyAction();
         localCopyAction.putValue("Name", "Copy");
         localJMenu.add(localCopyAction);
         return localJMenu;
       }







       public void write(byte[] paramArrayOfByte)
       {
         appendText(new String(paramArrayOfByte));
       }



       public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
       {
         appendText(new String(paramArrayOfByte, paramInt1, paramInt2));
       }



       public void write(int paramInt)
       {
         appendText(new String(new byte[] { (byte)paramInt }));
       }

       private static void appendText(String paramString) {
         Runnable local2 = new Runnable()
         {
           public void run() {
             if (!TextAreaOutputStream.sExitingCalled) {
               TextAreaOutputStream.access$600();
               TextAreaOutputStream.sTestLog.append(this.val$s);
               TextAreaOutputStream.sTestLog.setCaretPosition(TextAreaOutputStream.sTestLog.getText().length());
             }
           }
         };

         if (SwingUtilities.isEventDispatchThread()) {
           local2.run();
         }
         else {
           SwingUtilities.invokeLater(local2);
         }
       }





       private static final class LogSelectAction
         extends TextAction
       {
         LogSelectAction()
         {
           super();
         }







         public void actionPerformed(ActionEvent paramActionEvent)
         {
           TextAreaOutputStream.sTestLog.getCaret().setDot(0);
           int i = TextAreaOutputStream.sTestLog.getDocument().getLength();
           TextAreaOutputStream.sTestLog.moveCaretPosition(i);
         }
       }

       private static final class LogClearAction extends TextAction {
         LogClearAction() {
           super();
         }

         public void actionPerformed(ActionEvent paramActionEvent) {
           TextAreaOutputStream.sTestLog.setText(null);
         }
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\Log.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */