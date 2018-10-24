   package temp.com.mathworks.matlab.api.editor;

   import com.mathworks.matlab.api.editor.actions.Prioritizable;
   import com.mathworks.toolstrip.factory.TSTabConfiguration;
   import com.mathworks.toolstrip.factory.TSToolSet;
   import com.mathworks.toolstrip.factory.TSToolSetContents;
   import java.util.List;








   public abstract interface EditorToolstripTabContributor
     extends Prioritizable
   {
     public abstract Tab getTabToContribute();

     public abstract boolean isApplicable(Editor paramEditor);

     public abstract void contributeToEditorTab(TSTabConfiguration paramTSTabConfiguration,
        TSToolSetContents paramTSToolSetContents, Editor paramEditor);

     public abstract List<TSToolSet> getSupportingToolSets();

     public abstract TSTabConfiguration getTSTabConfiguration();

     public static enum Tab
     {
       EDITOR,




       VIEW,





       PUBLISH;

       private Tab() {}
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\editor\EditorToolstripTabContributor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */