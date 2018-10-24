   package temp.com.mathworks.util.tree;

   import com.mathworks.util.Converter;
   import com.mathworks.util.Predicate;
   import java.awt.Component;
   import java.util.Collection;
   import java.util.HashMap;
   import java.util.HashSet;
   import java.util.Iterator;
   import java.util.LinkedList;
   import java.util.List;
   import java.util.Map;
   import java.util.Stack;
   import javax.swing.tree.DefaultMutableTreeNode;
   import javax.swing.tree.TreeModel;
   import javax.swing.tree.TreePath;










   public class TreeUtils
   {
     public static <T> Tree<T> createWithUnknownRoot(T paramT, final Tree<T> paramTree)
     {
       new Tree()
       {
         private T fRoot;

         public T getRoot()
         {
           if (this.fRoot == null)
           {
             this.fRoot = this.val$argKnownItem;
             while (getParent(this.fRoot) != null)
               this.fRoot = getParent(this.fRoot);
           }
           return (T)this.fRoot;
         }

         public int getChildCount(T paramAnonymousT)
         {
           return paramTree.getChildCount(paramAnonymousT);
         }

         public T getChild(T paramAnonymousT, int paramAnonymousInt)
         {
           return (T)paramTree.getChild(paramAnonymousT, paramAnonymousInt);
         }

         public T getParent(T paramAnonymousT)
         {
           return (T)paramTree.getParent(paramAnonymousT);
         }
       };
     }

     public static Tree<DefaultMutableTreeNode> createTree(TreeModel paramTreeModel)
     {
       new Tree()
       {
         public DefaultMutableTreeNode getRoot()
         {
           return (DefaultMutableTreeNode)this.val$argModel.getRoot();
         }

         public int getChildCount(DefaultMutableTreeNode paramAnonymousDefaultMutableTreeNode)
         {
           return paramAnonymousDefaultMutableTreeNode.getChildCount();
         }

         public DefaultMutableTreeNode getChild(DefaultMutableTreeNode paramAnonymousDefaultMutableTreeNode, int paramAnonymousInt)
         {
           return (DefaultMutableTreeNode)paramAnonymousDefaultMutableTreeNode.getChildAt(paramAnonymousInt);
         }

         public DefaultMutableTreeNode getParent(DefaultMutableTreeNode paramAnonymousDefaultMutableTreeNode)
         {
           return (DefaultMutableTreeNode)paramAnonymousDefaultMutableTreeNode.getParent();
         }
       };
     }




     public static <T> Predicate<T> yes()
     {
       new Predicate()
       {
         public boolean accept(T paramAnonymousT)
         {
           return true;
         }
       };
     }







     public static <T> Predicate<T> yesWhileEmpty(Collection<T> paramCollection)
     {
       new Predicate()
       {
         public boolean accept(T paramAnonymousT)
         {
           return this.val$argCollection.size() == 0;
         }
       };
     }





     public static <T> Predicate<T> instanceOf(Class paramClass)
     {
       new Predicate()
       {
         public boolean accept(T paramAnonymousT)
         {
           return this.val$argClass.isInstance(paramAnonymousT);
         }
       };
     }













     public static <T> Predicate<? super T> or(Predicate... paramVarArgs)
     {
       new Predicate()
       {
         public boolean accept(T paramAnonymousT)
         {
           for (Predicate localPredicate : this.val$argPredicates)
             if (localPredicate.accept(paramAnonymousT))
               return true;
           return false;
         }
       };
     }





     public static <T> Predicate<? super T> and(Predicate... paramVarArgs)
     {
       new Predicate()
       {
         public boolean accept(T paramAnonymousT)
         {
           for (Predicate localPredicate : this.val$argPredicates)
             if (!localPredicate.accept(paramAnonymousT))
               return false;
           return true;
         }
       };
     }






     public static <T extends Component> T findAncestorComponent(Component paramComponent, Class<T> paramClass)
     {
       return (Component)findAncestor(createWithUnknownRoot(paramComponent, ComponentTree.getInstance(paramComponent)), paramComponent, instanceOf(paramClass));
     }

     public static <T extends Component> T findComponent(Component paramComponent, Class<T> paramClass)
     {
       Collection localCollection = find(subtree(ComponentTree.getInstance(paramComponent), paramComponent), new VisitStrategy(instanceOf(paramClass)), new HashSet());



       if (localCollection.size() == 0) {
         return null;
       }
       return (Component)localCollection.iterator().next();
     }






     public static <T extends Component> Collection<T> findComponents(Component paramComponent, Class<T> paramClass)
     {
       return find(subtree(ComponentTree.getInstance(paramComponent), paramComponent), new VisitStrategy(instanceOf(paramClass)), new HashSet());
     }









     public static Collection<Component> findComponents(Component paramComponent, Predicate<? super Component> paramPredicate)
     {
       return find(subtree(ComponentTree.getInstance(paramComponent), paramComponent), new VisitStrategy(paramPredicate), new HashSet());
     }









     public static <T> TreePath getPath(Tree<T> paramTree, T paramT)
     {
       if (paramT == null) {
         return null;
       }
       Stack localStack = new Stack();
       for (Object localObject = paramT; localObject != null; localObject = paramTree.getParent(localObject))
         localStack.push(localObject);
       localObject = new Object[localStack.size()];
       for (int i = 0; i < localObject.length; i++)
         localObject[i] = localStack.pop();
       return new TreePath((Object[])localObject);
     }







     public static <T> int getIndexOfChild(Tree<T> paramTree, T paramT1, T paramT2)
     {
       int i = paramTree.getChildCount(paramT1);
       for (int j = 0; j < i; j++) {
         if (paramTree.getChild(paramT1, j).equals(paramT2))
           return j;
       }
       return -1;
     }








     public static <T> T findAncestor(Tree<T> paramTree, T paramT, Predicate<? super T> paramPredicate)
     {
       if (paramT == null)
         return null;
       if (paramPredicate.accept(paramT)) {
         return paramT;
       }
       return (T)findAncestor(paramTree, paramTree.getParent(paramT), paramPredicate);
     }








     public static <T, P> P findAmongAncestors(Tree<T> paramTree, T paramT, PropertyGetter<T, P> paramPropertyGetter)
     {
       if (paramT == null) {
         return null;
       }

       Object localObject = paramPropertyGetter.get(paramT);
       if (localObject == null) {
         return (P)findAmongAncestors(paramTree, paramTree.getParent(paramT), paramPropertyGetter);
       }
       return (P)localObject;
     }







     public static <T> T findSingle(Tree<T> paramTree, VisitStrategy<T> paramVisitStrategy)
     {
       LinkedList localLinkedList = new LinkedList();
       find(paramTree, new VisitStrategy(and(new Predicate[] { yesWhileEmpty(localLinkedList), paramVisitStrategy.getRecursionCriteria() }), and(new Predicate[] { yesWhileEmpty(localLinkedList), paramVisitStrategy.getVisitCriteria() })), localLinkedList);






       return localLinkedList.size() == 0 ? null : localLinkedList.iterator().next();
     }










     public static <T, S extends T, C extends Collection<S>> C find(Tree<T> paramTree, VisitStrategy<T> paramVisitStrategy, C paramC)
     {
       visit(paramTree, paramVisitStrategy, new Visitor()
       {
         public void visit(T paramAnonymousT)
         {
           this.val$argOutput.add(paramAnonymousT);
         }

       });
       return paramC;
     }







     public static <T> Tree<T> subtree(final Tree<T> paramTree, T paramT)
     {
       new Tree()
       {
         public T getRoot()
         {
           return (T)this.val$argRoot;
         }

         public int getChildCount(T paramAnonymousT)
         {
           return paramTree.getChildCount(paramAnonymousT);
         }

         public T getChild(T paramAnonymousT, int paramAnonymousInt)
         {
           return (T)paramTree.getChild(paramAnonymousT, paramAnonymousInt);
         }

         public T getParent(T paramAnonymousT)
         {
           if (paramAnonymousT == this.val$argRoot) {
             return null;
           }
           return (T)paramTree.getParent(paramAnonymousT);
         }
       };
     }





     public static <T> void visit(Tree<T> paramTree, Visitor<T> paramVisitor)
     {
       visit(paramTree, new VisitStrategy(), paramVisitor);
     }







     public static <T> void visit(Tree<T> paramTree, VisitStrategy<T> paramVisitStrategy, Visitor<T> paramVisitor)
     {
       Object localObject = paramTree.getRoot();
       if (paramVisitStrategy.getRecursionCriteria().accept(localObject))
       {
         if (paramVisitStrategy.getVisitCriteria().accept(localObject)) {
           paramVisitor.visit(localObject);
         }
         for (int i = 0; i < paramTree.getChildCount(localObject); i++) {
           visit(subtree(paramTree, paramTree.getChild(localObject, i)), paramVisitStrategy, paramVisitor);
         }
       }
     }












     public static <T, P, C extends Collection<P>> C getAll(Tree<T> paramTree, VisitStrategy<T> paramVisitStrategy, final PropertyGetter<T, P> paramPropertyGetter, C paramC)
     {
       visit(paramTree, paramVisitStrategy, new Visitor()
       {
         public void visit(T paramAnonymousT)
         {
           this.val$argOutput.add(paramPropertyGetter.get(paramAnonymousT));
         }
       });
       return paramC;
     }










     public static <T, P> List<P> union(Tree<T> paramTree, PropertyGetter<T, ? extends Collection<P>> paramPropertyGetter)
     {
       return (List)union(paramTree, paramPropertyGetter, new LinkedList());
     }










     public static <T, P, C extends Collection<P>> C union(Tree<T> paramTree, PropertyGetter<T, ? extends Collection<P>> paramPropertyGetter, C paramC)
     {
       return union(paramTree, new VisitStrategy(), paramPropertyGetter, paramC);
     }












     public static <T, P, C extends Collection<P>> C union(Tree<T> paramTree, VisitStrategy<T> paramVisitStrategy, final PropertyGetter<T, ? extends Collection<P>> paramPropertyGetter, C paramC)
     {
       visit(paramTree, paramVisitStrategy, new Visitor()
       {
         public void visit(T paramAnonymousT)
         {
           this.val$argOutput.addAll((Collection)paramPropertyGetter.get(paramAnonymousT));
         }
       });
       return paramC;
     }











     public static <T, K, V> Map<K, V> simpleMapUnion(Tree<T> paramTree, PropertyGetter<T, ? extends Map<K, V>> paramPropertyGetter)
     {
       return simpleMapUnion(paramTree, paramPropertyGetter, new HashMap());
     }











     public static <T, K, V, M extends Map<K, V>> M simpleMapUnion(Tree<T> paramTree, PropertyGetter<T, ? extends Map<K, V>> paramPropertyGetter, M paramM)
     {
       return simpleMapUnion(paramTree, new VisitStrategy(), paramPropertyGetter, paramM);
     }













     public static <T, K, V, M extends Map<K, V>> M simpleMapUnion(Tree<T> paramTree, VisitStrategy<T> paramVisitStrategy, final PropertyGetter<T, ? extends Map<K, V>> paramPropertyGetter, M paramM)
     {
       visit(paramTree, paramVisitStrategy, new Visitor()
       {
         public void visit(T paramAnonymousT)
         {
           this.val$argOutput.putAll((Map)paramPropertyGetter.get(paramAnonymousT));
         }
       });
       return paramM;
     }















     public static <T, K, V> Map<K, List<V>> collectionMapUnion(Tree<T> paramTree, PropertyGetter<T, ? extends Map<K, ? extends Collection<V>>> paramPropertyGetter)
     {
       return collectionMapUnion(paramTree, paramPropertyGetter, new HashMap());
     }













     public static <T, K, V, M extends Map<K, List<V>>> M collectionMapUnion(Tree<T> paramTree, PropertyGetter<T, ? extends Map<K, ? extends Collection<V>>> paramPropertyGetter, M paramM)
     {
       return collectionMapUnion(paramTree, new VisitStrategy(), paramPropertyGetter, paramM);
     }

































     public static <T, K, V, M extends Map<K, List<V>>> M collectionMapUnion(Tree<T> paramTree, VisitStrategy<T> paramVisitStrategy, PropertyGetter<T, ? extends Map<K, ? extends Collection<V>>> paramPropertyGetter, final M paramM)
     {
       visit(paramTree, paramVisitStrategy, new Visitor()
       {
         public void visit(T paramAnonymousT)
         {
           Map localMap = (Map)this.val$argGetter.get(paramAnonymousT);
           for (Object localObject1 : localMap.keySet())
           {
             Object localObject2 = (List)paramM.get(localObject1);
             if (localObject2 == null)
             {
               localObject2 = new LinkedList();
               paramM.put(localObject1, localObject2);
             }
             ((List)localObject2).addAll((Collection)localMap.get(localObject1));
           }

         }
       });
       return paramM;
     }









     public static <T, U> Tree<U> convertSnapshot(Tree<T> paramTree, final Converter<T, U> paramConverter)
     {
       final DefaultMutableTree localDefaultMutableTree = new DefaultMutableTree(paramConverter.convert(paramTree.getRoot()));
       visit(paramTree, new Visitor()
       {
         public void visit(T paramAnonymousT)
         {
           if (paramAnonymousT != this.val$argSource.getRoot())
             localDefaultMutableTree.addChild(paramConverter.convert(this.val$argSource.getParent(paramAnonymousT)), paramConverter.convert(paramAnonymousT));
         }
       });
       return localDefaultMutableTree;
     }

     public static abstract interface PropertyGetter<T, P>
     {
       public abstract P get(T paramT);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\tree\TreeUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */