/*
 * Copyright (c) 2016, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 * <p>
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.ibus.mediation.cheetah.flow.contentAware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.ibus.mediation.cheetah.flow.contentAware.abstractContext.TypeConverter;
import org.wso2.carbon.ibus.mediation.cheetah.flow.contentAware.abstractContext.TypeConverterRegistry;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Base implementation of a type converter registry
 */
public class BaseTypeConverterRegistry implements TypeConverterRegistry {

    protected final ConcurrentMap<TypeMapper, TypeConverter> typeMapping = new ConcurrentHashMap<TypeMapper, TypeConverter>();
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private static BaseTypeConverterRegistry baseTypeConverterRegistry;

    public static BaseTypeConverterRegistry getInstance() {
        if (baseTypeConverterRegistry == null) {
            baseTypeConverterRegistry = new BaseTypeConverterRegistry();
        }
        return baseTypeConverterRegistry;
    }

    @Override public void addTypeConverter(Class<?> toType, Class<?> fromType, TypeConverter typeConverter) {
        log.trace("Adding type converter: {}", typeConverter);
        TypeMapper key = new TypeMapper(toType.getName(), fromType.getName());
        TypeConverter converter = typeMapping.get(key);
        if (typeConverter != converter) {
            //boolean add = true;
            if (converter == null) {
                typeMapping.put(key, typeConverter);
            } else {
                //different converter exist
            }
        }
    }

    @Override public void addTypeConverter(String targetType, String sourceType, TypeConverter typeConverter) {

    }

    @Override public boolean removeTypeConverter(Class<?> toType, Class<?> fromType) {
        log.trace("Removing type converter from: {} to: {}", fromType, toType);
        TypeMapper key = new TypeMapper(toType.getName(), fromType.getName());
        TypeConverter converter = typeMapping.remove(key);
        if (converter != null) {
            typeMapping.remove(key);
        }
        return converter != null;

    }

    @Override public void addFallbackTypeConverter(TypeConverter typeConverter, boolean canPromote) {

    }

    /*public TypeConverter getTypeConverter(Class<?> targetType, Class<?> sourceType) {
        TypeMapper key = new TypeMapper(targetType.getName(), sourceType.getName());
        return typeMapping.get(key);
    }*/

    public TypeConverter getTypeConverter(String targetType, String sourceType) {
        TypeMapper key = new TypeMapper(targetType, sourceType);
        return typeMapping.get(key);
    }

    @Override public TypeConverter lookup(String toType, String fromType) {
        getTypeConverter(toType, fromType);
        return null;
    }

}

