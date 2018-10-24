package temp.com.mathworks.util.osgi;

import java.io.File;
import java.util.Collection;
import org.osgi.framework.Bundle;

public abstract interface BundleProvider
{
  public abstract Collection<File> getBundleJarFiles(Bundle paramBundle);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\osgi\BundleProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */