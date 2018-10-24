   package temp.com.mathworks.mvm;

   import com.mathworks.mvm.eventmgr.DefaultEventMgr;
   import com.mathworks.mvm.eventmgr.EventMgr;
   import java.io.IOException;
   import java.io.Writer;
   import java.lang.ref.WeakReference;
   import java.util.ArrayList;
   import java.util.Arrays;
   import java.util.Collection;
   import java.util.HashMap;
   import java.util.Iterator;
   import java.util.List;
   import java.util.concurrent.TimeoutException;
   import java.util.concurrent.atomic.AtomicBoolean;
   import org.jetbrains.annotations.Nullable;































   public class MvmFactory
   {
     private final FactoryType fType;
     private final long fFactoryHandle;
     private static final Object LOCK;
     private static final long DEFAULT_INTERVAL_MSECS = 100L;
     private final String fFactoryName;
     private AtomicBoolean fTerminated = new AtomicBoolean();

     private final EventMgr fEventMgr;

     private static HashMap<Long, WeakReference<MVM>> sMvmMap;

     private static MvmFactory sLocalFactory;

     public static enum JavaLevel
     {
       Undefined(0),
       NoJava(1),
       SimpleJVM(2),
       JavaUI(3),
       JavaDesktop(4);

       private JavaLevel(int paramInt) {
         this.fValue = paramInt;
       }

       int getValue() { return this.fValue; }

       private final int fValue;
     }

     public static class FactoryOptions
       implements Cloneable
     {
       private final List<String> fArgs;
       private final JavaLevel fJavaLevel;
       private static final String[] EMPTY = new String[0];





       public FactoryOptions(@Nullable String... paramVarArgs)
       {
         this(null, paramVarArgs);
       }





       public FactoryOptions(@Nullable MvmFactory.JavaLevel paramJavaLevel, @Nullable String... paramVarArgs)
       {
         this.fJavaLevel = (paramJavaLevel == null ? JavaLevel.Undefined : paramJavaLevel);
         this.fArgs = (paramVarArgs == null ? null : new ArrayList(Arrays.asList(paramVarArgs)));
       }



       public String[] toArray()
       {
         return this.fArgs == null ? EMPTY : (String[])this.fArgs.toArray(new String[this.fArgs.size()]);
       }

       public JavaLevel getJavaLevel()
       {
         return this.fJavaLevel;
       }

       public boolean equals(Object paramObject)
       {
         if (!(paramObject instanceof FactoryOptions)) return false;
         FactoryOptions localFactoryOptions = (FactoryOptions)paramObject;

         return (localFactoryOptions.fJavaLevel.equals(this.fJavaLevel)) && (this.fArgs.size() == localFactoryOptions.fArgs.size()) && (this.fArgs.containsAll(localFactoryOptions.fArgs));
       }


       public int hashCode()
       {
         return this.fArgs.hashCode() + this.fJavaLevel.hashCode();
       }

       public FactoryOptions clone()
       {
         try {
           return (FactoryOptions)super.clone();
         } catch (CloneNotSupportedException localCloneNotSupportedException) {}
         return null;
       }
     }


     public static class MvmOptions
     {
       private final Writer fOutWriter;
       private final Writer fErrWriter;
       private final String[] fPath;

       public MvmOptions()
       {
         this(null, null, null);
       }







       public MvmOptions(@Nullable String[] paramArrayOfString, @Nullable Writer paramWriter1, @Nullable Writer paramWriter2)
       {
         this.fPath = (paramArrayOfString == null ? null : (String[])paramArrayOfString.clone());
         this.fOutWriter = paramWriter1;
         this.fErrWriter = paramWriter2;
       }




       public MvmOptions(@Nullable Writer paramWriter1, @Nullable Writer paramWriter2)
       {
         this(null, paramWriter1, paramWriter2);
       }
     }















     public static enum FactoryType
     {
       LOCAL(1),




       CHILD(2),





       NETWORK(3),








       MATLABMVM(4);

       private final int fValue;

       private FactoryType(int paramInt) { this.fValue = paramInt; }

       int getValue() {
         return this.fValue;
       }
     }

     static
     {
       LOCK = new Object();































































































































       sMvmMap = null;


       sLocalFactory = null;








































       MvmImpl.loadLibrary();
     }


     public FactoryType getType()
     {
       return this.fType;
     }




     public String getName()
     {
       return this.fFactoryName;
     }

     public EventMgr getEventMgr() {
       return this.fEventMgr;
     }











































     public static MvmFactory createFactory(FactoryType paramFactoryType, String paramString, @Nullable FactoryOptions paramFactoryOptions)
       throws IllegalArgumentException, IllegalStateException, SecurityException, RuntimeException, InterruptedException
     {
       MvmFactory localMvmFactory = null;
       try {
         localMvmFactory = createFactory(paramFactoryType, paramString, paramFactoryOptions, 0L);
       } catch (TimeoutException localTimeoutException) {}
       assert (localMvmFactory != null);
       return localMvmFactory;
     }














     public static MvmFactory createFactory(FactoryOptions paramFactoryOptions, String paramString, long paramLong)
       throws IllegalArgumentException, IllegalStateException, SecurityException, RuntimeException, InterruptedException, TimeoutException
     {
       return createFactory(FactoryType.NETWORK, paramString, paramFactoryOptions, paramLong);
     }





     private MvmFactory(long paramLong)
     {
       this.fFactoryHandle = paramLong;
       this.fType = FactoryType.MATLABMVM;
       this.fFactoryName = "Java Engine";
       this.fEventMgr = new DefaultEventMgr(new MvmFactoryWrapper(this));
     }




     public static MvmFactory createEngineFactory(String[] paramArrayOfString)
       throws IllegalStateException, InterruptedException
     {
       long l = nativeStartMatlabForEngine(paramArrayOfString);
       return new MvmFactory(l);
     }






     public static MvmFactory connectMatlab(String paramString)
       throws IllegalStateException
     {
       long l = nativeConnectMatlabForEngine(paramString);
       return new MvmFactory(l);
     }





     public static String[] findMatlab()
       throws IllegalStateException
     {
       String[] arrayOfString = nativeFindMatlabForEngine();
       return arrayOfString;
     }




     private static MvmFactory createFactory(FactoryType paramFactoryType, @Nullable String paramString, @Nullable FactoryOptions paramFactoryOptions, long paramLong)
       throws IllegalArgumentException, IllegalStateException, SecurityException, RuntimeException, InterruptedException, TimeoutException
     {
       if (paramFactoryType == FactoryType.LOCAL) return getLocalFactory();
       return new MvmFactory(paramFactoryType, paramString, paramFactoryOptions, paramLong);
     }






















     private MvmFactory(FactoryType paramFactoryType, @Nullable String paramString, @Nullable FactoryOptions paramFactoryOptions, long paramLong)
       throws IllegalArgumentException, IllegalStateException, SecurityException, InterruptedException, TimeoutException, RuntimeException
     {
       this.fType = paramFactoryType;
       if (paramFactoryType == FactoryType.LOCAL)
       {

         assert ((sLocalFactory == null) && (paramFactoryOptions == null));
         this.fFactoryName = "local";
         this.fFactoryHandle = nativeGetLocalFactory();
         if (this.fFactoryHandle == 0L) {
           throw new IllegalStateException("Local process does not support an MvmFactory");
         }
         this.fEventMgr = new DefaultEventMgr(new MvmFactoryWrapper(this));
         return;
       }

       this.fFactoryName = paramString;
       long l1 = nativeCreateFactory(paramFactoryType.getValue(), paramFactoryOptions == null ? 0 : paramFactoryOptions.getJavaLevel().getValue(), paramFactoryOptions == null ? null : paramFactoryOptions.toArray());




       long l2 = paramLong == 0L ? 0L : System.currentTimeMillis();
       long l3 = paramLong == 0L ? 0L : l2 + paramLong;
       long l4 = 100L;
       do
       {
         if (paramLong != 0L) {
           l5 = l3 - l2;
           if (l5 < l4) { l4 = l5;
           }
         }
         long l5 = nativeGetFactory(l1, l4);
         if (l5 != 0L)
         {
           this.fFactoryHandle = l5;

           this.fEventMgr = new DefaultEventMgr(new MvmFactoryWrapper(this));
           return;
         }

         if (Thread.interrupted())
         {
           this.fFactoryHandle = nativeCancelFactory(l1);
           if (this.fFactoryHandle == 0L) { throw new InterruptedException();
           }


           this.fEventMgr = new DefaultEventMgr(new MvmFactoryWrapper(this));
           return;
         }
         l2 = System.currentTimeMillis();
       } while ((paramLong == 0L) || (l2 < l3));

       throw new TimeoutException();
     }




     public static MvmFactory getLocalFactory()
       throws IllegalStateException
     {
       synchronized (MvmFactory.class) {
         if (sLocalFactory != null) return sLocalFactory;
         try {
           sLocalFactory = new MvmFactory(FactoryType.LOCAL, null, null, 0L);
         }
         catch (InterruptedException|TimeoutException localInterruptedException) {}
         return sLocalFactory;
       }
     }







     public MVM createMVM()
       throws MvmTerminatedException, IOException
     {
       return createMVM(null);
     }








     public MVM createMVM(@Nullable MvmOptions paramMvmOptions)
       throws MvmTerminatedException, IOException
     {
       if (this.fTerminated.get()) { throw new MvmTerminatedException(getTerminatedMessage());
       }
       if (this.fType == FactoryType.MATLABMVM) {
         long l1 = nativeGetBlessedMVM(this.fFactoryHandle);
         if (l1 == 0L) throw new MvmTerminatedException("Session or " + getTerminatedMessage());
         return getOrCreateMVM(l1);
       }

       Writer localWriter1 = null;
       Writer localWriter2 = null;
       String[] arrayOfString = null;
       if (paramMvmOptions != null) {
         localWriter1 = paramMvmOptions.fOutWriter;
         localWriter2 = paramMvmOptions.fErrWriter;
         arrayOfString = paramMvmOptions.fPath;
       }
       long l2 = nativeCreateMVM(this.fFactoryHandle, arrayOfString, localWriter1, localWriter2);
       if (l2 == 0L) throw new MvmTerminatedException("Session or " + getTerminatedMessage());
       return getOrCreateMVM(l2);
     }








     public static MVM createLocalMVM(@Nullable MvmOptions paramMvmOptions)
       throws IllegalStateException, MvmTerminatedException
     {
       try
       {
         return getLocalFactory().createMVM(paramMvmOptions);
       }
       catch (IOException localIOException) {
         if (!$assertionsDisabled) throw new AssertionError(); }
       return null;
     }








     public static MVM getCurrentMVM()
       throws IllegalStateException
     {
       long l = nativeGetCurrentMvmHandle();
       return getOrCreateMVM(l);
     }























     public boolean terminate()
     {
       if (!this.fTerminated.compareAndSet(false, true)) return false;
       return nativeTerminateFactory(this.fFactoryHandle);
     }





     public static Collection<MVM> getMVMs()
     {
       synchronized (LOCK) {
         ArrayList localArrayList = new ArrayList(sMvmMap.size());
         MVM localMVM; for (Iterator localIterator = sMvmMap.values().iterator(); localIterator.hasNext();

             localArrayList.add(localMVM))
         {
           WeakReference localWeakReference = (WeakReference)localIterator.next();
           localMVM = (MVM)localWeakReference.get();
           if ((localMVM == null) || (localMVM.isTerminated())) {}
         }
         return localArrayList;
       }
     }

     private String getTerminatedMessage() {
       return "Factory " + getName() + " was terminated.";
     }


     private static MVM getOrCreateMVM(long paramLong)
     {
       synchronized (LOCK) {
         Object localObject1 = getMVM(paramLong);
         if (localObject1 == null) {
           localObject1 = new MvmImpl(paramLong);
           addToMap(paramLong, (MVM)localObject1);
         }
         return (MVM)localObject1;
       }
     }






     public static MVM getMvmForHandle(long paramLong)
     {
       return getOrCreateMVM(paramLong);
     }



     private static MVM getMVM(long paramLong)
     {
       synchronized (LOCK) {
         if (sMvmMap == null) return null;
         WeakReference localWeakReference = (WeakReference)sMvmMap.get(Long.valueOf(paramLong));
         if (localWeakReference == null) return null;
         return (MVM)localWeakReference.get();
       }
     }



     private static void addToMap(long paramLong, MVM paramMVM)
     {
       synchronized (LOCK) {
         if (sMvmMap == null) {
           sMvmMap = new HashMap(5);
         }
         WeakReference localWeakReference = (WeakReference)sMvmMap.put(Long.valueOf(paramLong), new WeakReference(paramMVM));
         if ((!$assertionsDisabled) && (localWeakReference != null) && (localWeakReference.get() != null)) { throw new AssertionError();
         }
       }
     }


     long getHandle()
     {
       return this.fFactoryHandle;
     }

     private static native long nativeCreateFactory(int paramInt1, int paramInt2, @Nullable String[] paramArrayOfString)
       throws IllegalArgumentException, IllegalStateException, SecurityException, RuntimeException;

     private static native long nativeGetFactory(long paramLong1, long paramLong2)
       throws IllegalArgumentException, IllegalStateException, SecurityException, RuntimeException;

     private static native long nativeGetLocalFactory()
       throws IllegalStateException;

     private static native long nativeCancelFactory(long paramLong);

     private static native long nativeCreateMVM(long paramLong, @Nullable String[] paramArrayOfString, @Nullable Writer paramWriter1, @Nullable Writer paramWriter2);

     private static native long nativeGetBlessedMVM(long paramLong);

     private static native long nativeGetCurrentMvmHandle()
       throws IllegalStateException;

     private static native long nativeGetMvmHandleFromMvmID(long paramLong);

     private static native boolean nativeTerminateFactory(long paramLong);

     static native boolean nativeTerminateMVM(long paramLong);

     static native boolean nativeIsMvmTerminated(long paramLong);

     static native boolean nativeBreakInDebugger(long paramLong);

     static native boolean nativeTerminateSession()
       throws MvmTerminatedException;

     static native boolean nativeStartSession(int paramInt1, int paramInt2, boolean paramBoolean, @Nullable String[] paramArrayOfString, int paramInt3)
       throws MvmTerminatedException, IllegalStateException, IllegalArgumentException;

     static native boolean nativeSessionExists()
       throws MvmTerminatedException;

     public static native void nativeSetJavaEngine(boolean paramBoolean);

     private static native long nativeStartMatlabForEngine(String[] paramArrayOfString)
       throws IllegalStateException;

     private static native long nativeConnectMatlabForEngine(String paramString)
       throws IllegalStateException;

     private static native String[] nativeFindMatlabForEngine()
       throws IllegalStateException;

     public static native void nativeReleaseHandleObject(long paramLong);

     public static native boolean nativeVerifyIdentity(String paramString);
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\MvmFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */