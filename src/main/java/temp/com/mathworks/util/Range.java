   package temp.com.mathworks.util;

   /**
    * @deprecated

   public class Range implements Cloneable {
     private int fStart;
     private int fEnd;

     public Range() {}

     public Range(int paramInt1, int paramInt2) {
       this.fStart = paramInt1;
       this.fEnd = paramInt2;
     }

     public void copyFrom(Range paramRange) {
       this.fStart = paramRange.fStart;
       this.fEnd = paramRange.fEnd;
     }

     public int getStart() {
       return this.fStart;
     }

     public void setStart(int paramInt) {
       this.fStart = paramInt;
     }

     public int getEnd() {
       return this.fEnd;
     }

     public void setEnd(int paramInt)
     {
       this.fEnd = paramInt;
     }

     public void setValues(int paramInt1, int paramInt2) {
       setStart(paramInt1);
       setEnd(paramInt2);
     }

     public int getMin() {
       return Math.min(this.fStart, this.fEnd);
     }

     public int getMax() {
       return Math.max(this.fStart, this.fEnd);
     }

     public boolean isEmpty() {
       return this.fStart == this.fEnd;
     }

     public boolean intersect(Range paramRange)
     {
       return intersect(paramRange, null);
     }

     public boolean intersect(int paramInt1, int paramInt2) {
       return intersect(paramInt1, paramInt2, null);
     }

     public boolean intersect(Range paramRange1, Range paramRange2)
     {
       return intersect(paramRange1.getMin(), paramRange1.getMax(), paramRange2);
     }

     public boolean intersect(int paramInt1, int paramInt2, Range paramRange) {
       int i = Math.min(paramInt1, paramInt2);
       int j = Math.max(paramInt1, paramInt2);
       int k = Math.max(i, getMin());
       int m = Math.min(j, getMax());

       boolean bool = k <= m;

       if (paramRange != null) {
         paramRange.setStart(bool ? k : -1);
         paramRange.setEnd(bool ? m : -1);
       }

       return bool;
     }

     public void union(Range paramRange1, Range paramRange2)
     {
       union(paramRange1.getMin(), paramRange1.getMax(), paramRange2);
     }

     public void union(int paramInt1, int paramInt2, Range paramRange) {
       int i = Math.min(paramInt1, paramInt2);
       int j = Math.max(paramInt1, paramInt2);



       int k = Math.min(i, paramRange.getMin());
       int m = Math.max(j, paramRange.getMax());
       paramRange.setStart(k);
       paramRange.setEnd(m);
     }

     public String toString() {
       return "[" + this.fStart + "," + this.fEnd + "]";
     }

     public Object clone() {
       try {
         return super.clone();
       }
       catch (CloneNotSupportedException localCloneNotSupportedException)
       {
         throw new InternalError();
       }
     }

     public boolean equals(Object paramObject) {
       if (!(paramObject instanceof Range)) {
         return false;
       }
       Range localRange = (Range)paramObject;
       return (this.fStart == localRange.fStart) && (this.fEnd == localRange.fEnd);
     }

     public int hashCode() {
       return (this.fStart & 0xFFFF) << 16 | this.fEnd & 0xFFFF;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\Range.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */