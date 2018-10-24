package temp.com.mathworks.matlab.api.menus;

public abstract interface MenuContributor<T>
{
  public abstract Class<T> getContributionTarget();
  
  public abstract void contribute(T paramT, MenuContext paramMenuContext);
  
  public abstract void contributeToContextMenu(T paramT, MenuBuilder paramMenuBuilder);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\menus\MenuContributor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */