package minimalistTemplateEngine.syntaxtree;

import java.util.List;

import minimalistTemplateEngine.Context;

public abstract class SyntaxTree {
  
  public SyntaxTree parent;
  protected List<SyntaxTree> children = null;
  
  public void addNode(SyntaxTree t) {}
  
  public String render(Context c) { return "";}
    
  public String renderChildren(Context c) {
    String result = "";
    for(SyntaxTree st : children) {
      result += st.render(c);
    }
    return result;
  }
}






