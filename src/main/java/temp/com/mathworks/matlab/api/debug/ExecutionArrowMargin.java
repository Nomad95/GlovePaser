package temp.com.mathworks.matlab.api.debug;

public abstract interface ExecutionArrowMargin
{
  public abstract void addExecutionDisplayAdapter(ExecutionDisplayAdapter paramExecutionDisplayAdapter);
  
  public abstract void replaceExecutionDisplayAdapter(ExecutionDisplayAdapter paramExecutionDisplayAdapter);
  
  public abstract boolean hasExecutionDisplayAdapterDecoration(Class<? extends ExecutionDisplayAdapter> paramClass);
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\debug\ExecutionArrowMargin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */