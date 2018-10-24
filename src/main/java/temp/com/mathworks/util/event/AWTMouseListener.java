package temp.com.mathworks.util.event;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.MouseEvent;
import org.jetbrains.annotations.Nullable;

public abstract interface AWTMouseListener
{
  public abstract void mouseEventDispatched(MouseEvent paramMouseEvent, @Nullable Window paramWindow,
          @Nullable Component paramComponent);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\event\AWTMouseListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */