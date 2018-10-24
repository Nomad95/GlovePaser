   package temp.com.mathworks.util;

   import java.util.ArrayList;
   import java.util.Collection;
   import java.util.HashMap;
   import java.util.HashSet;
   import java.util.Map;
   import java.util.Map.Entry;
   import java.util.Set;













   public class CollectionMap<K, V>
   {
     private final Map<K, Collection<V>> fMap;
     private final Class<? extends Map> fMapClass;
     private final Class<? extends Collection> fCollectionClass;

     public CollectionMap()
     {
       this(HashMap.class, ArrayList.class);
     }





     public CollectionMap(Class<? extends Map> paramClass, Class<? extends Collection> paramClass1)
     {
       this.fCollectionClass = paramClass1;
       this.fMapClass = paramClass;
       this.fMap = createMap();
     }



     public CollectionMap(CollectionMap<K, V> paramCollectionMap)
     {
       this.fMapClass = paramCollectionMap.fMapClass;
       this.fCollectionClass = paramCollectionMap.fCollectionClass;
       this.fMap = createMap();
       for (Entry localEntry : paramCollectionMap.entrySet())
       {
         Collection localCollection = createCollection();
         localCollection.addAll((Collection)localEntry.getValue());
         this.fMap.put(localEntry.getKey(), localCollection);
       }
     }


     public synchronized Collection<V> get(K paramK)
     {
       Collection localCollection1 = (Collection)this.fMap.get(paramK);
       Collection localCollection2 = createCollection();
       if (localCollection1 != null)
         localCollection2.addAll(localCollection1);
       return localCollection2;
     }


     public synchronized boolean add(K paramK, V paramV)
     {
       Collection localCollection = (Collection)this.fMap.get(paramK);
       if (localCollection == null)
       {
         localCollection = createCollection();
         this.fMap.put(paramK, localCollection);
       }
       boolean bool = localCollection.add(paramV);
       if (localCollection.isEmpty())
         this.fMap.remove(paramK);
       return bool;
     }




     public synchronized Collection<V> remove(K paramK)
     {
       Collection localCollection = (Collection)this.fMap.remove(paramK);
       return localCollection == null ? createCollection() : localCollection;
     }


     public synchronized boolean remove(K paramK, V paramV)
     {
       Collection localCollection = (Collection)this.fMap.get(paramK);
       if (localCollection == null)
         return false;
       boolean bool = localCollection.remove(paramV);
       if (localCollection.isEmpty())
         this.fMap.remove(paramK);
       return bool;
     }


     public synchronized int size()
     {
       return this.fMap.size();
     }


     public synchronized Set<Entry<K, Collection<V>>> entrySet()
     {
       return this.fMap.entrySet();
     }


     public synchronized Set<K> keySet()
     {
       return this.fMap.keySet();
     }


     private Collection<V> createCollection()
     {
       try
       {
         return (Collection)this.fCollectionClass.newInstance();
       }
       catch (Exception localException)
       {
         throw new IllegalStateException(localException);
       }
     }


     private Map<K, Collection<V>> createMap()
     {
       try
       {
         return (Map)this.fMapClass.newInstance();
       }
       catch (Exception localException)
       {
         throw new IllegalStateException(localException);
       }
     }




     public static <K, V> CollectionMap<K, V> invert(Map<V, K> paramMap)
     {
       CollectionMap localCollectionMap = new CollectionMap(HashMap.class, HashSet.class);
       for (Entry localEntry : paramMap.entrySet())
         localCollectionMap.add(localEntry.getValue(), localEntry.getKey());
       return localCollectionMap;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\CollectionMap.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */