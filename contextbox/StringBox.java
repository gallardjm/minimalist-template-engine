package minimalistTemplateEngine.contextbox;

/** Box for String */
public class StringBox implements ContextBox {
  public String value;
  
  public StringBox(String s) {
    value = s;
  }
}
