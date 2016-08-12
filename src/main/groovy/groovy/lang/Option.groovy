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
 * Represents optional values. Instances of Option are either an instance of groovy.lang.Some or the singleton groovy.lang.None.
 *
 * Created by Daniel.Sun on 2016/8/10.
 */
public abstract class Option<T> implements Iterable<T>, Serializable {

    /**
     * Returns true if the option is None, false otherwise.
     *
     * @return true if the option is None, false otherwise.
     */
    public abstract boolean $isEmpty();

    /**
     * Returns true if the option is an instance of Some, false otherwise.
     *
     * @return true if the option is an instance of Some, false otherwise.
     */
    public boolean $isDefined() {
        return !this.$isEmpty();
    }

    /**
     * Returns the option's value
     *
     * @return the option's value
     */
    public abstract T $get();

    /**
     * Returns the option's value if the option is nonempty, otherwise return the result of evaluating default.
     *
     * @param dflt the default value
     * @return the option's value if the option is nonempty, otherwise return the result of evaluating default.
     */
    public T $getOrElse(T dflt) {
        return this.$isEmpty() ? dflt : this.$get();
    }

    /**
     * Creates an Option instance
     *
     * @param obj the option's value
     * @return the Option instance
     */
    public static <T> Option<T> $new(T obj) {
        return (null == obj || None.instance == obj) ? None.instance : Some.newInstance(obj);
    }

    /**
     * Returns this Option if it is nonempty, otherwise return the result of evaluating alternative.
     *
     * @param alternative the default Option instance
     * @return this Option if it is nonempty, otherwise return the result of evaluating alternative.
     */
    public Option<T> $orElse(Option<T> alternative) {
        return this.$isEmpty() ? alternative : this;
    }

    /**
     * Returns the option's value if it is nonempty, or null if it is empty.
     *
     * @return the option's value if it is nonempty, or null if it is empty.
     */
    public T $orNull() {
        return this.$isEmpty() ? null : this.$get();
    }

    /**
     * Chooses one of the closures to call according to the Option
     *
     * @param some the closure will be called when the Option is nonempty
     * @param none the closure will be called when the Option is empty
     * @return the call result of some/none closure
     */
    public Object $switch(Closure some, Closure none) {
        return this.$isEmpty() ? none.call(null) : some.call(this.$get());
    }

    /**
     * Returns a Some instance containing the result of applying c to this Option's value if this Option is nonempty. Otherwise return None.
     *
     * @param c a closure to convert the option's value
     * @return a Some instance containing the result of applying c to this Option's value if this Option is nonempty. Otherwise return None.
     */
    public Option<T> $map(Closure c) {
        if (this.$isEmpty()) {
            return None.instance;
        }

        return this.$new(c.call(this.$get()));
    }
}
