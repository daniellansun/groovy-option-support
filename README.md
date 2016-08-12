# groovy-option-support [![Build Status](https://travis-ci.org/danielsun1106/groovy-option-support.svg?branch=master)](https://travis-ci.org/danielsun1106/groovy-option-support)
Make groovy support scala-like option to avoid NPE

When some methods can return null, we had better to indicate the NPE risk using the Option type.

In addition, we can treat the Some object as the original object. In other words, we can access any method and property of the original object via the Some object. In order to avoid Option class having same method names with the one of the original class, the method names of Option class start with a dollar character($).

For example(more examples can be found at [here](https://github.com/danielsun1106/groovy-option-support/blob/master/src/test/groovy/groovy/lang/OptionTest.groovy)):
```groovy
def m = new HashMap() {
    {
        this.putAll([a: 1, b: 2, c: 3]);
    }

    @Override
    public Option get(Object key) {
        return Option.$new(super.get(key));
    }
}

def matchResult = m.get('b').$match {
    // if result is of type Some, do something here
    return it + 8; // b:2 + 8
} {
    // if result is of type None, do something here
    return 0;
}
assert matchResult == 10;
```