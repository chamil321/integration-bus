/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.wso2.carbon.ibus.mediation.cheetah.flow.contentAware.converters;

import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLInputFactory;
import de.odysseus.staxon.xml.util.PrettyXMLEventWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.ibus.mediation.cheetah.flow.contentAware.exceptions.TypeConversionException;
import org.wso2.carbon.ibus.mediation.cheetah.flow.contentAware.abstractContext.TypeConverter;
import org.wso2.carbon.ibus.util.ByteBufferBackedInputStream;
import org.wso2.carbon.messaging.CarbonMessage;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class JSONtoXMLConverter implements TypeConverter {
    private static final Logger log = LoggerFactory.getLogger(JSONtoXMLConverter.class);

    @Override
    public InputStream convert(CarbonMessage cMsg) throws TypeConversionException {

        BlockingQueue<ByteBuffer> contentBuf = aggregateContent(cMsg);
        InputStream input = new ByteBufferBackedInputStream(contentBuf);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        JsonXMLConfig config = new JsonXMLConfigBuilder().multiplePI(false).build();
        try {
            XMLEventReader reader = new JsonXMLInputFactory(config).createXMLEventReader(input);

            XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(output);
            writer = new PrettyXMLEventWriter(writer);

            writer.add(reader);

            reader.close();
            writer.close();

            output.close();
            input.close();
        }
        catch (XMLStreamException e) {
            log.error("Error in XML stream", e);
        }
        catch (IOException e) {
            log.error("Error in I/O", e);
        }

        byte[] xml = output.toByteArray();
        return new ByteArrayInputStream(xml);
    }

    private BlockingQueue<ByteBuffer> aggregateContent(CarbonMessage msg) {

        try {
            //Check whether the message is fully read
            while (!msg.isEndOfMsgAdded()) {
                Thread.sleep(10);
            }
            //Get a clone of content chunk queue from the pipe
            BlockingQueue<ByteBuffer> clonedContent = new LinkedBlockingQueue<>(msg.getFullMessageBody());
            return clonedContent;
        } catch (Exception e) {
            log.error("Error occurred during conversion from CarbonMessage", e);
        }
        return null;
    }

    @Override
    public <T> T convert(Object anyValue) throws TypeConversionException {
        return null;
    }
}