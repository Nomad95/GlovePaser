   package temp.com.mathworks.util;

   import java.io.File;
   import java.util.Collections;
   import java.util.Comparator;
   import java.util.Iterator;
   import java.util.LinkedHashSet;
   import java.util.List;
   import java.util.Vector;





   public final class MRUList<T>
     implements Iterable<T>
   {
     private final LinkedHashSet<T> fQueue;
     private final Comparator<T> fComparator;
     private final int fLimit;

     public static Comparator<File> getCanonicalFileComparator()
     {
       new Comparator()
       {
         public int compare(File paramAnonymousFile1, File paramAnonymousFile2)
         {
           return FileUtils.compareCanonicalPaths(paramAnonymousFile1, paramAnonymousFile2);
         }
       };
     }







     public MRUList(int paramInt)
     {
       this(paramInt, null, null);
     }







     public MRUList(int paramInt, Comparator<T> paramComparator)
     {
       this(paramInt, paramComparator, null);
     }









     public MRUList(int paramInt, Comparator<T> paramComparator, T[] paramArrayOfT)
     {
       this.fLimit = paramInt;
       this.fQueue = new LinkedHashSet();
       this.fComparator = paramComparator;

       if (paramArrayOfT != null)
       {
         synchronized (this.fQueue)
         {


           for (int i = paramArrayOfT.length - 1; i >= 0; i--) {
             add(paramArrayOfT[i]);
           }
         }
       }
     }




     public void add(T paramT)
     {
       synchronized (this.fQueue)
       {
         Iterator localIterator;
         if (this.fComparator != null)
         {
           localIterator = this.fQueue.iterator();
           while (localIterator.hasNext())
           {
             Object localObject1 = localIterator.next();
             if (this.fComparator.compare(localObject1, paramT) == 0) {
               localIterator.remove();
             }
           }
         } else {
           this.fQueue.remove(paramT);
         }

         if ((this.fLimit > 0) && (this.fQueue.size() == this.fLimit))
         {
           localIterator = this.fQueue.iterator();
           localIterator.next();
           localIterator.remove();
         }


         this.fQueue.add(paramT);
       }
     }


     public void clear()
     {
       synchronized (this.fQueue)
       {
         this.fQueue.clear();
       }
     }




     public T getLeastRecentlyUsed()
     {
       synchronized (this.fQueue)
       {
         return this.fQueue.size() == 0 ? null : this.fQueue.iterator().next();
       }
     }




     public void remove(T paramT)
     {
       synchronized (this.fQueue)
       {
         this.fQueue.remove(paramT);
       }
     }




     public int size()
     {
       return this.fQueue.size();
     }




     public int getLimit()
     {
       return this.fLimit;
     }


     public boolean contains(T paramT)
     {
       return this.fQueue.contains(paramT);
     }


     public Iterator<T> iterator()
     {
       synchronized (this.fQueue)
       {



         Vector localVector = new Vector(this.fQueue);
         Collections.reverse(localVector);
         return localVector.iterator();
       }
     }





     public T[] toArray(T[] paramArrayOfT)
     {
       synchronized (this.fQueue)
       {


         Vector localVector = new Vector(this.fQueue);
         Collections.reverse(localVector);
         return localVector.toArray(paramArrayOfT);
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\MRUList.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */