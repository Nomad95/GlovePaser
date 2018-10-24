   package temp.com.mathworks.util;

   import java.util.ArrayList;
   import java.util.Iterator;
   import java.util.List;
   import java.util.concurrent.Semaphore;










   public final class ObjectPool<T>
   {
     private final List<Entry> fObjects;
     private final ReturnRunnable<T> fGenerator;
     private final Predicate<T> fFilter;
     private final ParameterRunnable<T> fDisposer;
     private Integer fIndefiniteCount;
     private boolean fDisposed;

     public ObjectPool(Class<T> paramClass)
     {
       this(paramClass, null);
     }




     public ObjectPool(Class<T> paramClass, Integer paramInteger)
     {
       this(new ReturnRunnable()
       {
         public T run()
         {
           try
           {
             return (T)ObjectPool.this.newInstance();
           }
           catch (Exception localException)
           {
             throw new IllegalStateException(localException); } } }, null, null, paramInteger);
     }





     public ObjectPool(ReturnRunnable<T> paramReturnRunnable)
     {
       this(paramReturnRunnable, null, null, null);
     }




     public ObjectPool(ReturnRunnable<T> paramReturnRunnable, Integer paramInteger)
     {
       this(paramReturnRunnable, null, null, paramInteger);
     }








     public ObjectPool(ReturnRunnable<T> paramReturnRunnable, Predicate<T> paramPredicate, ParameterRunnable<T> paramParameterRunnable, Integer paramInteger)
     {
       this.fGenerator = paramReturnRunnable;
       this.fObjects = new ArrayList();
       this.fIndefiniteCount = paramInteger;
       this.fDisposer = (paramParameterRunnable != null ? paramParameterRunnable : new ParameterRunnable() { public void run(T paramAnonymousT) {} });
       this.fFilter = (paramPredicate != null ? paramPredicate : new Predicate() { public boolean accept(T paramAnonymousT) { return true; }
       });
     }

     public synchronized void dispose()
     {
       this.fDisposed = true;
       flush();
     }




     public synchronized void flush()
     {
       Iterator localIterator = this.fObjects.iterator();
       while (localIterator.hasNext())
       {
         ((Entry)localIterator.next()).dispose();
         localIterator.remove();
       }
     }




     public void withObject(ParameterRunnable<T> paramParameterRunnable)
     {
       Object localObject1 = acquire();
       try
       {
         paramParameterRunnable.run(localObject1);
       }
       finally
       {
         release(localObject1);
       }
     }


     public synchronized T acquire()
     {
       for (Object localObject = this.fObjects.iterator(); ((Iterator)localObject).hasNext();) { Entry localEntry = (Entry)((Iterator)localObject).next();

         if (localEntry.tryAcquire())
         {
           if (this.fFilter.accept(localEntry.getObject())) {
             return (T)localEntry.getObject();
           }
           localEntry.release();
         }
       }

       localObject = new Entry(this.fGenerator.run());
       if ((!((Entry)localObject).tryAcquire()) || (!this.fFilter.accept(((Entry)localObject).getObject()))) {
         throw new IllegalStateException();
       }
       this.fObjects.add(localObject);
       return (T)((Entry)localObject).getObject();
     }


     public synchronized void release(T paramT)
     {
       for (Iterator localIterator = this.fObjects.iterator(); localIterator.hasNext();) { localEntry = (Entry)localIterator.next();


         if (localEntry.getObject() == paramT)
         {
           localEntry.release();
           break;
         }
       }


       Entry localEntry;

       localIterator = this.fObjects.iterator();
       while (((this.fIndefiniteCount == null) || (this.fObjects.size() > this.fIndefiniteCount.intValue()) || (this.fDisposed)) && (localIterator.hasNext()))
       {
         localEntry = (Entry)localIterator.next();
         if (localEntry.tryAcquire())
         {
           localIterator.remove();
           localEntry.dispose();
         }
       }
     }




     public int size()
     {
       return this.fObjects.size();
     }

     private final class Entry
     {
       private final T fObject;
       private final Semaphore fLock;

       Entry()
       {
         Object localObject;
         this.fObject = localObject;
         this.fLock = new Semaphore(1);
       }

       boolean tryAcquire() { return this.fLock.tryAcquire(); }
       T getObject() { return (T)this.fObject; }
       void release() { this.fLock.release(); }

       void dispose()
       {
         release();
         ObjectPool.this.fDisposer.run(this.fObject);
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\ObjectPool.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */