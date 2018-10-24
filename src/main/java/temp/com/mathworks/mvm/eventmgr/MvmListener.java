package temp.com.mathworks.mvm.eventmgr;

import java.util.EventListener;

public abstract interface MvmListener<V extends MvmEvent>
  extends EventListener
{
  public abstract void mvmChanged(V paramV);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\eventmgr\MvmListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */