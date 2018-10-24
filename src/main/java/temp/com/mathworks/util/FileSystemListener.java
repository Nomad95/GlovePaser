package temp.com.mathworks.util;

import java.io.File;

@Deprecated
public abstract interface FileSystemListener
{
  @Deprecated
  public abstract void fileCreated(File paramFile);
  
  @Deprecated
  public abstract void fileMoved(File paramFile1, File paramFile2);
  
  @Deprecated
  public abstract void fileDeleted(File paramFile);
  
  @Deprecated
  public abstract void fileChanged(File paramFile);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\FileSystemListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */