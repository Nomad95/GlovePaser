   package temp.com.mathworks.util;

   import java.util.List;






















   public class RequestFilter
   {
     private final RequestAggregator<Request> fAggregator;
     private final Runnable fCode;

     public RequestFilter(Runnable paramRunnable)
     {
       this(paramRunnable, 100);
     }






     public RequestFilter(Runnable paramRunnable, int paramInt)
     {
       this.fCode = paramRunnable;
       this.fAggregator = new RequestAggregator(new Combiner()
       {
         public Runnable combine(final List<Request> paramAnonymousList)
         {
           new Runnable()
           {
             public void run()
             {
               if (!paramAnonymousList.isEmpty())
                 ((Request)paramAnonymousList.get(paramAnonymousList.size() - 1)).run();
               for (int i = 0; i < paramAnonymousList.size() - 1; i++) {
                 ((Request)paramAnonymousList.get(i)).finished();
               }
             }
           };
         }
       }, paramInt);
     }













     public void cancelPendingRequests()
     {
       this.fAggregator.cancelPendingRequests();
     }




     public void setName(String paramString)
     {
       this.fAggregator.setName(paramString);
     }


     public String getName()
     {
       return this.fAggregator.getName();
     }





     public void request()
     {
       request(null);
     }





     public void request(Runnable paramRunnable)
     {
       this.fAggregator.request(new Request(paramRunnable));
     }

     private class Request implements Runnable
     {
       private final Runnable fCodeToRunAfter;

       Request(Runnable paramRunnable)
       {
         this.fCodeToRunAfter = paramRunnable;
       }

       public void run()
       {
         RequestFilter.this.fCode.run();
         finished();
       }

       public void finished()
       {
         if (this.fCodeToRunAfter != null) {
           this.fCodeToRunAfter.run();
         }
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\RequestFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */