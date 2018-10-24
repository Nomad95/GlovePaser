   package temp.com.mathworks.util.tree;

   import java.awt.Component;
   import java.awt.Container;



   public class ComponentTree
     implements Tree<Component>
   {
     private final Component fRoot;

     public static ComponentTree getInstance(Component paramComponent)
     {
       return new ComponentTree(paramComponent);
     }

     private ComponentTree(Component paramComponent)
     {
       this.fRoot = paramComponent;
     }

     public Component getRoot()
     {
       return this.fRoot;
     }

     public int getChildCount(Component paramComponent)
     {
       if ((paramComponent instanceof Container)) {
         return ((Container)paramComponent).getComponentCount();
       }
       return 0;
     }

     public Component getChild(Component paramComponent, int paramInt)
     {
       if ((paramComponent instanceof Container)) {
         return ((Container)paramComponent).getComponent(paramInt);
       }
       throw new IllegalArgumentException(paramComponent + " is not a container");
     }

     public Component getParent(Component paramComponent)
     {
       return paramComponent.getParent();
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\tree\ComponentTree.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */