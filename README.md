# groovy-option-support [![Build Status](https://travis-ci.org/danielsun1106/groovy-option-support.svg?branch=master)](https://travis-ci.org/danielsun1106/groovy-option-support)
Make groovy support scala-like option to avoid NPE

When some methods can return null, we had better to indicate the NPE risk using the Option type.

In addition, we can treat the Some object as the original object. In other words, we can access any method and property of the original object via the Some object. In order to avoid Option class having same method names with the one of the original class, the method names of Option class start with a dollar character($).

For example:
```groovy

// examples for Option
Option find(String str) {
    if ('abc' == str) {
        return new Some('abc'); // found
    } else {
        return None.instance; // not found
    }
}

switch (find('abc')) {
    case new Some('abc'):
        assert true;
        break;
    case None.instance:
        assert false;
        break;
}

switch (find('def')) {
    case new Some('def'):
        assert false;
        break;
    case None.instance:
        assert true;
        break;
}

assert Option.$new(null).$isEmpty()
assert !Option.$new('123').$isEmpty()


// examples for Some
assert new Some('abcde').substring(0, 2) == 'ab'
assert new Some(new Integer[0]).length == 0
assert new Some('abc') == new Some('abc')
assert !new Some('abc').$isEmpty()
assert new HashSet([new Some('abc'), new Some('abc'), new Some('abc')]) == new HashSet([new Some('abc')])
assert new Some('abc').$getOrElse('def') == 'abc'
assert new Some('abc').$get() == 'abc'
assert new Some([1, 2, 3])[0] == 1

try {
    new Some(null);
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
for (x in new Some([1, 2, 3])) {
    sb << x;
}
assert '123' == sb.toString();

sb = new StringBuilder();
new Some([1, 2, 3]).each {
    sb << it;
}
assert '123' == sb.toString();

assert [3, 4, 5] == new Some([1, 2, 3]).collect { it + 2 }
assert [2, 3] == new Some([1, 2, 3]).grep { it > 1 }

for (x in None.instance) { // empty loop
    assert false;
}
```