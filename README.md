# groovy-option-support [![Build Status](https://travis-ci.org/danielsun1106/groovy-option-support.svg?branch=master)](https://travis-ci.org/danielsun1106/groovy-option-support)
Make groovy support scala-like option to avoid NPE

## Introduction
When some methods can return null, we had better to indicate the NPE risk using the Option instance, which can be created via calling `Option.$new(nullable)`.

In addition, we can treat the Some object as the original object. In other words, we can access any method and property of the original object via the Some object. In order to avoid Option class having same method names with the one of the original class, the method names of Option class start with a dollar character($).

## Examples:
```groovy
// HashMap's get method will return null when no entity found, so we enhance it by wrapping the result via $new
def m = new HashMap() {
    {
        putAll([a: 1, b: 2, c: 3]); // initialize the map
    }

    @Override
    public Option get(Object key) {
        return Option.$new(super.get(key));
    }
}

m.get('b').$switch {
    println it;
} {
    println 0;
}

```

The equivalent scala code is shown as follows:
```scala
val m = Map("a" -> 1, "b" -> 2, "c" -> 3)

m.get("b") match {
    case Some(it) => println(it)
    case None => println(0)
}
```

#### More examples can be found at [here](https://github.com/danielsun1106/groovy-option-support/blob/master/src/test/groovy/groovy/lang/OptionTest.groovy)

## Installation
```groovy
apply plugin: 'groovy'

repositories {
    maven { url 'https://dl.bintray.com/danielsun1106/generic/' }
    jcenter()
}

dependencies {
    compile 'com.groovyhelp:groovy-option-support:1.0.0'
    compile 'org.codehaus.groovy:groovy-all:2.4.7'
}

```
