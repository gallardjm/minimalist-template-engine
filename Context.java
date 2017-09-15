package minimalistTemplateEngine;

import java.util.Map;
import java.util.HashMap;
import minimalistTemplateEngine.contextbox.ContextBox;
import minimalistTemplateEngine.contextbox.StringBox;
import minimalistTemplateEngine.contextbox.BooleanBox;

/**
 * Context class
 *
 * Act like a Map<String, Multitype> to store a value (different types allowed) associated to a key
 *
 * Use put(key, value) to add a key and its value to the context. The value will be properly boxed
 * Use getX(key) to get back a value of type X identified by the key
 */
public class Context {
  
  /** Store the keys->value in a map using ContextBox abstraction to box multiple types in the map */  
  private Map<String, ContextBox> map;
  
  public Context() {
    map = new HashMap<String, ContextBox>();
  }
  
  /** Put a String in the context */
  public void put(String key, String s) {
    map.put(key, new StringBox(s));
  }
  
  /** Put a Boolean in the context */
  public void put(String key, boolean b) {
    map.put(key, new BooleanBox(b));
  }
  
  /** Get a String from the context */
  public String getString(String key) {
    ContextBox b = map.get(key);
    if(b != null && b instanceof StringBox) {
      return ((StringBox)b).value;
    } else {
      //ERROR
      return "";
    }
  }
  
  /** Get a Boolean from the context */
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