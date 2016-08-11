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
class OptionTest extends GroovyTestCase {
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
        assert new Some<String>('abcde').substring(0, 2) == 'ab'
        assert new Some<Integer[]>(new Integer[0]).length == 0
        assert new Some<String>('abc') == new Some<String>('abc')
        assert !(new Some<String>('abc').$isEmpty())
        assert new HashSet([new Some<String>('abc'), new Some<String>('abc'), new Some<String>('abc')]) == new HashSet([new Some<String>('abc')])
        assert new Some<String>('abc').$getOrElse('def') == 'abc'
        assert new Some<String>('abc').$get() == 'abc'
    }

    void testOption() {
        switch (find('abc')) {
            case new Some<String>('abc'):
                assert true;
                break;
            case None.instance:
                assert false;
                break;
        }

        switch (find('def')) {
            case new Some<String>('def'):
                assert false;
                break;
            case None.instance:
                assert true;
                break;
        }
    }

    void testLoop() {
        def sb = new StringBuilder();
        for (x in new Some<List>([1, 2, 3])) {
            sb << x;
        }
        assert '123' == sb.toString();

        for (x in None.instance) {
            assert false;
        }
    }

    private static Option<String> find(String str) {
        if ('abc' == str) {
            return new Some<String>('abc');
        } else {
            return None.instance;
        }
    }
}
