package minimalistTemplateEngine.syntaxtree;

import minimalistTemplateEngine.Context;
import minimalistTemplateEngine.Token;

//For pure text
public class TextNode extends SyntaxTree {
  
  private String text;
  
  public TextNode(Token t) {
    text = t.content;
  }

  public String render(Context c) {
    return text;
  }
}
