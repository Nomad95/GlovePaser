   package temp.com.mathworks.util;

   import java.awt.Color;
   import java.util.ArrayList;
   import java.util.Collections;
   import java.util.List;
   import org.apache.commons.lang.StringUtils;
   import org.apache.commons.lang.builder.ToStringBuilder;

































   public class StyledColor
     extends Color
   {
     protected String fStyleName;
     protected final List<String> fAllowedStyles;
     protected boolean fIsColor;

     public StyledColor(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, List<String> paramList)
     {
       super((float)paramDouble1, (float)paramDouble2, (float)paramDouble3, (float)paramDouble4);

       if (paramList == null) {
         throw new IllegalArgumentException("Argument allowedStyles must not be null.");
       }
       this.fStyleName = null;
       this.fIsColor = true;
       this.fAllowedStyles = new ArrayList(paramList);
     }

     public StyledColor(double paramDouble1, double paramDouble2, double paramDouble3, List<String> paramList)
     {
       this(paramDouble1, paramDouble2, paramDouble3, 1.0D, paramList);
     }







     public StyledColor(String paramString, List<String> paramList)
     {
       super(0.0F, 0.0F, 0.0F);

       if (paramList == null) {
         throw new IllegalArgumentException("Argument allowedStyles must not be null.");
       }
       assert (paramList.contains(paramString)) : ("styleName is not an allowed style: " + paramString);

       this.fStyleName = paramString;
       this.fIsColor = false;
       this.fAllowedStyles = new ArrayList(paramList);
     }




     private static StyledColor newInstance(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, String[] paramArrayOfString)
     {
       assert (paramArrayOfString != null) : "Argument allowedStyles must not be null";

       ArrayList localArrayList = new ArrayList(paramArrayOfString.length);
       for (String str : paramArrayOfString) {
         localArrayList.add(str);
       }
       return new StyledColor(paramDouble1, paramDouble2, paramDouble3, paramDouble4, localArrayList);
     }




     private static StyledColor newInstance(String paramString, String[] paramArrayOfString)
     {
       assert (paramString != null) : "Argument style must not be null";
       assert (paramArrayOfString != null) : "Argument allowedStyles must not be null";

       ArrayList localArrayList = new ArrayList(paramArrayOfString.length);
       for (String str : paramArrayOfString) {
         localArrayList.add(str);
       }
       return new StyledColor(paramString, localArrayList);
     }





     private double[] getComponents()
     {
       float[] arrayOfFloat = getComponents(null);
       return new double[] { arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3] };
     }





     public boolean isStyle()
     {
       return !this.fIsColor;
     }

     public boolean isColor()
     {
       return this.fIsColor;
     }


     public boolean equals(Object paramObject)
     {
       if (this == paramObject) {
         return true;
       }
       if (!(paramObject instanceof StyledColor)) {
         return false;
       }
       StyledColor localStyledColor = (StyledColor)paramObject;
       if (this.fIsColor != localStyledColor.fIsColor) {
         return false;
       }

       if (this.fIsColor)
       {
         return getRGB() == localStyledColor.getRGB();
       }


       return StringUtils.equalsIgnoreCase(this.fStyleName, localStyledColor.fStyleName);
     }






     public String getStyleName()
     {
       return this.fStyleName;
     }





     public List<String> getAllowedStyles()
     {
       return Collections.unmodifiableList(this.fAllowedStyles);
     }








     public boolean isStyleAllowed(String paramString)
     {
       return getFullStyleName(paramString) != null;
     }







     public String getFullStyleName(String paramString)
     {
       if (StringUtils.isBlank(paramString)) {
         return null;
       }
       for (String str : this.fAllowedStyles)
       {
         if (str.toLowerCase().startsWith(paramString.toLowerCase())) {
           return str;
         }
       }
       return null;
     }



     public String toString()
     {
       ToStringBuilder localToStringBuilder = new ToStringBuilder(this);

       localToStringBuilder.append("isColor", this.fIsColor);
       localToStringBuilder.append("styleName", this.fStyleName);
       localToStringBuilder.append("allowedStyles", this.fAllowedStyles.toString());

       double[] arrayOfDouble = getComponents();
       localToStringBuilder.append("r", arrayOfDouble[0]).append("g", arrayOfDouble[1]).append("b", arrayOfDouble[2]).append("a", arrayOfDouble[3]);
       localToStringBuilder.append("color", super.toString());

       return localToStringBuilder.toString();
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\StyledColor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */