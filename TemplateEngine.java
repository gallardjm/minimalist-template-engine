package minimalistTemplateEngine;

public class TemplateEngine {
  
  private String VAR_TOKEN_START   = "\\{\\{";
  private String VAR_TOKEN_END     = "\\}\\}";
  private String BLOCK_TOKEN_START = "\\{%";
  private String BLOCK_TOKEN_END   = "%\\}";
  private int TOKEN_MAX_SIZE = 99999;
  
  private String pattern;
  
  public TemplateEngine() {
    this.pattern= "(?="+VAR_TOKEN_START+".{0,"+TOKEN_MAX_SIZE+"}?"+VAR_TOKEN_END+"|"+BLOCK_TOKEN_START+".{0,"+TOKEN_MAX_SIZE+"}?"+BLOCK_TOKEN_END+")|(?<="+VAR_TOKEN_START+".{0,"+TOKEN_MAX_SIZE+"}?"+VAR_TOKEN_END+"|"+BLOCK_TOKEN_START+".{0,"+TOKEN_MAX_SIZE+"}?"+BLOCK_TOKEN_END+")";
  }
  
  public Token[] tokenize(String s){       
    String[] tokens_s = s.split(this.pattern);
    Token[] tokens = new Token[tokens_s.length];
    for(int i=0; i<tokens_s.length; i++) {
      tokens[i] = new Token(tokens_s[i]);
    }
    
    return tokens;
  }
  
  public SyntaxTree buildTree(Token[] tokens) {    
    SyntaxTree currentNode = new Root();
    for(Token t : tokens) {
      if(t.type == Token.VAR_TOKEN) {
        currentNode.addNode(new Variable(t));
      } else if(t.type == Token.TEXT_TOKEN) {
        currentNode.addNode(new Text(t));
      } else if (t.type == Token.IF_OPEN_TOKEN) {
        SyntaxTree newNode = new IfNode(t, currentNode);
        currentNode.addNode(newNode);
        currentNode = newNode;
      } else if (t.type == Token.IF_ELSE_TOKEN) {
        IfNode p = (IfNode)(currentNode);
        p.startElseBlock();
      } else if (t.type == Token.IF_CLOSE_TOKEN) {
        currentNode = currentNode.parent;
      }
    }
    
    return currentNode;
    
  }
  
  public String render(String template, Context context) {
    SyntaxTree st = buildTree(tokenize(template));
    return st.render(context);
  }
  
  
}