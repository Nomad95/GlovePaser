   package temp.com.mathworks.mvm;




   public final class MvmFactoryWrapper
   {
     private final MvmFactory fMvmFactory;




     MvmFactoryWrapper(MvmFactory paramMvmFactory)
     {
       this.fMvmFactory = paramMvmFactory;
     }

     public MvmFactory get() {
       return this.fMvmFactory;
     }

     public long getHandle() {
       return this.fMvmFactory.getHandle();
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\MvmFactoryWrapper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */