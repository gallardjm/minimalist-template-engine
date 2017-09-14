package minimalistTemplateEngine;

public class TemplateEngine {
  
  public static String VAR_TOKEN_START   = "{{";
  public static String VAR_TOKEN_END     = "}}";
  public static String BLOCK_TOKEN_START = "{%";
  public static String BLOCK_TOKEN_END   = "%}";
  public static String STRIP_BLOCK_TOKEN_START = "{%-";
  private int TOKEN_MAX_SIZE = 99999;
  
  private String pattern;
  
  public TemplateEngine() {
    String tokenPattern = escRegex(VAR_TOKEN_START)+".{0,"+TOKEN_MAX_SIZE+"}?"+escRegex(VAR_TOKEN_END)+"|"+escRegex(BLOCK_TOKEN_START)+".{0,"+TOKEN_MAX_SIZE+"}?"+escRegex(BLOCK_TOKEN_END);
    this.pattern= "(?="+tokenPattern+")|(?<="+tokenPattern+")";
  }
  
  //escape regex special char '{' and '}'
  private String escRegex(String s) {
    return s.replaceAll("\\{", "\\\\{").replaceAll("\\}", "\\\\}");
  }
  
  public Token[] tokenize(String s){       
    String[] tokens_s = s.split(this.pattern);
    
    //apply strip block
    for(int i=0; i<tokens_s.length; i++) {
      if(tokens_s[i].startsWith(STRIP_BLOCK_TOKEN_START)) {
        if(i>0) {
          tokens_s[i-1] = tokens_s[i-1].replaceAll("\\h*$", ""); //remove trailing vertical whitespace from previous token
        }
        if(i<tokens_s.length-1) {
          tokens_s[i+1] = tokens_s[i+1].replaceAll("^\\h*\\R?", ""); //remove leading whitespace + newline from next token
        }
      }
    }
    
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