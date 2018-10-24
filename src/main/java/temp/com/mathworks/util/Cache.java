   package temp.com.mathworks.util;

   import java.io.File;
   import java.io.IOException;
   import java.util.HashMap;
   import java.util.Iterator;
   import java.util.LinkedList;
   import java.util.List;
   import java.util.Map;
   import java.util.Queue;
   import java.util.Timer;
   import java.util.TimerTask;






   public class Cache<K, V>
   {
     private final Map<K, Entry> fData;
     private final Loader<K, V> fLoader;
     private final MRUList<Entry> fRecentlyUsed;
     private final int fElementLimit;
     private final Object fChangeLock;

     private Cache()
     {
       this.fLoader = null;
       this.fRecentlyUsed = null;
       this.fElementLimit = Integer.MAX_VALUE;
       this.fChangeLock = null;
       this.fData = null;
     }



















     public static <X, K, V> Cache<K, V> createIndirect(final Loader<K, V> paramLoader, final Converter<K, X> paramConverter, int paramInt)
     {
       new Cache(paramInt)
       {

         private Cache<X, V> fRealCache = new Cache(null, this.val$argElementLimit);

         public V get(final K paramAnonymousK) throws IOException
         {
           (V)this.fRealCache.get(paramConverter.convert(paramAnonymousK), new Loader()
           {
             public V load(X paramAnonymous2X) throws IOException
             {
               return (V)Cache.1.this.val$argLoader.load(paramAnonymousK);
             }
           });
         }

         public void flush()
         {
           this.fRealCache.flush();
         }

         public void flush(K paramAnonymousK)
         {
           this.fRealCache.flush(paramConverter.convert(paramAnonymousK));
         }

         public boolean isPresent(K paramAnonymousK)
         {
           return this.fRealCache.isPresent(paramConverter.convert(paramAnonymousK));
         }

         protected Entry getIfPresent(K paramAnonymousK)
         {
           Entry localEntry = this.fRealCache.getIfPresent(paramConverter.convert(paramAnonymousK));
           if (localEntry == null) {
             return null;
           }
           return new Entry(this, paramAnonymousK, localEntry.getValue());
         }

         public int getElementLimit()
         {
           return this.val$argElementLimit;
         }
       };
     }





     public Cache(Loader<K, V> paramLoader, int paramInt)
     {
       this.fLoader = paramLoader;
       this.fElementLimit = paramInt;
       this.fChangeLock = new Object();
       this.fData = new HashMap();
       this.fRecentlyUsed = new MRUList(paramInt);
     }













     public static <V> Cache<File, V> createFileCache(final FileCacheStyle paramFileCacheStyle, Loader<File, V> paramLoader, int paramInt)
     {
       new Cache(paramLoader, paramInt)
       {
         public void flush(File paramAnonymousFile)
         {
           if (paramFileCacheStyle == FileCacheStyle.FLUSH_PARENTS_RECURSIVELY)
           {
             File localFile = paramAnonymousFile;
             while (localFile != null)
             {
               super.flush(localFile);
               localFile = localFile.getParentFile();
             }
           }
           else if (paramFileCacheStyle == FileCacheStyle.EXPLICIT_FLUSH)
           {
             super.flush(paramAnonymousFile);
           }
           else
           {
             throw new IllegalArgumentException("createFileCache doesn't handle " + paramFileCacheStyle);
           }
         }

         public void reloadIfPresent(File paramAnonymousFile) throws IOException
         {
           if (paramFileCacheStyle == FileCacheStyle.FLUSH_PARENTS_RECURSIVELY)
           {
             File localFile = paramAnonymousFile;
             while (localFile != null)
             {
               super.reloadIfPresent(localFile);
               localFile = localFile.getParentFile();
             }
           }
           else if (paramFileCacheStyle == FileCacheStyle.EXPLICIT_FLUSH)
           {
             super.reloadIfPresent(paramAnonymousFile);
           }
           else
           {
             throw new IllegalArgumentException("reloadIfPresent doesn't handle " + paramFileCacheStyle);
           }
         }
       };
     }


















     public static <K, V> Cache<K, V> makeAsynchronous(final Cache<K, V> paramCache, final int paramInt, final V paramV, final ParameterRunnable<K> paramParameterRunnable, final ParameterRunnable<IOException> paramParameterRunnable1)
     {
       new Wrapper(paramCache)
       {
         private final Queue<K> iLoadQueue = new LinkedList();
         private Timer iTimer = new Timer("Cache Timer", true);
         private TimerTask iNextBatch;

         public V get(K paramAnonymousK)
         {
           Entry localEntry = paramCache.getIfPresent(paramAnonymousK);
           if (localEntry != null) {
             return (V)localEntry.getValue();
           }

           synchronized (this.iLoadQueue)
           {
             this.iLoadQueue.remove(paramAnonymousK);
             this.iLoadQueue.offer(paramAnonymousK);


             while (this.iLoadQueue.size() > paramCache.getElementLimit()) {
               this.iLoadQueue.poll();
             }


             if (this.iNextBatch == null)
             {
               this.iNextBatch = new TimerTask()
               {
                 public void run()
                 {
                   LinkedList localLinkedList;

                   synchronized (Cache.3.this.iLoadQueue)
                   {
                     localLinkedList = new LinkedList(Cache.3.this.iLoadQueue);
                     Cache.3.this.iLoadQueue.clear();
                     Cache.3.this.iNextBatch = null;
                   }




                   for (??? = localLinkedList.iterator(); ((Iterator)???).hasNext();) { Object localObject2 = ((Iterator)???).next();


                     try
                     {
                       Thread.yield();
                       Cache.3.this.val$argWrappedCache.get(localObject2);

                       if (Cache.3.this.val$argCallback != null)
                       {
                         Cache.3.this.val$argCallback.run(localObject2);
                       }
                     }
                     catch (IOException localIOException)
                     {
                       if (Cache.3.this.val$argExceptionHandler != null) {
                         Cache.3.this.val$argExceptionHandler.run(localIOException);
                       }
                     }
                   }
                 }
               };
               this.iTimer.schedule(this.iNextBatch, paramInt);
             }
           }

           return (V)paramV;
         }
       };
     }






     public static enum FileCacheStyle
     {
       EXPLICIT_FLUSH,


       FLUSH_PARENTS_RECURSIVELY;







       private FileCacheStyle() {}
     }







     public void flush()
     {
       synchronized (this.fChangeLock)
       {
         this.fData.clear();
         this.fRecentlyUsed.clear();
       }
     }





     public void flush(K paramK)
     {
       synchronized (this.fChangeLock)
       {
         Entry localEntry = (Entry)this.fData.remove(paramK);
         if (localEntry != null) {
           this.fRecentlyUsed.remove(localEntry);
         }
       }
     }



     public void reloadIfPresent(K paramK)
       throws IOException
     {
       synchronized (this.fChangeLock)
       {
         if (isPresent(paramK))
         {
           flush(paramK);
           get(paramK);
         }
       }
     }

     public void set(K paramK, V paramV)
     {
       synchronized (this.fChangeLock)
       {
         this.fData.put(paramK, new Entry(paramK, paramV));
       }
     }






     public V get(K paramK)
       throws IOException
     {
       return (V)get(paramK, this.fLoader);
     }





     private V get(K paramK, Loader<K, V> paramLoader)
       throws IOException
     {
       Entry localEntry;



       synchronized (this.fChangeLock)
       {
         localEntry = (Entry)this.fData.get(paramK);
       }


       if (localEntry == null)
       {
         localEntry = new Entry(paramK, paramLoader.load(paramK));

         synchronized (this.fChangeLock)
         {

           if (this.fData.size() == this.fElementLimit) {
             this.fData.remove(((Entry)this.fRecentlyUsed.getLeastRecentlyUsed()).getKey());
           }
           this.fData.put(paramK, localEntry);
         }
       }

       this.fRecentlyUsed.add(localEntry);
       return (V)localEntry.getValue();
     }


     public boolean isPresent(K paramK)
     {
       // Byte code:
       //   0: aload_0
       //   1: getfield 8	com/mathworks/util/Cache:fChangeLock	Ljava/lang/Object;
       //   4: dup
       //   5: astore_2
       //   6: monitorenter
       //   7: aload_0
       //   8: getfield 9	com/mathworks/util/Cache:fData	Ljava/util/Map;
       //   11: invokeinterface 38 1 0
       //   16: aload_1
       //   17: invokeinterface 39 2 0
       //   22: aload_2
       //   23: monitorexit
       //   24: ireturn
       //   25: astore_3
       //   26: aload_2
       //   27: monitorexit
       //   28: aload_3
       //   29: athrow
       // Line number table:
       //   Java source line #394	-> byte code offset #0
       //   Java source line #396	-> byte code offset #7
       //   Java source line #397	-> byte code offset #25
       // Local variable table:
       //   start	length	slot	name	signature
       //   0	30	0	this	Cache
       //   0	30	1	paramK	K
       //   5	22	2	Ljava/lang/Object;	Object
       //   25	4	3	localObject1	Object
       // Exception table:
       //   from	to	target	type
       //   7	24	25	finally
       //   25	28	25	finally
     }

     protected Entry getIfPresent(K paramK)
     {
       synchronized (this.fChangeLock)
       {
         if (isPresent(paramK)) {
           return (Entry)this.fData.get(paramK);
         }
         return null;
       }
     }






     public int getElementLimit() { return this.fElementLimit; }

     public static abstract interface Loader<K, V> {
       public abstract V load(K paramK) throws IOException;
     }

     protected class Entry {
       private K iKey;
       private V iValue;

       Entry(V paramV) { this.iKey = paramV;
         Object localObject; this.iValue = localObject;
       }

       K getKey()
       {
         return (K)this.iKey;
       }

       V getValue()
       {
         return (V)this.iValue;
       }

       public int hashCode()
       {
         return this.iKey.hashCode();
       }

       public boolean equals(Object paramObject)
       {
         return ((paramObject instanceof Entry)) && (((Entry)paramObject).iKey.equals(this.iKey));
       }
     }

     private static class Wrapper<K, V> extends Cache<K, V>
     {
       private final Cache<K, V> fWrappedCache;

       public Wrapper(Cache<K, V> paramCache) {
         super();
         this.fWrappedCache = paramCache;
       }

       public void flush()
       {
         this.fWrappedCache.flush();
       }

       public void flush(K paramK)
       {
         this.fWrappedCache.flush(paramK);
       }

       public boolean isPresent(K paramK)
       {
         return this.fWrappedCache.isPresent(paramK);
       }

       protected Entry getIfPresent(K paramK)
       {
         return this.fWrappedCache.getIfPresent(paramK);
       }

       public int getElementLimit()
       {
         return this.fWrappedCache.getElementLimit();
       }

       public void reloadIfPresent(K paramK) throws IOException
       {
         this.fWrappedCache.reloadIfPresent(paramK);
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\Cache.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */