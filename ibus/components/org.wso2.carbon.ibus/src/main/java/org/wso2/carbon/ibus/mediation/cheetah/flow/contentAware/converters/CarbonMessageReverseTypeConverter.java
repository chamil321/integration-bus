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

package org.wso2.carbon.ibus.mediation.cheetah.flow.contentAware.converters;

import org.apache.log4j.Logger;
import org.wso2.carbon.ibus.mediation.cheetah.flow.contentAware.exceptions.TypeConversionException;
import org.wso2.carbon.ibus.mediation.cheetah.flow.contentAware.abstractContext.AbstractTypeConverter;
import org.wso2.carbon.messaging.CarbonMessage;
import org.wso2.carbon.messaging.DefaultCarbonMessage;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * A type converter which is used to convert to and from CarbonMessage to other types
 * Specailly in case of content aware mediation
 */

public class CarbonMessageReverseTypeConverter extends AbstractTypeConverter {
    private static final Logger log = Logger.getLogger(CarbonMessageReverseTypeConverter.class);

    @Override public InputStream convert(InputStream inputStream) throws TypeConversionException {
        return null;
    }

    @Override public <T> T convert(Object value) throws TypeConversionException {
        if (value instanceof String) {
            try {
                CarbonMessage carbonMessage = new DefaultCarbonMessage();
                ByteBuffer byteBuffer = ByteBuffer.wrap(((String) value).getBytes("UTF-8"));
                //Set the content length of the new message
                carbonMessage.setProperty("Content-Length", String.valueOf(byteBuffer.limit()));
                carbonMessage.addMessageBody(byteBuffer);
                carbonMessage.setEndOfMsgAdded(true);
                return (T) carbonMessage;
            } catch (UnsupportedEncodingException e) {
                log.error("Encoding type is not supported", e);
            }
        }
        return null;
    }
}
