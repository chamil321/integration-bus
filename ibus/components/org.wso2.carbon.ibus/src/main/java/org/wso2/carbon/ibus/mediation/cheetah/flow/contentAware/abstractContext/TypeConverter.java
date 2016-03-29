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

package org.wso2.carbon.ibus.mediation.cheetah.flow.contentAware.abstractContext;

import org.omg.CORBA.portable.InputStream;
import org.wso2.carbon.ibus.mediation.cheetah.flow.contentAware.exceptions.TypeConversionException;
import org.wso2.carbon.messaging.CarbonMessage;

import java.io.IOException;

/**
 * This implements different type converters
 */
public interface TypeConverter {


    /**
     * Converts the value to the specified type
     *
     * @param inputStream the inputStream of cMsg
     * @return the converted value, or <tt>null</tt> if not possible to convert
     * @throws TypeConversionException is thrown if error during type conversion
     */
    java.io.InputStream convert(java.io.InputStream inputStream) throws TypeConversionException, IOException;

    /**
     * Converts the value to the specified type
     *
     * @param anyValue any type of an object
     * @return the converted value, or <tt>null</tt> if not possible to convert
     * @throws TypeConversionException is thrown if error during type conversion
     */
    <T> T convert(Object anyValue) throws TypeConversionException;

    /**
     * Converts the value to the specified type in the context of an exchange
     * <p/>
     * Used when conversion requires extra information from the current
     * exchange (such as encoding).
     *
     * @param type the requested type
     * @param carbonMessageWrapper the wrapper of the cMsg
     * @param value the value to be converted
     * @return the converted value, or <tt>null</tt> if not possible to convert
     * @throws TypeConversionException is thrown if error during type conversion
     */
    //<T> T convert(Class<T> type, CarbonMessageWrapper carbonMessageWrapper, Object value) throws TypeConversionException;



}
