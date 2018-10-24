package temp.com.mathworks.matlab.api.menus;

public abstract interface MenuContext
{
  public abstract MenuBuilder getBuilder(MenuID paramMenuID);
  
  public abstract void addNewMenu(MenuID paramMenuID, String paramString1, String paramString2);
  
  public abstract void refresh();
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\menus\MenuContext.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */