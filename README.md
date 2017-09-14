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

### Text replacement

Delimiters: ```{{``` and ```}}```.

* Variable ```{{ foo }}```
  - string only

### Logic  

Delimiters: ```{%``` and ```%}```.

* Branch ```{% if foo %} {% else %} {% endif %}```
  - foo must be a boolean
  
Nested logic is supported.

### Misc

#### Strip logic block

With ```{%-``` as start delimiter of a logic token, strip all whitespaces before the token + all whitespace and one newline if present after the token. This allow more readable templates without unecessary whitespaces and newlines in the result.

Example: ``` foo \n {%- x %}   \n bar``` is evaluated as ``` foo \n{% x %} bar```. 

### Future features

The foolowing features are considered:

Text replacement:

* Arrays ```{{ var[i] }}```
* Mathematical expression ```{{ 3*var**2+1}}```

Logic:

* Foreach loop ```{% for i in list %}```
* Boolean expression ```{% if (a && b) || (c == 1) %}```

