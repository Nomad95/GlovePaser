   package temp.com.mathworks.mvm.eventmgr;

   import java.util.Arrays;
   import java.util.HashMap;
   import java.util.Iterator;
   import java.util.List;
   import java.util.Set;
   import java.util.Vector;







   public class MvmDynamicEvent
     implements MvmEvent
   {
     private static final String[] reserved = { "count", "item_version" };
     private final Object[] fEventData;
     private final String[] fEventTags;
     private final HashMap<String, Object> fMap;

     public MvmDynamicEvent(Object[] paramArrayOfObject, String[] paramArrayOfString)
     {
       this.fEventData = paramArrayOfObject;
       this.fEventTags = paramArrayOfString;
       this.fMap = new HashMap();
       populateMap(paramArrayOfObject, this.fMap);
     }

     public boolean equals(MvmDynamicEvent paramMvmDynamicEvent)
     {
       return (paramMvmDynamicEvent != null) && (this.fEventData == paramMvmDynamicEvent.fEventData);
     }




     public Object[] getData()
     {
       return this.fEventData;
     }


     public String[] getTags()
     {
       return this.fEventTags;
     }























     public Set<String> getVarNames()
     {
       return this.fMap.keySet();
     }





     public Object getValue(String paramString)
     {
       if (this.fMap.containsKey(paramString)) {
         return this.fMap.get(paramString);
       }
       return new Object();
     }

     private Boolean isSimpleValue(Object paramObject, int paramInt)
     {
       if (!(paramObject instanceof Object[])) { return Boolean.valueOf(false);
       }
       assert (((Object[])paramObject).length == 1) : "Event data not well formed";
       Object[] arrayOfObject = (Object[])((Object[])(Object[])paramObject)[0];

       if ((arrayOfObject == null) || (arrayOfObject.length <= paramInt)) { return Boolean.valueOf(false);
       }
       Object localObject = arrayOfObject[paramInt];
       return Boolean.valueOf(((localObject instanceof int[])) || ((localObject instanceof String)) || ((localObject instanceof String[])) || ((localObject instanceof boolean[])) || ((localObject instanceof byte[])) || ((localObject instanceof short[])) || ((localObject instanceof char[])) || ((localObject instanceof long[])) || ((localObject instanceof float[])) || ((localObject instanceof double[])));
     }

















     private void populateMap(Object[] paramArrayOfObject, HashMap<String, Object> paramHashMap)
     {
       assert ((paramArrayOfObject != null) && (paramArrayOfObject.length > 0) && (paramArrayOfObject.length <= 2));

       if (1 == paramArrayOfObject.length) {
         populateMap((Object[])paramArrayOfObject[0], paramHashMap);
         return;
       }

       String[] arrayOfString = (String[])paramArrayOfObject[0];
       Object[] arrayOfObject = (Object[])paramArrayOfObject[1];

       if (arrayOfString == null) { return;
       }
       Boolean localBoolean = Boolean.valueOf(Arrays.asList(arrayOfString).containsAll(Arrays.asList(reserved)));

       Vector localVector = new Vector();


       Object localObject;

       for (int i = 0; i < arrayOfString.length; i++) {
         if (isSimpleValue(arrayOfObject, i).booleanValue())
         {
           if ((!localBoolean.booleanValue()) || ((!arrayOfString[i].equals("count")) && (!arrayOfString[i].equals("item_version"))))
           {

             localObject = this.fMap.put(arrayOfString[i], ((Object[])(Object[])arrayOfObject[0])[i]);
             if (localObject != null)
             {
               this.fMap.put(arrayOfString[i], localObject);
             }
             paramHashMap.put(arrayOfString[i], ((Object[])(Object[])arrayOfObject[0])[i]);
           }
         } else {
           localVector.add(Integer.valueOf(i));
         }
       }

       for (Iterator localIterator = localVector.iterator(); localIterator.hasNext();) { localObject = (Integer)localIterator.next();
         HashMap localHashMap = new HashMap();
         paramHashMap.put(arrayOfString[localObject.intValue()], localHashMap);
         populateMap((Object[])((Object[])(Object[])arrayOfObject[0])[localObject.intValue()], localHashMap);

         if (localHashMap.isEmpty()) {
           paramHashMap.remove(arrayOfString[localObject.intValue()]);
         }
       }
     }

     private static native MvmDynamicEvent nativeFactory(long paramLong);
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\eventmgr\MvmDynamicEvent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */