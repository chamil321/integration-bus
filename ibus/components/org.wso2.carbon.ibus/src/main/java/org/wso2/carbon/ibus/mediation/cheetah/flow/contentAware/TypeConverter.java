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

import org.wso2.carbon.messaging.CarbonMessage;

import java.io.InputStream;

/**
 * A pluggable strategy to be able to convert objects <a
 * href="http://camel.apache.org/type-converter.html">to different
 * types</a> such as to and from String, InputStream/OutputStream,
 * Reader/Writer, Document, byte[], ByteBuffer etc
 *
 * @version
 */
public interface TypeConverter {

    /**
     * Whether the type converter allows returning null as a valid response.
     * <p/>
     * By default <tt>null</tt> is not a valid response, returning <tt>false</tt> from this method.
     */
    boolean allowNull();

    /**
     * Converts the value to the specified type
     *
     * @param type the requested type
     * @param value the value to be converted
     * @return the converted value, or <tt>null</tt> if not possible to convert
     * @throws TypeConversionException is thrown if error during type conversion
     */
    <T> T convertTo(Class<T> type, Object value) throws TypeConversionException;

    /**
     * Converts the value to the specified type in the context of an exchange
     * <p/>
     * Used when conversion requires extra information from the current
     * exchange (such as encoding).
     *
     * @param sourceType the requested type
     * @param cMsg the current CarbonMessage
     * @param targetType the value to be converted
     * @return the converted value, or <tt>null</tt> if not possible to convert
     * @throws TypeConversionException is thrown if error during type conversion
     */
    InputStream convertTo(String sourceType, CarbonMessage cMsg, String targetType) throws TypeConversionException;

    /**
     * Converts the value to the specified type
     *
     * @param type the requested type
     * @param value the value to be converted
     * @return the converted value, is never <tt>null</tt>
     * @throws TypeConversionException is thrown if error during type conversion
     * @throws NoTypeConversionAvailableException if no type converters exists to convert to the given type
     */
    <T> T mandatoryConvertTo(Class<T> type, Object value)
            throws TypeConversionException, NoTypeConversionAvailableException;

    /**
     * Converts the value to the specified type in the context of an exchange
     * <p/>
     * Used when conversion requires extra information from the current
     * exchange (such as encoding).
     *
     * @param type the requested type
     * @param cMsg the current CarbonMessage
     * @param value the value to be converted
     * @return the converted value, is never <tt>null</tt>
     * @throws TypeConversionException is thrown if error during type conversion
     * @throws NoTypeConversionAvailableException if no type converters exists to convert to the given type
     */
    <T> T mandatoryConvertTo(Class<T> type, CarbonMessage cMsg, Object value)
            throws TypeConversionException, NoTypeConversionAvailableException;

    /**
     * Tries to convert the value to the specified type,
     * returning <tt>null</tt> if not possible to convert.
     * <p/>
     * This method will <b>not</b> throw an exception if an exception occurred during conversion.
     *
     * @param type the requested type
     * @param value the value to be converted
     * @return the converted value, or <tt>null</tt> if not possible to convert
     */
    <T> T tryConvertTo(Class<T> type, Object value);

    /**
     * Tries to convert the value to the specified type in the context of an exchange,
     * returning <tt>null</tt> if not possible to convert.
     * <p/>
     * This method will <b>not</b> throw an exception if an exception occurred during conversion.
     * Converts the value to the specified type in the context of an exchange
     * <p/>
     * Used when conversion requires extra information from the current
     * exchange (such as encoding).
     *
     * @param type the requested type
     * @param cMsg the current CarbonMessage
     * @param value the value to be converted
     * @return the converted value, or <tt>null</tt> if not possible to convert
     */
    <T> T tryConvertTo(Class<T> type, CarbonMessage cMsg, Object value);

}
