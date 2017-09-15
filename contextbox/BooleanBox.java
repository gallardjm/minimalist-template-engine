package minimalistTemplateEngine.contextbox;

/** Box for Boolean */
public class BooleanBox implements ContextBox {
  public boolean value;
  
  public BooleanBox(boolean b) {
    value = b;
  }
}