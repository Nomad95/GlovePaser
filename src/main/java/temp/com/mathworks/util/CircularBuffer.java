   package temp.com.mathworks.util;

   import java.util.ArrayList;
   import java.util.Iterator;
   import java.util.List;
   import java.util.ListIterator;




   public class CircularBuffer<E>
     implements Iterable<E>
   {
     private Object[] fElements;
     private int fStartIndex;
     private int fCount;

     public CircularBuffer(int paramInt)
     {
       this.fElements = new Object[paramInt];
     }






     public void setCapacity(int paramInt)
     {
       if (paramInt == this.fElements.length) return;
       Object[] arrayOfObject = new Object[paramInt];
       if (paramInt < this.fCount) {
         this.fStartIndex += this.fCount - paramInt;
         this.fCount = paramInt;
         if (this.fStartIndex > this.fElements.length)
           this.fStartIndex -= this.fElements.length;
       }
       int i = 0;
       int j = this.fCount;
       if (this.fStartIndex + this.fCount > this.fElements.length) {
         j = this.fElements.length - this.fStartIndex;
         System.arraycopy(this.fElements, this.fStartIndex, arrayOfObject, 0, j);
         this.fStartIndex = 0;
         i = j;
         j = this.fCount - j;
       }
       System.arraycopy(this.fElements, this.fStartIndex, arrayOfObject, i, j);
       this.fElements = arrayOfObject;
       this.fStartIndex = 0;
     }



     public int capacity()
     {
       return this.fElements.length;
     }



     public int size()
     {
       return this.fCount;
     }

     public boolean isEmpty() { return this.fCount == 0; }




     public void add(E paramE)
     {
       int i = this.fStartIndex + this.fCount;
       if (i >= this.fElements.length)
         i -= this.fElements.length;
       this.fElements[i] = paramE;

       if (this.fCount == this.fElements.length) {
         this.fStartIndex += 1;
         if (this.fStartIndex == this.fElements.length)
           this.fStartIndex = 0;
       } else {
         this.fCount += 1;
       }
     }




     public E get(int paramInt)
     {
       return (E)this.fElements[incrementIndex(this.fStartIndex, paramInt)];
     }



     public void insert(List<E> paramList, int[] paramArrayOfInt)
     {
       if (paramArrayOfInt.length > 0) {
         ListIterator localListIterator = paramList.listIterator(paramList.size());
         int i = incrementIndex(this.fStartIndex, this.fCount - 1);
         int j = incrementIndex(i, paramArrayOfInt.length);
         for (int k = paramArrayOfInt.length - 1; k >= 0; k--) {
           int m = incrementIndex(this.fStartIndex, paramArrayOfInt[k]);
           for (int n = 0; (j != m) && (n < this.fElements.length); n++) {
             this.fElements[j] = this.fElements[i];
             j = decrementIndex(j);
             i = decrementIndex(i);
           }
           this.fElements[j] = localListIterator.previous();
           j = decrementIndex(j);
         }
         this.fCount += paramArrayOfInt.length;
         if (this.fCount > this.fElements.length) {
           this.fStartIndex = incrementIndex(this.fStartIndex, this.fCount - this.fElements.length);
           this.fCount = this.fElements.length;
         }
       }
     }



     public List<E> remove(int[] paramArrayOfInt)
     {
       ArrayList localArrayList = new ArrayList();
       if (paramArrayOfInt.length > 0) {
         int i = paramArrayOfInt[0];
         int j = incrementIndex(this.fStartIndex, i);
         int k = j;
         for (int m = 1; m <= paramArrayOfInt.length; m++) {
           localArrayList.add(this.fElements[k]);
           k = incrementIndex(k);
           int n = (m < paramArrayOfInt.length ? paramArrayOfInt[m] : this.fCount) - i - 1;
           for (int i1 = 0; i1 < n; i1++) {
             this.fElements[j] = this.fElements[k];
             j = incrementIndex(j);
             k = incrementIndex(k);
           }
           i += n + 1;
         }
         this.fCount -= paramArrayOfInt.length;
       }
       return localArrayList;
     }



     public void clear()
     {
       this.fStartIndex = 0;
       this.fCount = 0;
     }

     public Iterator<E> iterator() {
       return new LocalIterator(null);
     }

     public ListIterator<E> listIterator() {
       return new LocalIterator(null);
     }

     public ListIterator<E> listIterator(int paramInt) {
       return new LocalIterator(paramInt, null);
     }

     private class LocalIterator implements ListIterator<E>
     {
       private int iIndex;

       private LocalIterator() {
         this.iIndex = 0;
       }

       private LocalIterator(int paramInt) {
         this.iIndex = paramInt;
       }

       public boolean hasNext() {
         return this.iIndex < CircularBuffer.this.fCount;
       }

       public E next() {
         Object localObject = CircularBuffer.this.get(this.iIndex);
         this.iIndex += 1;
         return (E)localObject;
       }

       public boolean hasPrevious() {
         return this.iIndex > 0;
       }

       public E previous() {
         Object localObject = CircularBuffer.this.get(this.iIndex);
         this.iIndex -= 1;
         return (E)localObject;
       }

       public int nextIndex() {
         return this.iIndex + 1;
       }

       public int previousIndex() {
         return this.iIndex - 1;
       }

       public void remove() {
         throw new UnsupportedOperationException();
       }

       public void set(E paramE) {
         throw new UnsupportedOperationException();
       }

       public void add(E paramE) {
         throw new UnsupportedOperationException();
       }
     }

     private int incrementIndex(int paramInt) {
       return incrementIndex(paramInt, 1);
     }

     private int decrementIndex(int paramInt) {
       return incrementIndex(paramInt, -1);
     }

     private int incrementIndex(int paramInt1, int paramInt2) {
       int i = paramInt1 + paramInt2;
       if (i >= this.fElements.length)
         i -= this.fElements.length;
       if (i < 0)
         i += this.fElements.length;
       return i;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\CircularBuffer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */