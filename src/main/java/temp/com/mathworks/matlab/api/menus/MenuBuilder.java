package temp.com.mathworks.matlab.api.menus;

import java.awt.Component;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public abstract interface MenuBuilder
{
  public abstract JMenu getMenu();
  
  public abstract JPopupMenu getPopupMenu();
  
  public abstract boolean isEmpty();
  
  public abstract void insertGroupBefore(MenuGroupID paramMenuGroupID1, MenuGroupID paramMenuGroupID2);
  
  public abstract void insertGroupAfter(MenuGroupID paramMenuGroupID1, MenuGroupID paramMenuGroupID2);
  
  public abstract void insertNextGroup(MenuGroupID paramMenuGroupID);
  
  public abstract Component add(MenuGroupID paramMenuGroupID, Component paramComponent);
  
  public abstract JMenuItem add(MenuGroupID paramMenuGroupID, Action paramAction);
  
  public abstract void clearContents();
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\menus\MenuBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */