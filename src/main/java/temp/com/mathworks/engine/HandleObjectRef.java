   package temp.com.mathworks.engine;

   import com.mathworks.matlab.types.HandleObject;
   import com.mathworks.mvm.MvmFactory;















   class HandleObjectRef
     implements HandleObject
   {
     private long baseRef;
     private long elementRef;

     private static Object createHandleObjectRef(long paramLong1, long paramLong2)
     {
       HandleObjectRef localHandleObjectRef = new HandleObjectRef();
       localHandleObjectRef.baseRef = paramLong1;
       localHandleObjectRef.elementRef = paramLong2;
       return localHandleObjectRef;
     }

     protected void finalize() throws Throwable
     {
       MvmFactory.nativeReleaseHandleObject(this.baseRef);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\engine\HandleObjectRef.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */