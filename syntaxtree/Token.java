package minimalistTemplateEngine.syntaxtree;

import minimalistTemplateEngine.TemplateEngine;

public class Token {
  public static int VAR_TOKEN = 0;
  public static int TEXT_TOKEN = 1;
  
  public static int IF_OPEN_TOKEN = 2;
  public static int IF_CLOSE_TOKEN = 4;
  public static int IF_ELSE_TOKEN = 3;
  
  public int type = -1;
  private String content;
  
  public Token(String s) {
    content = s;
    if(s.startsWith(TemplateEngine.VAR_TOKEN_START)) {
      type = VAR_TOKEN;
    } else if(s.startsWith(TemplateEngine.BLOCK_TOKEN_START)) {
      String tag = s;
      if(tag.startsWith(TemplateEngine.STRIP_BLOCK_TOKEN_START)) 
      {
        tag = tag.substring(TemplateEngine.STRIP_BLOCK_TOKEN_START.length()).trim();
      } else {
        tag = tag.substring(TemplateEngine.BLOCK_TOKEN_START.length()).trim();
      }
      if(tag.startsWith("endif")) {
        type = IF_CLOSE_TOKEN;
      } else if(tag.startsWith("else")) {
        type = IF_ELSE_TOKEN;
      } else if(tag.startsWith("if")){
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
      String clean = content.substring(TemplateEngine.BLOCK_TOKEN_START.length());
      clean = clean.substring(0, clean.length()-TemplateEngine.BLOCK_TOKEN_END.length());
      return clean.trim();
    } else if (type == TEXT_TOKEN) {
      return content;
    } else if(type == IF_OPEN_TOKEN) {
      String clean = content;
      if(clean.startsWith(TemplateEngine.STRIP_BLOCK_TOKEN_START)) 
      {
        clean = clean.substring(TemplateEngine.STRIP_BLOCK_TOKEN_START.length());
      } else {
        clean = clean.substring(TemplateEngine.BLOCK_TOKEN_START.length());
      }
      clean = clean.substring(0, clean.length()-TemplateEngine.BLOCK_TOKEN_END.length());
      clean = clean.trim().substring(2).trim(); //remove the if
      return clean;
    }
    
    return "";
  }
}