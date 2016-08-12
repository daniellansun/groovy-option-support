/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package groovy.lang
/**
 * Created by Daniel.Sun on 2016/8/10.
 */
public class OptionTest extends GroovyTestCase {
    void testNone() {
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
    }

    void testSome() {
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
    }

    void testOption() {
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

        assert Option.$new(null).$map { it + 2 } == None.instance
        assert Option.$new(1).$map { it + 2 } == Option.$new(3)

        assert Option.$new(null).$isEmpty()
        assert !Option.$new('123').$isEmpty()

        assert !Option.$new(null).$isDefined()
        assert Option.$new('123').$isDefined()

        assert Option.$new(null).$orElse(Option.$new('123')) == Option.$new('123')
        assert Option.$new('123').$orElse(Option.$new('234')) == Option.$new('123')

        assert Option.$new(null).$orNull() == null
        assert Option.$new('123').$orNull() == '123'

        assert Option.$new(Option.$new('123')) == Option.$new('123')
        assert Option.$new(Option.$new(null)) == Option.$new(null)

    }

    void testLoop() {
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

        for (x in None.instance) { // empty loop
            assert false;
        }
    }

    void testCollection() {
        assert [3, 4, 5] == Option.$new([1, 2, 3]).collect { it + 2 }
        assert [2, 3] == Option.$new([1, 2, 3]).grep { it > 1 }

        None.instance.each { assert false; }
    }

    void testObjectPath() {
        def p = Option.$new(' Hello, world ');
        assert 'HELLO, WORLD' == p.trim().toUpperCase()

        def n = Option.$new(null);
        assert None.instance == n.trim().toUpperCase()
    }

}
