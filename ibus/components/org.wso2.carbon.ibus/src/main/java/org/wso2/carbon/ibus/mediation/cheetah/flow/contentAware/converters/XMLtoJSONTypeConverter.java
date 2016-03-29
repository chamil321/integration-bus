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

import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLOutputFactory;
import org.wso2.carbon.ibus.mediation.cheetah.flow.contentAware.exceptions.TypeConversionException;
import org.wso2.carbon.ibus.mediation.cheetah.flow.contentAware.abstractContext.AbstractTypeConverter;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.*;
import javax.xml.transform.stax.StAXResult;
import javax.xml.transform.stax.StAXSource;
import java.io.*;

/**
 * This converts XML to JSON input stream
 */
public class XMLtoJSONTypeConverter extends AbstractTypeConverter {

    @Override public InputStream convert(InputStream inputStream) throws TypeConversionException, IOException {
        InputStream input = inputStream;
        InputStream results = null;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        JsonXMLConfig config = new JsonXMLConfigBuilder().autoArray(true).prettyPrint(true).build();
        try {
            //Create source (XML).
            XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(input);
            Source source = new StAXSource(reader);

            //Create result (JSON).
            XMLStreamWriter writer = new JsonXMLOutputFactory(config).createXMLStreamWriter(output);
            Result result = new StAXResult(writer);

            //Copy source to result via "identity transform".
            TransformerFactory.newInstance().newTransformer().transform(source, result);

            byte[] outputByteArray = output.toByteArray();
            results = new ByteArrayInputStream(outputByteArray);

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } finally {
            //As per StAX specification, XMLStreamReader/Writer.close() doesn't close the underlying stream.
            output.close();
            input.close();
        }

        return results;
        //return null;
    }

    /*public static void main(String[] args) throws IOException, TypeConversionException {
        XMLtoJSONTypeConverter test = new XMLtoJSONTypeConverter();
        String temp = "<?xml version=\"1.0\"?>\n" + "<customer>\n" + "    <first-name>Jane</first-name>\n"
                + "    <last-name>Doe</last-name>\n" + "    <address>\n" + "        <street>123 A Street</street>\n"
                + "    </address>\n" + "    <phone-number type=\"work\">555-1111</phone-number>\n"
                + "    <phone-number type=\"cell\">555-2222</phone-number>\n" + "</customer>";
        InputStream input = new ByteArrayInputStream(temp.getBytes(StandardCharsets.UTF_8));
        InputStream output = test.convert(input);
        System.out.println("input2 \n" + temp);
        java.util.Scanner s = new java.util.Scanner(output).useDelimiter("\\A");
        System.out.println("output " + (s.hasNext() ? s.next() : ""));
    }*/

}
