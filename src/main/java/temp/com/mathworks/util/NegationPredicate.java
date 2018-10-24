   package temp.com.mathworks.util;



   public class NegationPredicate<T>
     implements Predicate<T>
   {
     private final Predicate<T> fPredicate;


     public NegationPredicate(Predicate<T> paramPredicate)
     {
       this.fPredicate = paramPredicate;
     }

     public boolean accept(T paramT) {
       return !this.fPredicate.accept(paramT);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\NegationPredicate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */