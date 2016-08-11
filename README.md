# groovy-option-support [![Build Status](https://travis-ci.org/danielsun1106/groovy-option-support.svg?branch=master)](https://travis-ci.org/danielsun1106/groovy-option-support)
Make groovy support scala-like option to avoid NPE

When some methods can return null, we had better to indicate the NPE risk using the Option type.

In addition, we can treat the Some object as the original object. In other words, we can access any method and property of the original object via the Some object. In order to avoid Option class having same method name with the original class, the method names of Option class starts with a dollar character.

For example:
```groovy

// examples for Option
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


// examples for Some
assert new Some<String>('abcde').substring(0, 2) == 'ab'
assert new Some<Integer[]>(new Integer[0]).length == 0
assert new Some<String>('abc') == new Some<String>('abc')
assert !(new Some<String>('abc').$isEmpty())
assert new HashSet([new Some<String>('abc'), new Some<String>('abc'), new Some<String>('abc')]) == new HashSet([new Some<String>('abc')])
assert new Some<String>('abc').$getOrElse('def') == 'abc'
assert new Some<String>('abc').$get() == 'abc'

try {
    new Some<String>(null);
    assert false;
} catch (IllegalArgumentException e) {
    assert true;
}


// examples for None
assert None.instance == None.instance
assert None.instance.is(None.instance)
assert None.instance.$isEmpty()
assert new HashSet([None.instance, None.instance, None.instance]) == new HashSet([None.instance])
assert None.instance.$getOrElse('abc') == 'abc'

try {
    None.instance.$get();
    assert false;
} catch (NoSuchElementException e) {
    assert true;
}


// examples for loop
def sb = new StringBuilder();
for (x in new Some<List>([1, 2, 3])) {
    sb << x;
}
assert '123' == sb.toString();

// empty loop
for (x in None.instance) {
    assert false;
}
```