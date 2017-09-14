package minimalistTemplateEngine;

import java.util.Map;
import java.util.HashMap;

public class Context {
   
  private Map<String, Box> map;
  
  public Context() {
    map = new HashMap<String, Box>();
  }
  
  public void put(String key, String s) {
    map.put(key, new TextBox(s));
  }
  
  public void put(String key, boolean b) {
    map.put(key, new BooleanBox(b));
  }
  
  public String getString(String key) {
    Box b = map.get(key);
    if(b != null && b instanceof TextBox) {
      return ((TextBox)b).value;
    } else {
      //ERROR
      return "";
    }
  }
  
  public boolean getBool(String key) {
    Box b = map.get(key);
    if(b != null && b instanceof BooleanBox) {
      return ((BooleanBox)b).value;
    } else {
      //ERROR
      return false;
    }
  }
  
  
  private class Box {}
  
  private class TextBox extends Box {
    public String value;
    
    public TextBox(String s) {
      value = s;
    }
  }
  
  private class BooleanBox extends Box {
    public boolean value;
    
    public BooleanBox(boolean b) {
      value = b;
    }
  }
}