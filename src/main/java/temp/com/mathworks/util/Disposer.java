   package temp.com.mathworks.util;

   import com.google.common.base.Preconditions;
   import java.util.ArrayList;
   import java.util.Arrays;
   import java.util.IdentityHashMap;
   import java.util.Iterator;
   import java.util.List;
   import java.util.Map;
   import org.jetbrains.annotations.NotNull;
   import org.jetbrains.annotations.Nullable;






















   public class Disposer
   {
     private static class Node
     {
       final Disposable fDisposable;
       final Node fParent;
       final List<Node> fChildren = new ArrayList();

       private Node(Disposable paramDisposable, Node paramNode) {
         this.fDisposable = paramDisposable;
         this.fParent = paramNode;
         Disposer.sObject2Node.put(paramDisposable, this);
       }
     }

     private static final Disposable ROOT = new Disposable() { public void dispose() {}
     };
     private static final Map<Disposable, Node> sObject2Node = new IdentityHashMap();
     private static final Node sRoot = new Node(ROOT, null, null);





     public static void register(@NotNull Disposable paramDisposable1, @NotNull Disposable paramDisposable2)
     {
       Preconditions.checkArgument(paramDisposable1 != paramDisposable2, "Child and parent have to be different objects: '%s'", new Object[] { paramDisposable1 });

       synchronized (sRoot) {
         Preconditions.checkArgument(!isDescendantOf(paramDisposable1, paramDisposable2), "Cannot register cyclic dependencies, parent '%s', child '%s'", new Object[] { paramDisposable2, paramDisposable1 });
         Preconditions.checkArgument(!isDescendantOf(paramDisposable2, paramDisposable1), "Object '%s' is already registered as descendant of '%s'", new Object[] { paramDisposable1, paramDisposable2 });

         Preconditions.checkArgument(sObject2Node.get(paramDisposable1) == null, "Child '%s' is already registered under another root", new Object[] { paramDisposable1 });

         Node localNode = (Node)sObject2Node.get(paramDisposable2);
         if (localNode == null) { localNode = new Node(paramDisposable2, sRoot, null);
         }
         localNode.fChildren.add(new Node(paramDisposable1, localNode, null));
       }
     }









     public static void dispose(@Nullable Disposable paramDisposable)
     {
       _dispose(paramDisposable);
     }



     public static void dispose(@NotNull Iterable<? extends Disposable> paramIterable)
     {
       for (Disposable localDisposable : paramIterable) {
         _dispose(localDisposable);
       }
     }



     public static void dispose(@NotNull Disposable[] paramArrayOfDisposable)
     {
       dispose(Arrays.asList(paramArrayOfDisposable));
     }

     private static void _dispose(Disposable paramDisposable) {
       if (paramDisposable == null)
         return;
       Node localNode;
       synchronized (sRoot) {
         localNode = (Node)sObject2Node.remove(paramDisposable);
         if (localNode != null)
         {
           localNode.fParent.fChildren.remove(localNode);


           removeFromMap(localNode);
         }
       }

       if (localNode != null) {
         _dispose(localNode);
       } else {
         paramDisposable.dispose();
       }
     }

     private static void _dispose(Node paramNode) {
       List localList = paramNode.fChildren;

       Disposable localDisposable = paramNode.fDisposable;

       if ((localDisposable instanceof Disposable.Parent)) {
         ((Disposable.Parent)localDisposable).beforeChildrenDisposed(new Iterable() {
           public Iterator<Disposable> iterator() {
             new Iterator() {
               Iterator<Node> it = Disposer.2.this.val$children.iterator();

               public boolean hasNext() { return this.it.hasNext(); }

               public Disposable next()
               {
                 return ((Node)this.it.next()).fDisposable;
               }

               public void remove() {
                 this.it.remove();
               }
             };
           }
         });
       }

       int i = localList.size(); for (;;) { i--; if (i < 0) break;
         _dispose((Node)localList.get(i));
       }

       localDisposable.dispose();
     }

     private static void removeFromMap(Node paramNode) {
       for (Node localNode : paramNode.fChildren) {
         sObject2Node.remove(localNode.fDisposable);
         removeFromMap(localNode);
       }
     }

     private static boolean isDescendantOf(Disposable paramDisposable1, Disposable paramDisposable2) {
       Node localNode = (Node)sObject2Node.get(paramDisposable1);
       return (localNode != null) && (_isDescendantOf(localNode, paramDisposable2));
     }

     private static boolean _isDescendantOf(Node paramNode, Disposable paramDisposable) {
       for (Iterator localIterator = paramNode.fChildren.iterator(); localIterator.hasNext();
           return true)
       {
         Node localNode = (Node)localIterator.next();
         if ((localNode.fDisposable != paramDisposable) && (!_isDescendantOf(localNode, paramDisposable))) {}
       }
       return false;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\Disposer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */