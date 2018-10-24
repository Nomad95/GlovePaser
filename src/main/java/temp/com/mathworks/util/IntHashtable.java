   package temp.com.mathworks.util;

   import java.util.Hashtable;













   public class IntHashtable
     extends Hashtable
   {
     private MutableInt fKey = new MutableInt();


     public IntHashtable() {}


     public IntHashtable(int paramInt)
     {
       super(paramInt);
     }

     public synchronized boolean containsKey(int paramInt)
     {
       return super.containsKey(this.fKey.setValue(paramInt));
     }

     public synchronized Object get(int paramInt)
     {
       return super.get(this.fKey.setValue(paramInt));
     }

     public synchronized Object put(int paramInt, Object paramObject)
     {
       return super.put(new MutableInt(paramInt), paramObject);
     }

     public synchronized Object remove(int paramInt)
     {
       return super.remove(this.fKey.setValue(paramInt));
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\IntHashtable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */