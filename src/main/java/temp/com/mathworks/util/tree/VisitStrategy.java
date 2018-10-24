   package temp.com.mathworks.util.tree;

   import com.mathworks.util.Predicate;









   public class VisitStrategy<T>
   {
     private final Predicate<? super T> fRecursionCriteria;
     private final Predicate<? super T> fVisitCriteria;

     public VisitStrategy()
     {
       this(TreeUtils.yes());
     }




     public VisitStrategy(Predicate<? super T> paramPredicate)
     {
       this(TreeUtils.yes(), paramPredicate);
     }








     public VisitStrategy(Predicate<? super T> paramPredicate1, Predicate<? super T> paramPredicate2)
     {
       this.fRecursionCriteria = paramPredicate1;
       this.fVisitCriteria = paramPredicate2;
     }




     public Predicate<? super T> getRecursionCriteria()
     {
       return this.fRecursionCriteria;
     }




     public Predicate<? super T> getVisitCriteria()
     {
       return this.fVisitCriteria;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\tree\VisitStrategy.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */