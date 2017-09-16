package minimalistTemplateEngine;

import java.util.HashMap;

/**
 * Context class
 *
 * Act like a Map<String, Multitype> to store a value (different types allowed) associated to a key
 * Type allowed: String, boolean
 * A key can have only one value, redefining it (even with a value of a different type) override the
 * previous value
 *
 * Use put(key, value) to add a key and its value to the context.
 * Use getX(key) to get back a value of type X identified by the key
 */
public class Context {
  
  // Store the keys->value pair in a the map corresponding to the right type for the value
  private HashMap<String, String>  mapString;
  private HashMap<String, Boolean> mapBoolean; //auto cast, boolean (primitive) <-> Boolean (object)
  
  /** Map of the key -> type of the value, used to ensure key uniqueness */
  private HashMap<String, Integer> mapKey; 
  private static final int STRING_MAP = 0;
  private static final int BOOLEAN_MAP = 1;
  
  public Context() {
    mapString  = new HashMap<String, String>();
    mapBoolean = new HashMap<String, Boolean>();
    
    mapKey = new HashMap<String, Integer>();
  }
  
  /** Put a String in the context */
  public void put(String key, String value) {
    ensureUniqueness(key);
    mapString.put(key, value);
    mapKey.put(key, STRING_MAP);
  }
  
  /** Put a boolean in the context */
  public void put(String key, boolean b) {
    ensureUniqueness(key);
    mapBoolean.put(key, b);
    mapKey.put(key, BOOLEAN_MAP);
  }
  
  /** Get a String from the context */
  public String getString(String key) throws IllegalArgumentException {
    if(mapString.containsKey(key)) {
      return mapString.get(key);
    } else {
      throw new IllegalArgumentException(
          "Context doesn't contain a String value for the key \""+key+"\"");
    }
  }
  
  /** Get a boolean from the context */
  public boolean getBool(String key) throws IllegalArgumentException {
    if(mapBoolean.containsKey(key)) {
      return mapBoolean.get(key);
    } else {
      throw new IllegalArgumentException(
          "Context doesn't contain a boolean value for the key \""+key+"\"");
    }
  }
  
  /** Ensure the uniqueness of a key by deleting previous value if the key was already used */
  private void ensureUniqueness(String key) {
    if(mapKey.containsKey(key)) {
      int mapType = mapKey.get(key);
      if(mapType == STRING_MAP) {
        mapString.remove(key);
      } else if(mapType == BOOLEAN_MAP) {
        mapBoolean.remove(key);
      }
      mapKey.remove(key);
    }
  }
  

}