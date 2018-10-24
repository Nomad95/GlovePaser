   package temp.com.mathworks.util;

   import java.util.Properties;

















   public class SystemPropertiesInitializer
   {
     private static void updateSystemProperties()
     {
       Properties localProperties = System.getProperties();
       localProperties.put("ice.browser.verbose", "false");


       if (LanguageUtils.isJapanese()) {
         localProperties.put("ice.pilots.html4.defaultEncoding", "SHIFT_JIS");
       }

       localProperties.put("javax.xml.parsers.SAXParserFactory", "org.apache.xerces.jaxp.SAXParserFactoryImpl");
       localProperties.put("javax.xml.parsers.DocumentBuilderFactory", "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
       localProperties.put("javax.xml.transform.TransformerFactory", "com.icl.saxon.TransformerFactoryImpl");



       localProperties.put("com.apple.mrj.application.apple.menu.about.name", "MATLAB");




       if (System.getProperty("org.apache.commons.logging.Log") == null) {
         localProperties.put("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
       }
       if (System.getProperty("org.apache.commons.logging.simplelog.defaultlog") == null) {
         localProperties.put("org.apache.commons.logging.simplelog.defaultlog", "warn");
       }

       System.setProperties(localProperties);
     }

     static {}
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\SystemPropertiesInitializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */