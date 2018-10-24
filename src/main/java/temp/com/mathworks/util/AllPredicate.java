   package temp.com.mathworks.util;




   public class AllPredicate<T>
     implements Predicate<T>
   {
     private final Predicate<T>[] fSubPredicates;



     public AllPredicate(Predicate<T>... paramVarArgs)
     {
       this.fSubPredicates = paramVarArgs;
     }

     public boolean accept(T paramT) {
       for (Predicate localPredicate : this.fSubPredicates)
         if ((localPredicate != null) && (!localPredicate.accept(paramT))) return false;
       return true;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\AllPredicate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */