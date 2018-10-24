package temp.com.mathworks.mvm.eventmgr;

public abstract interface EventMgr
{
  public abstract <E,  extends MvmEvent> void addMvmListener(MvmListener<E> paramMvmListener, String paramString)
    throws InvalidEventTypeException;
  
  public abstract <E,  extends MvmTypedEvent> void addMvmListener(MvmListener<E> paramMvmListener, Class<E> paramClass)
    throws InvalidEventTypeException;
  
  public abstract <E,  extends MvmTypedEvent> void addMvmListener(MvmListener<E> paramMvmListener, Class<E> paramClass,
        String paramString)
    throws InvalidEventTypeException;
  
  public abstract <E,  extends MvmTypedEvent> void removeMvmListener(MvmListener<E> paramMvmListener,
        Class<E> paramClass);
  
  public abstract <E,  extends MvmEvent> void removeMvmListener(MvmListener<E> paramMvmListener, String paramString);
  
  public abstract <E,  extends MvmTypedEvent> void removeMvmListener(MvmListener<E> paramMvmListener,
        Class<E> paramClass, String paramString);
  
  public abstract <E extends FirableMvmEvent> void fireMvmEvent(E paramE)
    throws InvalidEventTypeException;
  
  public abstract void flush();
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\eventmgr\EventMgr.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */