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
    private T obj;

    private Some(T obj) {
        this.obj = obj;
    }

    /**
     * Creates a Some instance
     *
     * @param obj the value of Some instance
     * @return a Some instance
     */
    public static Some<T> newInstance(T obj) {
        if (null == obj) {
            throw new IllegalArgumentException("The argument obj should not be null.");
        }

        if (None.instance == obj) {
            throw new IllegalArgumentException("The argument obj should not be none.");
        }

        if (obj instanceof Some) {
            return (Some<T>) obj;
        }

        return new Some<T>(obj);
    }

    @Override
    public T $get() {
        return obj;
    }

    @Override
    public boolean $isEmpty() {
        return false;
    }

    @Override
    public int hashCode() {
        return obj.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Some)) {
            return false;
        }

        return this.obj.equals(((Some) other).obj);
    }

    @Override
    public String toString() {
        return "Some[$obj]";
    }

    @Override
    public Iterator<T> iterator() {
        return obj.iterator();
    }

    def methodMissing(String name, args) {
        return obj."$name"(*args);
    }

    def propertyMissing(String name) {
        return obj."$name";
    }
}
