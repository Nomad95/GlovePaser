   package temp.com.mathworks.util.collections;

   import java.util.Arrays;
   import java.util.Collection;
   import java.util.Iterator;
   import java.util.concurrent.atomic.AtomicReference;












































   public final class CopyOnWriteList<T>
     implements Iterable<T>, Collection<T>
   {
     private static final Object[] EMPTY_ARRAY = new Object[0];
     private AtomicReference<T[]> ref;

     public CopyOnWriteList()
     {
       this.ref = new AtomicReference((Object[])EMPTY_ARRAY);
     }

     public CopyOnWriteList(T paramT) {
       this.ref = new AtomicReference((Object[])new Object[] { paramT });
     }

     public CopyOnWriteList(Collection<? extends T> paramCollection) {
       Object[] arrayOfObject = paramCollection.toArray((Object[])new Object[paramCollection.size()]);
       this.ref = new AtomicReference(arrayOfObject);
     }

     public boolean add(T paramT) {
       Object[] arrayOfObject1;
       Object[] arrayOfObject2;
       do {
         arrayOfObject1 = (Object[])this.ref.get();
         int i = arrayOfObject1.length;
         arrayOfObject2 = (Object[])new Object[i + 1];
         System.arraycopy(arrayOfObject1, 0, arrayOfObject2, 0, i);
         arrayOfObject2[i] = paramT;
       } while (!this.ref.compareAndSet(arrayOfObject1, arrayOfObject2));
       return true;
     }

     public void addIfAbsent(T paramT) {
       Object[] arrayOfObject1;
       Object[] arrayOfObject2;
       do {
         arrayOfObject1 = (Object[])this.ref.get();
         int i = arrayOfObject1.length;
         for (Object localObject : arrayOfObject1) {
           if ((localObject == null) && (paramT == null)) return;
           if ((localObject != null) && (localObject.equals(paramT))) return;
         }
         arrayOfObject2 = (Object[])new Object[i + 1];
         System.arraycopy(arrayOfObject1, 0, arrayOfObject2, 0, i);
         arrayOfObject2[i] = paramT;
       } while (!this.ref.compareAndSet(arrayOfObject1, arrayOfObject2));
     }

     public boolean remove(Object paramObject) {
       Object[] arrayOfObject1;
       boolean bool;
       Object[] arrayOfObject2;
       do {
         arrayOfObject1 = (Object[])this.ref.get();
         int i = arrayOfObject1.length;
         for (int j = 0;
             j < i; j++) {
           Object localObject = arrayOfObject1[j];
           if (((localObject == null) && (paramObject == null)) || ((localObject != null) && (localObject.equals(paramObject)))) break;
         }
         if (j >= i) {
           bool = false;
           break;
         }

         arrayOfObject2 = (Object[])new Object[i - 1];
         System.arraycopy(arrayOfObject1, 0, arrayOfObject2, 0, j);
         if (j < i - 1) System.arraycopy(arrayOfObject1, j + 1, arrayOfObject2, j, i - j - 1);
         bool = true;
       } while (!this.ref.compareAndSet(arrayOfObject1, arrayOfObject2));

       return bool;
     }

     public Object getTimestamp() {
       return this.ref.get();
     }

     public boolean isTimestampChanged(Object paramObject) {
       return this.ref.get() != paramObject;
     }

     public int size() {
       return ((Object[])this.ref.get()).length;
     }

     public T get(int paramInt) {
       return (T)((Object[])this.ref.get())[paramInt];
     }

     public Iterator<T> iterator() {
       return new MyIterator((Object[])this.ref.get());
     }

     public boolean isEmpty() {
       return size() == 0;
     }

     public boolean contains(Object paramObject) {
       for (Object localObject : (Object[])this.ref.get()) {
         if ((localObject == null) && (paramObject == null)) return true;
         if ((localObject != null) && (localObject.equals(paramObject))) return true;
       }
       return false;
     }

     public Object[] toArray() {
       return (Object[])this.ref.get();
     }

     public <T> T[] toArray(T[] paramArrayOfT) {
       Object[] arrayOfObject = (Object[])this.ref.get();
       int i = arrayOfObject.length;

       if (paramArrayOfT.length < i) {
         return (Object[])Arrays.copyOf(arrayOfObject, i, paramArrayOfT.getClass());
       }


       System.arraycopy(arrayOfObject, 0, paramArrayOfT, 0, i);
       if (paramArrayOfT.length > i)
         paramArrayOfT[i] = null;
       return paramArrayOfT;
     }

     public boolean containsAll(Collection<?> paramCollection) {
       throw new UnsupportedOperationException();
     }

     public boolean addAll(Collection<? extends T> paramCollection) {
       throw new UnsupportedOperationException();
     }

     public boolean removeAll(Collection<?> paramCollection) {
       throw new UnsupportedOperationException();
     }

     public boolean retainAll(Collection<?> paramCollection) {
       throw new UnsupportedOperationException();
     }

     public void clear() {
       this.ref = new AtomicReference((Object[])EMPTY_ARRAY);
     }

     private static class MyIterator<T> implements Iterator<T>
     {
       private T[] fArray;
       private int fCurrent;

       MyIterator(T[] paramArrayOfT) {
         this.fArray = paramArrayOfT;
       }

       public boolean hasNext() {
         return this.fCurrent < this.fArray.length;
       }

       public T next()
       {
         Object localObject;
         if (this.fCurrent < this.fArray.length) {
           localObject = this.fArray[(this.fCurrent++)];
         } else {
           localObject = null;
         }
         return (T)localObject;
       }

       public void remove() {
         throw new UnsupportedOperationException();
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\collections\CopyOnWriteList.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */