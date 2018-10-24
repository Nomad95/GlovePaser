package temp.com.mathworks.matlab.api.editor;

import com.mathworks.matlab.api.menus.MenuGroupID;

public enum EditorMenuGroupID
  implements MenuGroupID
{
  FILE_NEW_OPEN_CLOSE_GROUP,  FILE_SAVE_GROUP,  FILE_PRINT_GROUP,  FILE_WORKSPACE_GROUP,  NEW_GROUP,  EDIT_UNDO_REDO_GROUP,  EDIT_TEXT_GROUP,  EDIT_FIND_GROUP,  TEXT_SELECTION_GROUP,  TEXT_COMMENT_GROUP,  TEXT_INDENT_GROUP,  TEXT_CASE_GROUP,  TEXT_FOLD_GROUP,  FOLD_SINGLE_GROUP,  FOLD_ALL_GROUP,  GO_JUMP_GROUP,  GO_BOOKMARK_GROUP,  GO_GOTO_GROUP,  TOOLS_REPORT_GROUP,  DEBUG_RUN_GROUP,  DEBUG_BREAKPOINTS_GROUP,  WINDOW_GROUP,  CONTEXT_SELECTION_GROUP,  CONTEXT_EDIT_GROUP,  CONTEXT_COMMENT_INDENT_GROUP,  CONTEXT_DEBUG_GROUP,  CONTEXT_FUNCTIONS_GROUP,  CONTEXT_VIEW_MANAGEMENT_GROUP,  CONTEXT_CODE_ANALYZER_GROUP;
  
  public static final String MENU_GROUP_PROPERTY = "EditorMenuGroup";
  
  private EditorMenuGroupID() {}
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\api\editor\EditorMenuGroupID.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */