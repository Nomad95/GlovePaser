   package temp.com.mathworks.util.event;

   import java.util.Collections;
   import java.util.HashMap;
   import java.util.Map;
   import java.util.Map.Entry;
   import java.util.Set;




   public class GlobalEventManager
   {
     private static final Map<String, EventListenerList<GlobalEventListener>> LISTENER_LISTS = Collections.synchronizedMap(new HashMap());






     public static void addListener(String paramString, GlobalEventListener paramGlobalEventListener)
     {
       EventListenerList localEventListenerList = (EventListenerList)LISTENER_LISTS.get(paramString);
       if (localEventListenerList == null) {
         synchronized (LISTENER_LISTS) {
           localEventListenerList = (EventListenerList)LISTENER_LISTS.get(paramString);
           if (localEventListenerList == null) {
             localEventListenerList = EventListenerList.newEventListenerList(GlobalEventListener.class);
             LISTENER_LISTS.put(paramString, localEventListenerList);
           }
         }
       }
       localEventListenerList.addListener(paramGlobalEventListener);
     }

     public static void removeListeners(String paramString)
     {
       LISTENER_LISTS.remove(paramString);
     }

     public static void removeListener(String paramString, GlobalEventListener paramGlobalEventListener)
     {
       EventListenerList localEventListenerList = (EventListenerList)LISTENER_LISTS.get(paramString);
       if (localEventListenerList != null) localEventListenerList.removeListener(paramGlobalEventListener);
     }

     public static void removeListener(GlobalEventListener paramGlobalEventListener)
     {
       Set localSet = LISTENER_LISTS.entrySet();
       synchronized (LISTENER_LISTS) {
         for (Entry localEntry : localSet) {
           ((EventListenerList)localEntry.getValue()).removeListener(paramGlobalEventListener);
         }
       }
     }

     public static void fireEvent(String paramString)
     {
       EventListenerList localEventListenerList = (EventListenerList)LISTENER_LISTS.get(paramString);
       if (localEventListenerList != null) {
         ((GlobalEventListener)localEventListenerList.fire()).actionPerformed(paramString);
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\event\GlobalEventManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */