/*
 * Copyright 2002-2017 the original  egan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.egzosn.infrastructure.params.enums;

/**
 * Created by egan on 2015/8/5.
 */
public enum AndOr {
    AND{
        @Override
        public String toMatchString(String propertyName, String pattern) {
            return String.format(" and %s%s ", propertyName, pattern);
        }
    }, OR{
        @Override
        public String toMatchString(String propertyName, String pattern) {
            return String.format(" or %s%s ", propertyName, pattern);
        }
    }, NUL{
        @Override
        public String toMatchString(String propertyName, String pattern) {
            return String.format(" %s%s ", propertyName, pattern);
        }
    };
    public abstract String toMatchString(String propertyName, String pattern);

}