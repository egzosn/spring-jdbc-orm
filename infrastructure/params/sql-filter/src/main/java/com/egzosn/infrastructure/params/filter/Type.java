/*
 * Copyright 2002-2020 the original  egan.
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

package com.egzosn.infrastructure.params.filter;

import java.lang.ref.SoftReference;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by egan on 2015/12/1.
 * 类型简化集
 */
public enum  Type {

    i {
        public Object parse(String parse) {
           return Integer.parseInt(parse);
        }
    }, f {
        public Object parse(String parse) {
           return Float.parseFloat(parse);
        }
    }, d {
        public Object parse(String parse) {
           return Double.parseDouble(parse);
        }
   }, s {
        public Object parse(String parse) {
           return Short.parseShort(parse);
        }
    }, S {
        public Object parse(String parse) {
           return parse;
        }
   }, D {
        public Object parse(String parse) {
            if (parse.contains(" ")){
                return Type.parseDate(parse);
            }
            return Type.parseDay(parse);

        }
  }, b {
        public Object parse(String parse) {
           return Boolean.parseBoolean(parse);
        }
  }, bd {
        public Object parse(String parse) {
           return new BigDecimal(parse);
        }
    };


    static final class DateFormatHolder {
        private static final ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>> THREADLOCAL_FORMATS = new ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>>();

        DateFormatHolder() {
        }

        public static SimpleDateFormat formatFor(String pattern) {
            SoftReference<Map<String, SimpleDateFormat>> ref = THREADLOCAL_FORMATS.get();
            Map<String, SimpleDateFormat> formats = ref == null ? null : ref.get();
            if (formats == null) {
                formats = new HashMap<String, SimpleDateFormat>();
                THREADLOCAL_FORMATS.set(new SoftReference(formats));
            }

            SimpleDateFormat format =  formats.get(pattern);

            if (format == null) {
                format = new SimpleDateFormat(pattern);
                format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                ((Map) formats).put(pattern, format);
            }

            return format;
        }

        public static void clearThreadLocal() {
            THREADLOCAL_FORMATS.remove();
        }
    }
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat formatFor = DateFormatHolder.formatFor(pattern);
        return formatFor.format(date);
    }
    public static Date parseDate(String date, String pattern) {

        SimpleDateFormat formatFor = DateFormatHolder.formatFor(pattern);
        try {
            return formatFor.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public abstract Object parse(String parse);
    public static Date parseDate(String date) {
        return parseDate(date, YYYY_MM_DD_HH_MM_SS);
    }
    public static final String format(Date date) {
        return formatDate(date, YYYY_MM_DD_HH_MM_SS);
    }

    public static final Date parseDay(String date) {
        return parseDate(date, YYYY_MM_DD);
    }

    public static final String formatDay(Date date) {
        return formatDate(date, YYYY_MM_DD);
    }

}
