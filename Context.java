package minimalistTemplateEngine;

import java.util.HashMap;

/**
 * Context class
 *
 * Act like a Map<String, Multitype> to store a value (different types allowed) associated to a key
 * Type allowed: String, boolean
 * A key can have multiple values if each value has a different type
 *
 * Use put(key, value) to add a key and its value to the context.
 * Use getX(key) to get back a value of type X identified by the key
 */
public class Context {
  
  // Store the keys->value pair in a the map corresponding to the right type for the value
  private HashMap<String, String>  mapString;
  private HashMap<String, Boolean> mapBoolean; //auto cast, boolean (primitive) <-> Boolean (object)
  
  public Context() {
    mapString  = new HashMap<String, String>();
    mapBoolean = new HashMap<String, Boolean>();
  }
  
  /** Put a String in the context */
  public void put(String key, String value) {
    mapString.put(key, value);
  }
  
  /** Put a boolean in the context */
  public void put(String key, boolean b) {
    mapBoolean.put(key, b);
  }
  
  /** Get a String from the context */
  public String getString(String key) {
    if(mapString.containsKey(key)) {
      return mapString.get(key);
    } else {
      //ERROR
      return "";
    }
  }
  
  /** Get a boolean from the context */
  public boolean getBool(String key) {
    if(mapBoolean.containsKey(key)) {
      return mapBoolean.get(key);
    } else {
      //ERROR
      return false;
    }
  }
  

}