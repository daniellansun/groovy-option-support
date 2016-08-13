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
 * Class Some<T> represents existing values of type T.
 *
 * Created by Daniel.Sun on 2016/8/10.
 */
public class Some<T> extends Option<T> {
    private final T value;

    private Some(T value) {
        this.value = value;
    }

    /**
     * Creates a Some instance
     *
     * @param value the value of Some instance
     * @return a Some instance
     */
    public static Some<T> newInstance(T value) {
        if (null == value) {
            throw new IllegalArgumentException("The argument value should not be null.");
        }

        if (None.instance == value) {
            throw new IllegalArgumentException("The argument value should not be none.");
        }

        if (value instanceof Some) {
            return (Some<T>) value;
        }

        return new Some<T>(value);
    }

    @Override
    public T $get() {
        return this.value;
    }

    @Override
    public boolean $isEmpty() {
        return false;
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Some)) {
            return false;
        }

        return this.value.equals(((Some) other).value);
    }

    @Override
    public String toString() {
        return "Some[$value]";
    }

    @Override
    public Iterator<T> iterator() {
        return this.value.iterator();
    }

    def methodMissing(String name, args) {
        return this.value."$name"(*args);
    }

    def propertyMissing(String name) {
        return this.value."$name";
    }
}
