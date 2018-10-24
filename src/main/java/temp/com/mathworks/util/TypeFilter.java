   package temp.com.mathworks.util;

   import java.util.HashMap;
   import java.util.List;
   import java.util.Map;





















   public class TypeFilter<T>
     implements Combiner<TargetedRequest<T>, Runnable>
   {
     public Runnable combine(final List<TargetedRequest<T>> paramList)
     {
       final HashMap localHashMap = new HashMap();


       for (TargetedRequest localTargetedRequest : paramList)
       {

         Class localClass = localTargetedRequest.getClass();
         Object localObject = (Map)localHashMap.get(localClass);
         if (localObject == null)
         {
           localObject = new HashMap();
           localHashMap.put(localClass, localObject);
         }
         ((Map)localObject).put(localTargetedRequest.getTarget(), localTargetedRequest);
       }

       new Runnable()
       {
         public void run()
         {
           for (TargetedRequest localTargetedRequest : paramList)
           {

             Class localClass = localTargetedRequest.getClass();
             Map localMap = (Map)localHashMap.get(localClass);
             if (((TargetedRequest)localMap.get(localTargetedRequest.getTarget())).equals(localTargetedRequest)) {
               localTargetedRequest.run();
             }
           }
         }
       };
     }

     public static abstract interface TargetedRequest<T>
       extends Runnable
     {
       public abstract T getTarget();
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\TypeFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */