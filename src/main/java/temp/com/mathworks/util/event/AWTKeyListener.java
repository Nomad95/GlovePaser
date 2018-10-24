package temp.com.mathworks.util.event;

import java.awt.Window;
import java.awt.event.KeyEvent;
import org.jetbrains.annotations.Nullable;

public abstract interface AWTKeyListener
{
  public abstract void keyEventDispatched(KeyEvent paramKeyEvent, @Nullable Window paramWindow);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\event\AWTKeyListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */