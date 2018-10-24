   package temp.com.mathworks.util;

   import com.mathworks.cfbutils.NativeCfb;
   import com.mathworks.cfbutils.StatEntry;
   import com.mathworks.cfbutils.StatEntryReceiver;
   import java.awt.Color;
   import java.awt.Component;
   import java.awt.Dialog;
   import java.awt.Dimension;
   import java.awt.Font;
   import java.awt.Frame;
   import java.awt.Point;
   import java.awt.Toolkit;
   import java.awt.Window;
   import java.awt.peer.ComponentPeer;
   import java.io.File;
   import java.lang.reflect.Field;
   import java.lang.reflect.Method;
   import java.util.Map;
   import javax.swing.SwingUtilities;
   import org.apache.commons.lang.Validate;
   import org.jetbrains.annotations.NotNull;
   import org.jetbrains.annotations.Nullable;

   public final class NativeJava
   {
     private static boolean sNativeLibraryExists;
     private static Map<Long, Window> sWindowReferenceTable;
     private static final int DEFAULT_CARET_BLINK_RATE = 500;
     public static final Object[] EMPTY;

     static
     {
       sNativeLibraryExists = false;


       sWindowReferenceTable = new java.util.Hashtable(5);


       EMPTY = new Object[0];



       if (NativeJavaSwitch.isLoadingEnabled())
       {
         try
         {
           System.loadLibrary("nativejava");
           sNativeLibraryExists = true;





           if (PlatformInfo.isWindows()) {
             tellJavaVersion(System.getProperty("java.version").startsWith("1.1"));
           }
         }
         catch (Throwable localThrowable) {}
       }
     }








     public static boolean nativeLibraryExists()
     {
       return sNativeLibraryExists;
     }






     public static String getSystemCharEncoding()
     {
       return nativeGetCharEncoding();
     }









     public static boolean canWrite(File paramFile)
     {
       if (PlatformInfo.isWindows())
       {

         File localFile = paramFile;
         while ((localFile != null) && (!localFile.exists())) {
           localFile = localFile.getParentFile();
         }

         if (localFile == null)
         {
           throw new IllegalArgumentException("canWrite shouldn't be called for a file that has no existing parents: " + paramFile);
         }



         return canWriteAccordingToWindows(NativeCfb.getNormalizedWindowsPath(localFile));
       }
       return paramFile.canWrite();
     }

















     public static long getHWnd(Frame paramFrame)
     {
       return getHWnd(paramFrame, "sun.awt.windows.WFramePeer");
     }








     public static long getHWnd(Dialog paramDialog)
     {
       return getHWnd(paramDialog, "sun.awt.windows.WDialogPeer");
     }








     public static long getHWnd(Window paramWindow)
     {
       return getHWnd(paramWindow, "sun.awt.windows.WWindowPeer");
     }

     private static long getHWnd(Window paramWindow, String paramString) {
       Validate.notNull(paramWindow, "'window' cannot be null");

       long l = 0L;

       ComponentPeer localComponentPeer = paramWindow.getPeer();
       if ((PlatformInfo.isWindows()) && (localComponentPeer != null) && (localComponentPeer.getClass().getName().equals(paramString)))
       {
         try
         {
           Method localMethod = localComponentPeer.getClass().getMethod("getHWnd", new Class[0]);
           l = ((Long)localMethod.invoke(localComponentPeer, new Object[0])).longValue();
         } catch (Exception localException) {
           Log.logException(localException);
         }
       }
       return l;
     }










     /**
      * @deprecated

     public static boolean listFiles(String paramString, AsyncReceiver<StatEntry> paramAsyncReceiver)
     {
       StatEntryReceiver local1 = new StatEntryReceiver()
       {
         public boolean receive(StatEntry paramAnonymousStatEntry) {
           return this.val$output.receive(NativeJava.createStatEntry(paramAnonymousStatEntry));
         }
       };
       return NativeCfb.listFiles(paramString, local1);
     }

     /**
      * @deprecated

     public static File resolveMappedDriveToUnc(String paramString) {
       return NativeCfb.resolveMappedDriveToUnc(paramString);
     }

     /**
      * @deprecated

     public static StatEntry getStat(String paramString) {
       StatEntry localStatEntry = NativeCfb.getStat(paramString);
       if (localStatEntry == null) {
         return null;
       }
       return createStatEntry(localStatEntry);
     }

     @NotNull
     private static StatEntry createStatEntry(StatEntry paramStatEntry)
     {
       return new StatEntry(paramStatEntry.getName(), paramStatEntry.st_mode, paramStatEntry.st_size, paramStatEntry.st_mtime, paramStatEntry.st_ctime, paramStatEntry.s_isdir, paramStatEntry.isHidden());
     }



     public static class StatEntry
     {
       public final long st_mode;


       public final long st_size;


       public final long st_mtime;


       public final long st_ctime;


       public final boolean s_isdir;


       private final String fName;

       private final boolean fHiddenFlag;


       public StatEntry(long paramLong1, long paramLong2, long paramLong3, long paramLong4, boolean paramBoolean1, boolean paramBoolean2)
       {
         this(null, paramLong1, paramLong2, paramLong3, paramLong4, paramBoolean1, paramBoolean2);
       }














       public StatEntry(@Nullable String paramString, long paramLong1, long paramLong2, long paramLong3, long paramLong4, boolean paramBoolean1, boolean paramBoolean2)
       {
         this.fName = paramString;
         this.st_mode = paramLong1;
         this.st_size = paramLong2;
         this.st_mtime = paramLong3;
         this.st_ctime = paramLong4;
         this.s_isdir = paramBoolean1;
         this.fHiddenFlag = paramBoolean2;
       }

       public String getName()
       {
         return this.fName;
       }

       public boolean isHidden()
       {
         return this.fHiddenFlag;
       }
     }










     public static long hWndFromWindow(Window paramWindow)
     {
       if (!PlatformInfo.isWindows()) {
         throw new IllegalStateException("This method may be called only on Windows.");
       }
       if (paramWindow == null) {
         throw new IllegalArgumentException();
       }
       if ((paramWindow instanceof HWndProvider))
       {
         return ((HWndProvider)paramWindow).getHWnd();
       }

       return 0L;
     }










     public static long hWndFromComponent(Component paramComponent)
     {
       if (!PlatformInfo.isWindows()) {
         throw new IllegalStateException("This method may be called only on Windows.");
       }
       if (paramComponent == null) {
         throw new IllegalArgumentException();
       }
       if (!(paramComponent instanceof Window))
       {
         paramComponent = SwingUtilities.windowForComponent(paramComponent);
       }
       if ((paramComponent instanceof Window)) {
         return hWndFromWindow((Window)paramComponent);
       }
       return 0L;
     }

















     public static long getNativeWindowFromComponent(Component paramComponent)
     {
       if (paramComponent == null)
         return 0L;
       if (PlatformInfo.isWindows())
         return hWndFromComponent(paramComponent);
       if (PlatformInfo.isMacintosh()) {
         Component localComponent = SwingUtilities.getRoot(paramComponent);
         return getNSWindowFromComponent(localComponent);
       }
       throw new IllegalStateException("This method may be called only on Windows or Mac");
     }








































































     /**
      * @deprecated

     public static void changeFileAttribute(String paramString1, String paramString2)
     {
       NativeCfb.changeFileAttribute(paramString1, paramString2);
     }

     public static void attachThreadInput()
     {
       if (PlatformInfo.isWindows()) {
         AttachThreadInput();
       }
     }

     public static void detachThreadInput()
     {
       if (PlatformInfo.isWindows()) {
         DetachThreadInput();
       }
     }





     public static String getAcrobatPath()
     {
       if (!PlatformInfo.isWindows()) {
         throw new IllegalStateException("This method may be called only on Windows.");
       }

       return GetAcrobatPath();
     }












     public static int getCaretBlinkRate()
     {
       int i = 500;
       if ((sNativeLibraryExists) && (PlatformInfo.isWindows())) {
         i = GetSystemCaretBlinkRate();

         if (i < 0) {
           i = 500;
         }
       }
       else {
         Object localObject = Toolkit.getDefaultToolkit().getDesktopProperty("awt.cursorBlinkRate");
         if ((localObject instanceof Integer)) {
           i = ((Integer)localObject).intValue();
         }
       }
       return i;
     }







     private static void addToWindowReferenceTable(Window paramWindow, long paramLong)
     {
       sWindowReferenceTable.put(Long.valueOf(paramLong), paramWindow);
     }







     public static Window windowReferenceFromHWND(long paramLong)
     {
       checkHWnd(paramLong);
       return (Window)sWindowReferenceTable.get(Long.valueOf(paramLong));
     }






















     public static void setNextModalParent(long paramLong)
     {
       if ((sNativeLibraryExists) && (PlatformInfo.getPlatform() == 1))
       {

         SetNextModalParent(paramLong);
       }
     }








     public static void enableNativeEvents(Window paramWindow, long paramLong)
     {
       if ((sNativeLibraryExists) && (PlatformInfo.isWindows())) {
         checkHWnd(paramLong);
         addToWindowReferenceTable(paramWindow, paramLong);
         nativeEnableFrameEvents(paramWindow, paramLong);
       }
     }







     public static void enableNativeDialogEvents(Window paramWindow, long paramLong)
     {
       enableNativeDialogEvents(paramWindow, paramLong, true);
     }











     public static void enableNativeDialogEvents(Window paramWindow, long paramLong, boolean paramBoolean)
     {
       if ((sNativeLibraryExists) && (PlatformInfo.isWindows())) {
         checkHWnd(paramLong);
         addToWindowReferenceTable(paramWindow, paramLong);
         nativeEnableDialogEvents(paramWindow, paramLong, paramBoolean);
       }
     }






     public static void disableNativeEvents(long paramLong)
     {
       if ((sNativeLibraryExists) && (PlatformInfo.isWindows())) {
         checkHWnd(paramLong);
         sWindowReferenceTable.remove(Long.valueOf(paramLong));
         nativeDisableFrameEvents(null, paramLong);
       }
     }










     public static boolean isMinimized(Window paramWindow)
     {
       boolean bool = false;

       if (PlatformInfo.isWindows())
       {
         if (sNativeLibraryExists) {
           try
           {
             bool = isMinimized(hWndFromWindow(paramWindow));

           }
           catch (IllegalStateException localIllegalStateException) {}
         }
       }
       else if ((PlatformInfo.isMacintosh()) && ((paramWindow instanceof Frame)))
       {
         try
         {
           Class[] arrayOfClass = null;
           Field localField = Frame.class.getField("ICONIFIED");
           Method localMethod = Frame.class.getMethod("getState", arrayOfClass);
           if (localMethod != null)
           {
             Object[] arrayOfObject = EMPTY;
             Integer localInteger = (Integer)localMethod.invoke(paramWindow, arrayOfObject);

             bool = localInteger.intValue() == localField.getInt(null);
           }
         }
         catch (Exception localException) {}
       }



       return bool;
     }









     public static boolean isMaximized(Window paramWindow)
     {
       boolean bool = false;

       if ((PlatformInfo.isWindows()) && (sNativeLibraryExists))
       {
         try
         {
           bool = isMaximized(hWndFromWindow(paramWindow));
         }
         catch (IllegalStateException localIllegalStateException) {}
       }



       return bool;
     }







     public static void setfocus(long paramLong)
     {
       if (sNativeLibraryExists) {
         SetFocus(paramLong);
       }
     }

     public static String getWindowTitle(long paramLong) {
       String str = "";
       if ((PlatformInfo.isWindows()) && (sNativeLibraryExists)) {
         str = GetWindowTitle(paramLong);
       }
       return str;
     }





     public static void setMinimized(long paramLong, boolean paramBoolean)
     {
       if ((PlatformInfo.isWindows()) && (sNativeLibraryExists))
       {
         if (paramBoolean) {
           minimize(paramLong);
         } else if (isMinimized(paramLong)) {
           restore(paramLong);
         }
       }
     }


     public static void setMinimized(Window paramWindow, boolean paramBoolean)
     {
       Object localObject1;

       Object localObject2;
       if (PlatformInfo.isWindows())
       {
         if (sNativeLibraryExists)
         {






           localObject1 = getRestoredSize(paramWindow);
           localObject2 = getRestoredLocation(paramWindow);
           long l;
           try {
             l = hWndFromWindow(paramWindow);
           } catch (IllegalStateException localIllegalStateException) {
             paramWindow.addNotify();
             paramWindow.setVisible(false);
             l = hWndFromWindow(paramWindow);
           }
           assert (l != 0L);
           setMinimized(hWndFromWindow(paramWindow), paramBoolean);

           setRestoredSize(paramWindow, ((Dimension)localObject1).width, ((Dimension)localObject1).height);
           setRestoredLocation(paramWindow, ((Point)localObject2).x, ((Point)localObject2).y);
         }
       }
       else if ((PlatformInfo.isMacintosh()) && ((paramWindow instanceof Frame)))
       {
         try
         {
           localObject1 = new Class[] { Integer.TYPE };
           localObject2 = Frame.class.getField("ICONIFIED");
           Method localMethod = Frame.class.getMethod("setState", (Class[])localObject1);
           if (localMethod != null)
           {

             Object[] arrayOfObject = { ((Field)localObject2).get(null) };
             localMethod.invoke(paramWindow, arrayOfObject);
           }
         }
         catch (Exception localException) {}
       }
     }







     private static void setMaximized(long paramLong, boolean paramBoolean)
     {
       if (paramBoolean) {
         maximize(paramLong);
       } else if (isMaximized(paramLong)) {
         restore(paramLong);
       }
     }











     public static void setMaximized(Window paramWindow, boolean paramBoolean)
     {
       if ((PlatformInfo.isWindows()) && (sNativeLibraryExists))
       {
         Dimension localDimension = getRestoredSize(paramWindow);
         Point localPoint = getRestoredLocation(paramWindow);
         long l;
         try {
           l = hWndFromWindow(paramWindow);
         } catch (IllegalStateException localIllegalStateException) {
           paramWindow.addNotify();
           paramWindow.setVisible(false);
           l = hWndFromWindow(paramWindow);
         }
         assert (l != 0L);
         setMaximized(hWndFromWindow(paramWindow), paramBoolean);

         setRestoredSize(paramWindow, localDimension.width, localDimension.height);
         setRestoredLocation(paramWindow, localPoint.x, localPoint.y);
       }
     }








     public static Dimension getRestoredSize(Window paramWindow)
     {
       if ((sNativeLibraryExists) && (isMaximized(paramWindow))) {
         return getRestoredSize(hWndFromWindow(paramWindow));
       }
       return paramWindow.getSize();
     }








     public static void setRestoredSize(Window paramWindow, int paramInt1, int paramInt2)
     {
       if ((sNativeLibraryExists) && (isMaximized(paramWindow))) {
         setRestoredSize(hWndFromWindow(paramWindow), paramInt1, paramInt2);
       } else {
         paramWindow.setSize(paramInt1, paramInt2);
       }
     }





     public static void setRestoredSize(Window paramWindow, Dimension paramDimension)
     {
       setRestoredSize(paramWindow, paramDimension.width, paramDimension.height);
     }








     public static Point getRestoredLocation(Window paramWindow)
     {
       if ((sNativeLibraryExists) && (isMaximized(paramWindow))) {
         return getRestoredLocation(hWndFromWindow(paramWindow));
       }
       return paramWindow.getLocation();
     }








     public static void setRestoredLocation(Window paramWindow, int paramInt1, int paramInt2)
     {
       if ((sNativeLibraryExists) && (isMaximized(paramWindow))) {
         setRestoredLocation(hWndFromWindow(paramWindow), paramInt1, paramInt2);
       } else {
         paramWindow.setLocation(paramInt1, paramInt2);
       }
     }





     public static void setRestoredLocation(Window paramWindow, Point paramPoint)
     {
       setRestoredLocation(paramWindow, paramPoint.x, paramPoint.y);
     }



     public static void setBounds(Window paramWindow, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
     {
       setBounds(hWndFromWindow(paramWindow), paramInt1, paramInt2, paramInt3, paramInt4);
     }































     public static void getWindowsTextMetric(long paramLong, String paramString, int paramInt1, int paramInt2, Object paramObject)
     {
       if (!PlatformInfo.isWindows())
         throw new IllegalStateException("This method may be called only on Windows.");
       long l = GetWindowsDC(paramLong);
       GetWindowsTextMetric(l, paramString, paramInt1, paramInt2, paramObject);
       ReleaseWindowsDC(paramLong, l);
     }














     private static void checkHWnd(long paramLong)
     {
       assert (paramLong != 0L) : "Must only be called with a non-zero hWnd";
     }


























     public static Color componentColorFromSystemColor(String paramString)
     {
       if (!PlatformInfo.isMacintosh()) {
         throw new IllegalStateException("componentColorFromSystemColor is only valid on the Macintosh platform.");
       }

       return (Color)colorFromSystemColor(paramString);
     }













     public static String getPhysicalFontName(Font paramFont)
     {
       if (!PlatformInfo.isMacintosh()) {
         throw new IllegalStateException("getPhysicalFontName is implemented specifically for the Macintosh Platform");
       }
       return (String)getMacPhysicalFontName(paramFont);
     }


     public static String showPageSetupDialog(String paramString)
     {
       if (!PlatformInfo.isMacintosh()) {
         throw new IllegalStateException("showPageSetupDialog is implemented specifically for the Macintosh Platform");
       }
       return (String)showMacPageSetupDialog(paramString);
     }























     public static int showAlert(Component paramComponent, int paramInt, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString)
     {
       if ((!PlatformInfo.isMacintosh()) && (!PlatformInfo.isWindowsVistaAndAbove())) {
         throw new IllegalStateException("showAlert is not supported on this platform");
       }
       return showNativeAlert(getNativeWindowFromComponent(paramComponent), paramInt, paramString1, paramString2, paramString3, paramArrayOfString);
     }


     /**
      * @deprecated

     public static String getCurrentDirectory()
     {
       return NativeCfb.getCurrentDirectory();
     }

     private static native String nativeGetCharEncoding();

     private static native boolean canWriteAccordingToWindows(String paramString);

     private static native void tellJavaVersion(boolean paramBoolean);

     public static native long hWndFromTitle(String paramString);

     private static native long getNSWindowFromComponent(Object paramObject);

     public static native boolean hWndIsEnabled(long paramLong);

     public static native boolean isThisProcessInForeground(long paramLong);

     public static native int drawMenuBar(long paramLong);

     public static native long getMenuBar(long paramLong);

     public static native long getSubmenu(long paramLong, int paramInt);

     public static native int setMenuItemLabel(long paramLong, int paramInt, String paramString);

     private static native void AttachThreadInput();

     private static native void DetachThreadInput();

     private static native String GetAcrobatPath();

     private static native int GetSystemCaretBlinkRate();

     private static native void nativeEnableFrameEvents(Window paramWindow, long paramLong);

     private static native void nativeDisableFrameEvents(@Nullable Window paramWindow, long paramLong);

     private static native void nativeEnableDialogEvents(Window paramWindow, long paramLong, boolean paramBoolean);

     private static native void SetNextModalParent(long paramLong);

     private static native boolean isMinimized(long paramLong);

     private static native boolean isMaximized(long paramLong);

     private static native void minimize(long paramLong);

     private static native void maximize(long paramLong);

     private static native void restore(long paramLong);

     private static native void SetFocus(long paramLong);

     private static native String GetWindowTitle(long paramLong);

     private static native Dimension getRestoredSize(long paramLong);

     private static native void setRestoredSize(long paramLong, int paramInt1, int paramInt2);

     private static native Point getRestoredLocation(long paramLong);

     private static native void setRestoredLocation(long paramLong, int paramInt1, int paramInt2);

     private static native void setBounds(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4);

     public static native byte[] getImageData(Object paramObject);

     public static native void copyPrivateEventData(Object paramObject1, Object paramObject2);

     private static native void GetWindowsTextMetric(long paramLong, String paramString, int paramInt1, int paramInt2, Object paramObject);

     private static native long GetWindowsDC(long paramLong);

     private static native void ReleaseWindowsDC(long paramLong1, long paramLong2);

     public static native boolean winClipboardHasText();

     public static native long winSendMessage(long paramLong1, int paramInt, long paramLong2, long paramLong3);

     public static native void winPostMessage(long paramLong1, int paramInt, long paramLong2, long paramLong3);

     public static native int getMouseButtonState();

     public static native int getMacControlTint();

     public static native void macActivateIgnoringOtherApps();

     private static native Object colorFromSystemColor(String paramString);

     public static native void setDefaultMenuBar();

     public static native void disposeLingeringWindowsMac();

     private static native Object getMacPhysicalFontName(Font paramFont);

     private static native Object showMacPageSetupDialog(String paramString);

     public static native void nativeCheckDisplay();

     private static native int showNativeAlert(long paramLong, int paramInt, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString);
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\NativeJava.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */