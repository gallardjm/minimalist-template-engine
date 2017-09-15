package minimalistTemplateEngine.syntaxtree;

import minimalistTemplateEngine.Context;

//For pure text
public class TextNode extends SyntaxTree {
  
  private String text;
  
  public TextNode(Token t) {
    text = t.getContentClean();
  }

  public String render(Context c) {
    return text;
  }
}
