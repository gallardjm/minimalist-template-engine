package minimalistTemplateEngine.syntaxtree;

import minimalistTemplateEngine.Context;
import minimalistTemplateEngine.Token;

//If / else / endif
public class IfNode extends SyntaxTree {
  
  Token condition;
  RootNode ifBlock;
  RootNode elseBlock;
  RootNode currentBlock;
  
  public IfNode(Token t, SyntaxTree p) {
    condition = t;
    this.parent = p;
    ifBlock = new RootNode();
    elseBlock = new RootNode();
    
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


