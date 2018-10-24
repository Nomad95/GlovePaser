   package temp.com.mathworks.util;

   import java.util.ArrayList;
   import java.util.Collections;
   import java.util.Comparator;
   import java.util.HashMap;
   import java.util.Iterator;
   import java.util.List;
   import java.util.Map;
   import java.util.Map.Entry;
   import java.util.Set;
   import java.util.TreeMap;
   import org.apache.commons.lang.StringUtils;
   import org.apache.commons.lang.builder.HashCodeBuilder;
   import org.apache.commons.lang.builder.ToStringBuilder;





















   public class CategoricalVariable<T>
   {
     private final Map<T, String> fCategories;
     private List<T> fInvisibleCategoryValues = new ArrayList();




     private Map<String, String> fLocalizedCategoryLookup;




     private final ICategoriesUpdater catUpdater;




     private final Comparator<T> categoryComparator;




     private volatile T value;



     private volatile String name;




     public CategoricalVariable(Map<T, String> paramMap, Map<String, String> paramMap1, Comparator<T> paramComparator)
     {
       this(paramMap, paramComparator);
       this.fLocalizedCategoryLookup = paramMap1;
     }

     public Map<String, String> getLocalizedCategoryMap() {
       return this.fLocalizedCategoryLookup;
     }







     public CategoricalVariable(Map<T, String> paramMap, Comparator<T> paramComparator)
     {
       this(paramMap, (ICategoriesUpdater)null, paramComparator);

       if ((paramMap == null) || (paramMap.isEmpty())) {
         throw new IllegalArgumentException("Category set must not be empty");
       }
     }








     public CategoricalVariable(ICategoriesUpdater<T> paramICategoriesUpdater, Comparator<T> paramComparator)
     {
       this(null, paramICategoriesUpdater, paramComparator);

       if (paramICategoriesUpdater == null) {
         throw new IllegalArgumentException("Category updater must not be null");
       }
     }

     private CategoricalVariable(Map<T, String> paramMap, ICategoriesUpdater<T> paramICategoriesUpdater, Comparator<T> paramComparator)
     {
       this.catUpdater = paramICategoriesUpdater;
       this.categoryComparator = paramComparator;
       if (paramComparator != null) {
         this.fCategories = Collections.synchronizedSortedMap(new TreeMap(paramComparator));
       } else
         this.fCategories = Collections.synchronizedMap(new HashMap());
       setCategories(paramMap);
       assignFirstElement();
     }
















     private static CategoricalVariable<Integer> newInstance(int paramInt, int[] paramArrayOfInt1, int[] paramArrayOfInt2, String[] paramArrayOfString1, String[] paramArrayOfString2)
     {
       assert (paramArrayOfInt1 != null);
       assert (paramArrayOfString1 != null);
       assert (paramArrayOfInt1.length == paramArrayOfString1.length);

       ArrayList localArrayList1 = new ArrayList(paramArrayOfInt1.length);
       Object localObject = paramArrayOfInt1;int i = localObject.length; for (int j = 0; j < i; j++) { Integer localInteger = Integer.valueOf(localObject[j]);
         localArrayList1.add(localInteger);
       }

       localObject = new Comparator()
       {
         public int compare(Integer paramAnonymousInteger1, Integer paramAnonymousInteger2)
         {
           Integer localInteger1 = Integer.valueOf(this.val$catList.indexOf(paramAnonymousInteger1));
           assert (localInteger1.intValue() != -1) : ("" + paramAnonymousInteger1 + " is not a valid category.");

           Integer localInteger2 = Integer.valueOf(this.val$catList.indexOf(paramAnonymousInteger2));
           assert (localInteger2.intValue() != -1) : ("" + paramAnonymousInteger2 + " is not a valid category.");

           return localInteger1.compareTo(localInteger2);
         }


       };
       HashMap localHashMap1 = new HashMap(paramArrayOfInt1.length);
       HashMap localHashMap2 = new HashMap(paramArrayOfInt1.length);
       for (int k = 0; k < paramArrayOfInt1.length; k++) {
         localHashMap1.put(Integer.valueOf(paramArrayOfInt1[k]), paramArrayOfString1[k]);
         localHashMap2.put(paramArrayOfString1[k], paramArrayOfString2[k]);
       }


       CategoricalVariable localCategoricalVariable = new CategoricalVariable(localHashMap1, localHashMap2, (Comparator)localObject);
       localCategoricalVariable.setValue(Integer.valueOf(paramInt));


       if (paramArrayOfInt2 != null) {
         ArrayList localArrayList2 = new ArrayList();
         for (int m = 0; m < paramArrayOfInt2.length; m++) {
           localArrayList2.add(Integer.valueOf(paramArrayOfInt2[m]));
         }
         localCategoricalVariable.setInvisibleCategories(localArrayList2);
       }

       return localCategoricalVariable;
     }




     public CategoricalVariable(CategoricalVariable<T> paramCategoricalVariable)
     {
       if (paramCategoricalVariable == null) {
         throw new IllegalArgumentException("Input argument must not be null");
       }
       this.name = paramCategoricalVariable.name;
       this.catUpdater = paramCategoricalVariable.catUpdater;
       this.categoryComparator = paramCategoricalVariable.categoryComparator;
       this.fLocalizedCategoryLookup = paramCategoricalVariable.fLocalizedCategoryLookup;


       if (this.categoryComparator != null) {
         this.fCategories = Collections.synchronizedSortedMap(new TreeMap(this.categoryComparator));
       } else {
         this.fCategories = Collections.synchronizedMap(new HashMap());
       }
       this.fCategories.putAll(paramCategoricalVariable.fCategories);
       this.value = paramCategoricalVariable.value;
     }





     public final synchronized T getValue()
     {
       return (T)this.value;
     }





     public synchronized void setValue(T paramT)
     {
       assert (isValidValue(paramT)) : ("Value is not a valid category: " + paramT);
       this.value = paramT;
     }










     public final synchronized boolean isValidValue(T paramT)
     {
       for (Object localObject : this.fCategories.keySet())
       {
         if (localObject.equals(paramT)) {
           return true;
         }
       }
       return false;
     }


     public synchronized boolean equals(Object paramObject)
     {
       if (!(paramObject instanceof CategoricalVariable)) {
         return false;
       }
       if (paramObject == this) {
         return true;
       }
       CategoricalVariable localCategoricalVariable = (CategoricalVariable)paramObject;
       if (localCategoricalVariable.value == this.value) {
         return true;
       }
       if (this.value == null) {
         return false;
       }


       return (localCategoricalVariable.value.equals(this.value)) && (localCategoricalVariable.fCategories.keySet().equals(this.fCategories.keySet()));
     }







     public static boolean equals(CategoricalVariable paramCategoricalVariable1, CategoricalVariable paramCategoricalVariable2)
     {
       if (paramCategoricalVariable1 == paramCategoricalVariable2) {
         return true;
       }
       if ((paramCategoricalVariable1 != null) && (paramCategoricalVariable2 != null))
       {
         return paramCategoricalVariable1.equals(paramCategoricalVariable2);
       }

       return false;
     }


     public synchronized int hashCode()
     {
       return new HashCodeBuilder(17, 37).append(this.value).append(this.fCategories.keySet()).toHashCode();
     }





     public final Map<T, String> getCategories()
     {
       HashMap localHashMap = new HashMap(this.fCategories);
       removeInvisibleCategories(localHashMap);
       return Collections.unmodifiableMap(localHashMap);
     }





     public final Map<T, String> getLocalizedCategories()
     {
       if (this.fLocalizedCategoryLookup == null)
         return getCategories();
       HashMap localHashMap = new HashMap();
       for (Object localObject : this.fCategories.keySet()) {
         String str = (String)this.fCategories.get(localObject);
         if ((str != null) && (!str.isEmpty()))
           localHashMap.put(localObject, this.fLocalizedCategoryLookup.get(str));
       }
       removeInvisibleCategories(localHashMap);
       return localHashMap;
     }

     private void removeInvisibleCategories(Map<T, String> paramMap) {
       if (this.fInvisibleCategoryValues != null) {
         for (Object localObject : this.fInvisibleCategoryValues) {
           paramMap.remove(localObject);
         }
       }
     }

     private void setCategories(Map<T, String> paramMap)
     {
       if (((paramMap == null) || (paramMap.isEmpty())) && (this.catUpdater != null))
       {
         paramMap = this.catUpdater.updateCategories(this.fCategories);


         if ((paramMap == null) || (paramMap.isEmpty())) {
           return;
         }
       }
       this.fCategories.clear();
       this.fCategories.putAll(paramMap);
       setCategoryLabels(this.fCategories);
     }

     private void setInvisibleCategories(List<T> paramList) {
       if (paramList == null) {
         this.fInvisibleCategoryValues = new ArrayList();
       } else {
         this.fInvisibleCategoryValues = paramList;
       }
     }

     private void assignFirstElement()
     {
       Iterator localIterator = this.fCategories.keySet().iterator(); if (localIterator.hasNext()) { Object localObject = localIterator.next();

         setValue(localObject);
       }
     }








     private void setCategoryLabels(Map<T, String> paramMap)
     {
       if (paramMap == null) {
         return;
       }
       for (Object localObject : this.fCategories.keySet())
       {
         assert (localObject != null);
         String str = (String)paramMap.get(localObject);


         if (str != null) {
           this.fCategories.put(localObject, str);
         } else {
           this.fCategories.put(localObject, localObject.toString());
         }
       }
     }




     public final String getLabel()
     {
       return (String)this.fCategories.get(getValue());
     }







     public final String getLocalizedLabel()
     {
       String str = (String)this.fCategories.get(getValue());
       if ((this.fLocalizedCategoryLookup == null) || (str == null) || (str.length() == 0))
         return str;
       return (String)this.fLocalizedCategoryLookup.get(str);
     }

     public List<T> getInvisibleValues() {
       return this.fInvisibleCategoryValues;
     }





     public synchronized boolean updateCategories()
     {
       if (this.catUpdater == null) {
         return false;
       }
       Map localMap = this.catUpdater.updateCategories(Collections.unmodifiableMap(this.fCategories));
       if ((localMap == null) || (localMap.isEmpty())) {
         return false;
       }
       setCategories(localMap);
       if (!this.fCategories.containsKey(getValue())) {
         assignFirstElement();
       }
       return true;
     }









     public synchronized T findValueByLocalizedLabel(String paramString)
     {
       if (this.fLocalizedCategoryLookup != null) {
         for (String str : this.fLocalizedCategoryLookup.keySet()) {
           if (paramString.equals(this.fLocalizedCategoryLookup.get(str))) {
             return (T)findValueByLabel(str);
           }
         }
       }
       return (T)findValueByLabel(paramString);
     }

     public boolean isValueHidden()
     {
       return this.fInvisibleCategoryValues.contains(getValue());
     }

     public boolean isLocalized() {
       return this.fLocalizedCategoryLookup != null;
     }










     public synchronized T findValueByLabel(String paramString)
     {
       for (Entry localEntry : this.fCategories.entrySet())
       {
         if (StringUtils.equals(paramString, (String)localEntry.getValue())) {
           return (T)localEntry.getKey();
         }
       }
       return null;
     }





     public final void setName(String paramString)
     {
       this.name = paramString;
     }





     public final String getName()
     {
       return this.name;
     }


     public String toString()
     {
       return new ToStringBuilder(this).append("name", this.name).append("value", this.value).append("categories", this.fCategories).append("catUpdater", this.catUpdater).append("categoryComparator", this.categoryComparator).toString();
     }

     public static abstract interface ICategoriesUpdater<T>
     {
       public abstract Map<T, String> updateCategories(Map<T, String> paramMap);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\CategoricalVariable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */