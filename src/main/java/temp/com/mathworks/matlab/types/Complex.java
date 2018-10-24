   package temp.com.mathworks.matlab.types;


   public final class Complex
   {
     public double real;

     public double imag;


     public Complex(double paramDouble1, double paramDouble2)
     {
       this.real = paramDouble1;
       this.imag = paramDouble2;
     }

     public String toString() {
       return String.valueOf(this.real) + " + " + String.valueOf(this.imag) + "i";
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\types\Complex.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */