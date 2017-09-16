# Minimalist Template Engine

A minimalist template engine in java with no dependency.

## Quick start

Hello World!

    String template = "Hello {{place}}{% if excited %}!{% if mad %}!!!1!!{% endif %}{% else %}.{% endif %}";

    TemplateEngine engine = new TemplateEngine();
    Context context = new Context();
    context.put("place", "world");
    context.put("excited", true);
    context.put("mad", false);

    String result = engine.render(template, context);

## Supported template syntax

Context's keys have to be a valid variable name (only characters from [a-zA-Z_0-9], at least one)

The Context uses a Javascript engine (from the package javax.script) to evaluate expression in the grammar tokens.

### Text replacement

Delimiters: ```{{``` and ```}}```.

* Variable ```{{ foo }}```
  - foo is any expression (javascript syntax) resulting in a String, an int or a double

### Logic  

Delimiters: ```{%``` and ```%}```.

* Branch ```{% if foo %} {% else %} {% endif %}```
  - foo is any boolean expression (javascript syntax)
  
Nested logic is supported.

### Misc

#### Strip logic block

With ```{%-``` as start delimiter of a logic token, strip all whitespaces before the token + all whitespace and one newline if present after the token. This allow more readable templates without unecessary whitespaces and newlines in the result.

Example: ``` foo \n {%- x %}   \n bar``` is evaluated as ``` foo \n{% x %} bar```. 

### Future features

The foolowing features are considered:

Text replacement:

* Arrays ```{{ var[i] }}```

Logic:

* Foreach loop ```{% for i in list %}```

