   package temp.com.mathworks.util.osgi;

   import java.util.Dictionary;
   import java.util.Properties;
   import org.osgi.framework.Bundle;
   import org.osgi.framework.BundleActivator;
   import org.osgi.framework.BundleContext;











   public class ServicesActivator
     implements BundleActivator
   {
     private static final String SERVICES_ATTRIBUTE = "Services";
     private static final String PAIR_DELIMITER = ",";
     private static final String SERVICE_DELIMITER = ":";

     public void start(BundleContext paramBundleContext)
       throws Exception
     {
       String str = (String)paramBundleContext.getBundle().getHeaders().get("Services");


       assert ((str != null) && (str.length() > 0)) : "The OSGi ServicesActivator requires a \"Services\" attribute in the bundle's Manifest";
       parseServices(paramBundleContext, str);
     }

     private static void parseServices(BundleContext paramBundleContext, String paramString) throws Exception {
       for (String str : paramString.split(",")) {
         registerServicePair(paramBundleContext, str);
       }
     }

     private static void registerServicePair(BundleContext paramBundleContext, String paramString) throws Exception {
       String str1 = "OSGi Manifest \"Services\" attribute incorrect; expected \"Services: interfaceName: className, interfaceName: className ...\"";

       assert (paramString.contains(":")) : str1;

       String[] arrayOfString = paramString.split(":");
       assert (arrayOfString.length == 2) : str1;
       String str2 = arrayOfString[0].trim();
       String str3 = arrayOfString[1].trim();


       Class localClass = Class.forName(str2);

       assert (localClass.isInterface()) : ("OSGi expected " + str2 + " in " + paramString + " to be an interface");


       Object localObject = Class.forName(str3).newInstance();

       assert (localClass.isInstance(localObject)) : ("OSGi expected " + str3 + " to implement " + str2);

       OSGiUtils.log("Registering " + str3 + " as a " + str2);
       paramBundleContext.registerService(localClass.getName(), localObject, new Properties());
     }

     public void stop(BundleContext paramBundleContext)
       throws Exception
     {}
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\osgi\ServicesActivator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */