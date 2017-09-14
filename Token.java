package minimalistTemplateEngine;


public class Token {
  public static int VAR_TOKEN = 0;
  public static int TEXT_TOKEN = 1;
  
  public static int IF_OPEN_TOKEN = 2;
  public static int IF_CLOSE_TOKEN = 4;
  public static int IF_ELSE_TOKEN = 3;
  
  public int type = -1;
  public String content;
  
  public Token(String s) {
    content = s;
    if(s.startsWith("{{")) {
      type = VAR_TOKEN;
    } else if(s.startsWith("{%")) {
      if(s.substring(2).trim().startsWith("endif")) {
        type = IF_CLOSE_TOKEN;
      } else if(s.substring(2).trim().startsWith("else")) {
        type = IF_ELSE_TOKEN;
      } else if(s.substring(2).trim().startsWith("if")){
        type = IF_OPEN_TOKEN;
      }
    } else {
      type = TEXT_TOKEN;
    }
  }
  
  @Override
  public String toString() {
    return type+" | \""+content+"\"";
  }
  
  public String getContentClean() {
    if(type == VAR_TOKEN || type == IF_ELSE_TOKEN || type == IF_CLOSE_TOKEN) {
      String clean = content.substring(2);
      clean = clean.substring(0, clean.length()-2);
      return clean.trim();
    } else if (type == TEXT_TOKEN) {
      return content;
    } else if(type == IF_OPEN_TOKEN) {
      String clean = content.substring(2);
      clean = clean.substring(0, clean.length()-2);
      clean = clean.trim().substring(2).trim();
      return clean;
    }
    
    return "";
  }
}