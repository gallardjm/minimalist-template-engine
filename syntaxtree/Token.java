package minitemp.syntaxtree;

import java.util.ArrayList;
import java.lang.StringBuilder;

import minitemp.TemplateEngine;

public class Token {
  
  public static final int VAR_TOKEN       = 0; /** type of variable grammar token*/
  public static final int TEXT_TOKEN      = 1; /** type of text token*/
  public static final int IF_OPEN_TOKEN   = 2; /** type of logic grammar token: if */
  public static final int IF_ELSE_TOKEN   = 3; /** type of logic grammar token: else */
  public static final int IF_CLOSE_TOKEN  = 4; /** type of logic grammar token: endif */
  public static final int FOR_OPEN_TOKEN  = 5; /** type of logic grammar token: for */
  public static final int FOR_CLOSE_TOKEN = 6; /** type of logic grammar token: endfor */
  
  
  public int type = -1; /** type of token, matched with the constant, by default -1 = invalid */
  private String rawContent; /** the token full string */
  
  
  public Token(String tokenAsString) {
    rawContent = tokenAsString;
    defineType();
  }
  
  
  private void defineType() {
    if(rawContent.startsWith(TemplateEngine.VAR_TOKEN_START)) {
      type = VAR_TOKEN;
    } else if(rawContent.startsWith(TemplateEngine.BLOCK_TOKEN_START)) {
      String tag = rawContent;
      if(tag.startsWith(TemplateEngine.STRIP_BLOCK_TOKEN_START)) {
        tag = tag.substring(TemplateEngine.STRIP_BLOCK_TOKEN_START.length()).trim();
      } else {
        tag = tag.substring(TemplateEngine.BLOCK_TOKEN_START.length()).trim();
      }
      if(tag.startsWith(TemplateEngine.LOGIC_ENDIF_TAG)) {
        type = IF_CLOSE_TOKEN;
      } else if(tag.startsWith(TemplateEngine.LOGIC_ELSE_TAG)) {
        type = IF_ELSE_TOKEN;
      } else if(tag.startsWith(TemplateEngine.LOGIC_IF_TAG)) {
        type = IF_OPEN_TOKEN;
      } else if(tag.startsWith(TemplateEngine.LOGIC_FOR_TAG)) {
        type = FOR_OPEN_TOKEN;
      } else if(tag.startsWith(TemplateEngine.LOGIC_ENDFOR_TAG)) {
        type = FOR_CLOSE_TOKEN;
      }
    } else {
      type = TEXT_TOKEN;
    }
  }
  
  @Override
  public String toString() {
    return type+" | \""+rawContent+"\"";
  }
  
  /**
   * Return the clean content of a token
   * Text token: everything in the token
   * Grammar token: the inside of the delimiters - tag + trim()
   */
  public String getContentClean() {
    if(type == VAR_TOKEN || type == IF_ELSE_TOKEN 
        || type == IF_CLOSE_TOKEN || type == FOR_CLOSE_TOKEN) {
      String clean = rawContent.substring(TemplateEngine.BLOCK_TOKEN_START.length());
      clean = clean.substring(0, clean.length()-TemplateEngine.BLOCK_TOKEN_END.length());
      return clean.trim();
    } else if (type == TEXT_TOKEN) {
      return rawContent;
    } else if(type == IF_OPEN_TOKEN || type == FOR_OPEN_TOKEN) {
      String clean = rawContent;
      if(clean.startsWith(TemplateEngine.STRIP_BLOCK_TOKEN_START)) 
      {
        clean = clean.substring(TemplateEngine.STRIP_BLOCK_TOKEN_START.length());
      } else {
        clean = clean.substring(TemplateEngine.BLOCK_TOKEN_START.length());
      }
      clean = clean.substring(0, clean.length()-TemplateEngine.BLOCK_TOKEN_END.length());
      //remove the tag
      if(type == IF_OPEN_TOKEN) {
        clean = clean.trim().substring(TemplateEngine.LOGIC_IF_TAG.length()).trim(); 
      } else if(type == FOR_OPEN_TOKEN) {
        clean = clean.trim().substring(TemplateEngine.LOGIC_FOR_TAG.length()).trim(); 
      }  
      return clean;
    }
    
    return "";
  }
  
  /**
   * Breaks a string into tokens using a given regex.
   * Apply strip token logic before building the token so return value != input splitted
   *
   * @param the template to evaluate as String
   * @param the regex used to split the template into tokens
   * @return the string splitted into token
   */
  static public Token[] tokenize(String template, String regex){
    String[] tokens_splitted = template.split(regex); //split the template around delimiters
    
    //assemble the tokens
    ArrayList<String> tokens_assembled = new ArrayList<String>(tokens_splitted.length/2+10); //initial guess
    StringBuilder chunk = null;
    boolean inChunk = false;
    for(int i=0; i<tokens_splitted.length; i++) {
      switch(tokens_splitted[i]){
        case TemplateEngine.VAR_TOKEN_START:
            inChunk = true;
            chunk = new StringBuilder(TemplateEngine.VAR_TOKEN_START);
            break;
        case TemplateEngine.BLOCK_TOKEN_START:
            inChunk = true;
            chunk = new StringBuilder(TemplateEngine.BLOCK_TOKEN_START);
            break;
        case TemplateEngine.VAR_TOKEN_END:
            inChunk = false;
            chunk.append(TemplateEngine.VAR_TOKEN_END);
            tokens_assembled.add(chunk.toString());
            break;
        case TemplateEngine.BLOCK_TOKEN_END:
            inChunk = false;
            chunk.append(TemplateEngine.BLOCK_TOKEN_END);
            tokens_assembled.add(chunk.toString());
            break;
        default:
          if(inChunk) {
            chunk.append(tokens_splitted[i]);
          } else {
            tokens_assembled.add(tokens_splitted[i]);
          }
      }
    }
    
    String[] tokens_raw = new String[tokens_assembled.size()];
    tokens_raw = tokens_assembled.toArray(tokens_raw);
    
    //apply strip block
    for(int i=0; i<tokens_raw.length; i++) {
      if(tokens_raw[i].startsWith(TemplateEngine.STRIP_BLOCK_TOKEN_START)) {
        if(i>0) {
          //remove trailing vertical whitespace from previous token
          tokens_raw[i-1] = tokens_raw[i-1].replaceAll("\\h*$", ""); 
        }
        if(i<tokens_raw.length-1) {
          //remove leading whitespace + newline from next token
          tokens_raw[i+1] = tokens_raw[i+1].replaceAll("^\\h*\\R?", "");
        }
      }
    }
    
    //build Token object
    Token[] tokens = new Token[tokens_raw.length];
    for(int i=0; i<tokens_raw.length; i++) {
      tokens[i] = new Token(tokens_raw[i]);
    }
    
    return tokens;
  }
  
}
