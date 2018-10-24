   package temp.com.mathworks.mvm.exec;

   import com.mathworks.mvm.MvmImpl;
   import java.io.ByteArrayInputStream;
   import java.io.ByteArrayOutputStream;
   import java.io.IOException;
   import java.io.InvalidClassException;
   import java.io.NotSerializableException;
   import java.io.ObjectInputStream;
   import java.io.ObjectOutputStream;
   import java.io.OptionalDataException;
   import java.io.StreamCorruptedException;
   import java.util.concurrent.CancellationException;
   import java.util.concurrent.atomic.AtomicReference;
   import org.jetbrains.annotations.Nullable;






   final class NativeFutureResult<V>
   {
     private long fNativeID;
     private Throwable fJavaCause;

     static
     {
       MvmImpl.loadLibrary();
     }



     private AtomicReference<Runnable> fRunnable = new AtomicReference();



     private volatile boolean fDoneCalled;



     private NativeFutureResult(long paramLong)
     {
       assert (paramLong != 0L);
       this.fNativeID = paramLong;
     }












     V get()
       throws IllegalStateException, CancellationException, MvmExecutionException
     {
       return (V)nativeGet(this.fNativeID);
     }

     boolean cancel(boolean paramBoolean) { return nativeCancel(this.fNativeID, paramBoolean); }






     boolean waitForCompletion(double paramDouble)
       throws IllegalStateException
     {
       return nativeWaitForCompletion(this.fNativeID, paramDouble);
     }




     boolean isCancelled()
     {
       return nativeIsCancelled(this.fNativeID);
     }


     boolean isDone()
     {
       return nativeIsDone(this.fNativeID);
     }





     void runWhenDone(@Nullable final Runnable paramRunnable)
     {
       if (paramRunnable == null) { return;
       }
       final Runnable localRunnable = (Runnable)this.fRunnable.getAndSet(null);

       Runnable local1 = localRunnable == null ? paramRunnable : new Runnable()
       {
         public void run() {
           localRunnable.run();
           paramRunnable.run();
         }
       };

       if (!this.fRunnable.compareAndSet(null, local1))
       {


         runWhenDone(local1);


       }
       else if (this.fDoneCalled) { callRunnable();
       }
     }













     private void done()
     {
       assert (!this.fDoneCalled);
       this.fDoneCalled = true;
       callRunnable();
     }

     private void callRunnable()
     {
       assert (this.fDoneCalled);
       Runnable localRunnable = (Runnable)this.fRunnable.getAndSet(null);
       if (localRunnable != null) {
         localRunnable.run();
       }
     }




     private void setJavaCause(Throwable paramThrowable)
     {
       this.fJavaCause = paramThrowable;
     }



     private Throwable getJavaCause()
     {
       return this.fJavaCause;
     }


     protected void finalize()
       throws Throwable
     {
       try
       {
         if (this.fNativeID != 0L) nativeReleaseFutureResult(this.fNativeID);
         this.fNativeID = 0L;
       } finally {
         super.finalize();
       }
     }







     private static byte[] serialize(Object paramObject)
       throws NotSerializableException, InvalidClassException
     {
       ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
       try { ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(localByteArrayOutputStream);Object localObject1 = null;
         try { localObjectOutputStream.writeObject(paramObject);
         }
         catch (Throwable localThrowable2)
         {
           localObject1 = localThrowable2;throw localThrowable2;
         } finally {
           if (localObjectOutputStream != null) if (localObject1 != null) try { localObjectOutputStream.close(); } catch (Throwable localThrowable3) { ((Throwable)localObject1).addSuppressed(localThrowable3); } else localObjectOutputStream.close();
         } } catch (IOException localIOException) { if (!$assertionsDisabled) throw new AssertionError();
       }
       return localByteArrayOutputStream.toByteArray();
     }











     private static Object deserialize(byte[] paramArrayOfByte)
       throws ClassNotFoundException, InvalidClassException, StreamCorruptedException, OptionalDataException
     {
       ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
       Object localObject1 = null;
       try { ObjectInputStream localObjectInputStream = new ObjectInputStream(localByteArrayInputStream);Object localObject2 = null;
         try { localObject1 = localObjectInputStream.readObject();
         }
         catch (Throwable localThrowable2)
         {
           localObject2 = localThrowable2;throw localThrowable2;
         } finally {
           if (localObjectInputStream != null) if (localObject2 != null) try { localObjectInputStream.close(); } catch (Throwable localThrowable3) { ((Throwable)localObject2).addSuppressed(localThrowable3); } else localObjectInputStream.close();
         } } catch (IOException localIOException) { if (!$assertionsDisabled) throw new AssertionError();
       }
       return localObject1;
     }

     private native V nativeGet(long paramLong);

     private native boolean nativeCancel(long paramLong, boolean paramBoolean);

     private native boolean nativeWaitForCompletion(long paramLong, double paramDouble);

     private native boolean nativeIsCancelled(long paramLong);

     private native boolean nativeIsDone(long paramLong);

     private native void nativeReleaseFutureResult(long paramLong);
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\exec\NativeFutureResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */