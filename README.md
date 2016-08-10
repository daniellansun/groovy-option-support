# groovy-option-support [![Build Status](https://travis-ci.org/danielsun1106/groovy-option-support.svg?branch=master)](https://travis-ci.org/danielsun1106/groovy-option-support)
Make groovy support scala-like option to avoid NPE

When some methods can return null, we had better to indicate the NPE risk using the Option type. For example:
```groovy
Option<String> find(String str) {
    if ('abc' == str) {
        return new Some<String>('abc'); // found
    } else {
        return None.instance; // not found, returning null before, which is replaced by the None instance
    }
}

switch (find('abc')) {
    case new Some<String>('abc'):
        assert true;
        break;
    case None.instance:
        assert false;
        break;
}
```