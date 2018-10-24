   package temp.com.mathworks.util.collections;

   import java.io.Serializable;
   import java.util.AbstractSet;
   import java.util.Collection;
   import java.util.IdentityHashMap;
   import java.util.Iterator;
   import java.util.Set;





   public class IdentityHashSet<E>
     extends AbstractSet<E>
     implements Set<E>, Cloneable, Serializable
   {
     private transient IdentityHashMap<E, Object> map;
     private static final Object PRESENT = new Object();



     public IdentityHashSet()
     {
       this.map = new IdentityHashMap();
     }







     public IdentityHashSet(Collection<? extends E> paramCollection)
     {
       this.map = new IdentityHashMap(paramCollection.size());
       addAll(paramCollection);
     }






     public Iterator<E> iterator()
     {
       return this.map.keySet().iterator();
     }





     public int size()
     {
       return this.map.size();
     }





     public boolean isEmpty()
     {
       return this.map.isEmpty();
     }






     public boolean contains(Object paramObject)
     {
       return this.map.containsKey(paramObject);
     }






     public boolean add(E paramE)
     {
       return this.map.put(paramE, PRESENT) == null;
     }






     public boolean remove(Object paramObject)
     {
       return this.map.remove(paramObject) == PRESENT;
     }



     public void clear()
     {
       this.map.clear();
     }




     public Object clone()
     {
       try
       {
         IdentityHashSet localIdentityHashSet = (IdentityHashSet)super.clone();
         localIdentityHashSet.map = ((IdentityHashMap)this.map.clone());
         return localIdentityHashSet;
       } catch (CloneNotSupportedException localCloneNotSupportedException) {
         throw new InternalError();
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\collections\IdentityHashSet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */