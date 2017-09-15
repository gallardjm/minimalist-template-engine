package minimalistTemplateEngine.syntaxtree;

import minimalistTemplateEngine.Context;

/**
 * Syntax tree leaf for pure text without any special meaning
 */
public class TextLeaf extends SyntaxTree {
  
  private String text;
  
  public TextLeaf(Token t) {
    text = t.getContentClean();
  }

  @Override
  public String render(Context c) {
    return text;
  }
}
