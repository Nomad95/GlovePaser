   package temp.com.mathworks.util;

   import java.util.Collection;
   import java.util.Iterator;
   import java.util.NoSuchElementException;

















   public final class LazyFilter<T>
     implements ExtendedIterable<T>
   {
     private final Collection<T> fCollection;
     private final Predicate<T> fPredicate;

     public LazyFilter(Collection<T> paramCollection, Predicate<T> paramPredicate)
     {
       this.fCollection = paramCollection;
       this.fPredicate = paramPredicate;
     }

     public boolean isEmpty()
     {
       return isSizeEqualTo(0);
     }

     public boolean isSizeGreaterThan(int paramInt)
     {
       Iterator localIterator = iterator();
       for (int i = 0; i < paramInt; i++)
       {
         if (!localIterator.hasNext())
           return false;
         localIterator.next();
       }
       return localIterator.hasNext();
     }

     public boolean isSizeEqualTo(int paramInt)
     {
       Iterator localIterator = iterator();
       for (int i = 0; i < paramInt; i++)
       {
         if (!localIterator.hasNext())
           return false;
         localIterator.next();
       }
       return !localIterator.hasNext();
     }

     public boolean isSizeLessThanOrEqualTo(int paramInt)
     {
       Iterator localIterator = iterator();
       for (int i = 0; i < paramInt; i++)
       {
         if (!localIterator.hasNext())
           return true;
         localIterator.next();
       }
       return !localIterator.hasNext();
     }

     public Iterator<T> iterator()
     {
       new Iterator()
       {
         private final Iterator<T> iRealIterator = LazyFilter.this.fCollection.iterator();
         private T iNext;

         public synchronized boolean hasNext()
         {
           if (this.iNext != null) {
             return true;
           }
           while (this.iRealIterator.hasNext())
           {
             this.iNext = this.iRealIterator.next();
             if (LazyFilter.this.fPredicate.accept(this.iNext)) {
               break;
             }
             this.iNext = null;
           }

           return this.iNext != null;
         }

         public synchronized T next()
         {
           if (hasNext())
           {
             Object localObject = this.iNext;
             this.iNext = null;
             return (T)localObject;
           }

           throw new NoSuchElementException();
         }

         public synchronized void remove()
         {
           this.iRealIterator.remove();
         }
       };
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\LazyFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */