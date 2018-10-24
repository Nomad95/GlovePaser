   package temp.com.mathworks.util.tree;

   import java.util.HashMap;
   import java.util.List;
   import java.util.Map;
   import java.util.Vector;




   public class DefaultMutableTree<T>
     implements Tree<T>
   {
     private T fRoot;
     private Map<T, List<T>> fChildren;
     private Map<T, T> fParents;

     public DefaultMutableTree()
     {
       this(null);
     }

     public DefaultMutableTree(T paramT)
     {
       this.fRoot = paramT;
       this.fChildren = new HashMap();
       this.fParents = new HashMap();
     }

     public void addChild(T paramT1, T paramT2)
     {
       Object localObject = (List)this.fChildren.get(paramT1);
       if (localObject == null)
       {
         localObject = new Vector();
         this.fChildren.put(paramT1, localObject);
       }
       ((List)localObject).add(paramT2);
       this.fParents.put(paramT2, paramT1);
     }

     public void removeChild(T paramT1, T paramT2)
     {
       List localList = (List)this.fChildren.get(paramT1);
       if (localList != null)
         localList.remove(paramT2);
       this.fParents.remove(paramT2);
     }

     public void removeChild(T paramT, int paramInt)
     {
       List localList = (List)this.fChildren.get(paramT);
       if (localList != null)
       {
         this.fParents.remove(localList.get(paramInt));
         localList.remove(paramInt);
       }
     }

     public T getRoot()
     {
       return (T)this.fRoot;
     }

     public T getChild(T paramT, int paramInt)
     {
       return (T)((List)this.fChildren.get(paramT)).get(paramInt);
     }

     public T getParent(T paramT)
     {
       return (T)this.fParents.get(paramT);
     }

     public int getChildCount(T paramT)
     {
       List localList = (List)this.fChildren.get(paramT);
       return localList == null ? 0 : localList.size();
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\tree\DefaultMutableTree.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */