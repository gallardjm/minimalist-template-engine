package minimalistTemplateEngine;

import java.util.List;
import java.util.ArrayList;


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


class Root extends SyntaxTree {
  
  public Root() {
    this.children = new ArrayList<SyntaxTree>();
  }
  
  public void addNode(SyntaxTree t) {
    this.children.add(t);
  }
  
  public String render(Context c) {
    return renderChildren(c);
  }
}


//for variable {{ x }}
class Variable extends SyntaxTree {
  
  Token token;
  
  public Variable(Token t) {
    token = t;
  }

  public String render(Context c) {    
    return c.getString(token.getContentClean());
  }
}

//For pure text
class Text extends SyntaxTree {
  
  String text;
  
  public Text(Token t) {
    text = t.content;
  }

  public String render(Context c) {
    return text;
  }
}

//If / else / endif
class IfNode extends SyntaxTree {
  
  Token condition;
  Root ifBlock;
  Root elseBlock;
  Root currentBlock;
  
  public IfNode(Token t, SyntaxTree p) {
    condition = t;
    this.parent = p;
    ifBlock = new Root();
    elseBlock = new Root();
    
    currentBlock = ifBlock;
  }
  
  public void startElseBlock() {
    currentBlock = elseBlock;
  }
  
  public void addNode(SyntaxTree t) {
    this.currentBlock.addNode(t);
  }
  
  //TODO extend to boolean expression
  public String render(Context c) {
    boolean value = c.getBool(condition.getContentClean());
    if(value) {
      return ifBlock.render(c);
    } else {
      return elseBlock.render(c);
    }
  }
}