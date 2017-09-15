package minimalistTemplateEngine.syntaxtree;

import java.util.ArrayList;

import minimalistTemplateEngine.Context;

public class RootNode extends SyntaxTree {
  
  public RootNode() {
    this.children = new ArrayList<SyntaxTree>();
  }
  
  public void addNode(SyntaxTree t) {
    this.children.add(t);
  }
  
  public String render(Context c) {
    return renderChildren(c);
  }
}