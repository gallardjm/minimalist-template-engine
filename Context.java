package minimalistTemplateEngine;

import java.util.Map;
import java.util.HashMap;
import minimalistTemplateEngine.contextbox.*;

public class Context {
   
  private Map<String, ContextBox> map;
  
  public Context() {
    map = new HashMap<String, ContextBox>();
  }
  
  public void put(String key, String s) {
    map.put(key, new StringBox(s));
  }
  
  public void put(String key, boolean b) {
    map.put(key, new BooleanBox(b));
  }
  
  public String getString(String key) {
    ContextBox b = map.get(key);
    if(b != null && b instanceof StringBox) {
      return ((StringBox)b).value;
    } else {
      //ERROR
      return "";
    }
  }
  
  public boolean getBool(String key) {
    ContextBox b = map.get(key);
    if(b != null && b instanceof BooleanBox) {
      return ((BooleanBox)b).value;
    } else {
      //ERROR
      return false;
    }
  }
  

}