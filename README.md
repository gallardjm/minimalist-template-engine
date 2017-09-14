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

* Variable ```{{ var }}```
  - string only
* Branch ```{% if condition %} {% else %} {% endif %}```
  - condition must be a boolean
  - nested branch supported
