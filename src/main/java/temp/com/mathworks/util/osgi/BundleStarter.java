package temp.com.mathworks.util.osgi;

import java.util.Collection;
import org.osgi.framework.Bundle;

public abstract interface BundleStarter
{
  public abstract void startBundles(Bundle paramBundle, Collection<Bundle> paramCollection);
  
  public abstract void startBundleAtRuntime(Bundle paramBundle1, Bundle paramBundle2);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\osgi\BundleStarter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */