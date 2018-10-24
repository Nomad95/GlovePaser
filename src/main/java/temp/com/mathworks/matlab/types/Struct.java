   package temp.com.mathworks.matlab.types;

   import com.mathworks.mvm.MvmFactory;
   import java.util.Collection;
   import java.util.Collections;
   import java.util.HashMap;
   import java.util.Map;
   import java.util.Map.Entry;
   import java.util.Set;











   public class Struct
     implements Map<String, Object>
   {
     private HashMap<String, Object> map;
     private Map<String, Object> immutableMap;
     private static final String sInvalidStructFieldName = "Invalid field name for MATLAB struct.";

     public Struct()
     {
       this.map = new HashMap();
       this.immutableMap = Collections.unmodifiableMap(this.map);
     }




     public Struct(Object... paramVarArgs)
     {
       int i = paramVarArgs.length;
       if (i % 2 != 0) {
         throw new IllegalArgumentException();
       }
       this.map = new HashMap();
       for (int j = 0; j < i; j += 2) {
         Object localObject1 = paramVarArgs[j];
         Object localObject2 = paramVarArgs[(j + 1)];
         if ((localObject1 instanceof String)) {
           if (!MvmFactory.nativeVerifyIdentity((String)localObject1)) {
             throw new IllegalArgumentException("Invalid field name for MATLAB struct.");
           }
           this.map.put((String)localObject1, localObject2);
         }
         else {
           throw new IllegalArgumentException("Invalid field name for MATLAB struct.");
         }
       }
       this.immutableMap = Collections.unmodifiableMap(this.map);
     }

     public void clear()
     {
       this.immutableMap.clear();
     }

     public boolean containsKey(Object paramObject)
     {
       return this.immutableMap.containsKey(paramObject);
     }

     public boolean containsValue(Object paramObject)
     {
       return this.immutableMap.containsValue(paramObject);
     }

     public Set<Entry<String, Object>> entrySet()
     {
       return this.immutableMap.entrySet();
     }

     public boolean equals(Object paramObject)
     {
       return this.immutableMap.equals(paramObject);
     }

     public Object get(Object paramObject)
     {
       return this.immutableMap.get(paramObject);
     }

     public int hashCode()
     {
       return this.immutableMap.hashCode();
     }

     public boolean isEmpty()
     {
       return this.immutableMap.isEmpty();
     }

     public Set<String> keySet()
     {
       return this.immutableMap.keySet();
     }


     public Object put(String paramString, Object paramObject)
     {
       return this.immutableMap.put(paramString, paramObject);
     }






     private Object enginePut(String paramString, Object paramObject)
     {
       return this.map.put(paramString, paramObject);
     }


     public void putAll(Map<? extends String, ? extends Object> paramMap)
     {
       this.immutableMap.putAll(paramMap);
     }

     public Object remove(Object paramObject)
     {
       return this.immutableMap.remove(paramObject);
     }

     public int size()
     {
       return this.immutableMap.size();
     }

     public Collection<Object> values()
     {
       return this.immutableMap.values();
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\types\Struct.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */