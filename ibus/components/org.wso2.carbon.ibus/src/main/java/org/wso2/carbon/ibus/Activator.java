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
 * See the License for the specific language governing permissions and limitations under the License.
 */

package org.wso2.carbon.ibus;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.wso2.carbon.ibus.mediation.cheetah.CheetahMessageProcessor;
import org.wso2.carbon.ibus.mediation.cheetah.config.CheetahConfigRegistry;
import org.wso2.carbon.ibus.mediation.cheetah.config.dsl.external.deployer.IFlowDeployer;
import org.wso2.carbon.ibus.mediation.cheetah.flow.contentAware.abstractContext.TypeConverter;
import org.wso2.carbon.ibus.mediation.cheetah.flow.contentAware.converters.CarbonMessageReverseTypeConverter;
import org.wso2.carbon.ibus.mediation.cheetah.flow.contentAware.converters.CarbonMessageToDocumentConverter;
import org.wso2.carbon.ibus.mediation.cheetah.flow.contentAware.converters.XMLtoJSONTypeConverter;
import org.wso2.carbon.ibus.mediation.cheetah.inbound.DispatcherRegistry;
import org.wso2.carbon.ibus.mediation.cheetah.inbound.manager.InboundEndpointManager;
import org.wso2.carbon.ibus.mediation.cheetah.inbound.protocols.http.HTTPInboundEPDispatcher;
import org.wso2.carbon.kernel.deployment.Deployer;
import org.wso2.carbon.messaging.CarbonMessage;
import org.wso2.carbon.messaging.CarbonMessageProcessor;
import org.wso2.carbon.messaging.TransportListenerManager;

import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;

/**
 * OSGi Bundle Activator of the Cheetah Carbon component.
 */
public class Activator implements BundleActivator {

    private static final Logger log = LoggerFactory.getLogger(Activator.class);

    public void start(BundleContext bundleContext) throws Exception {

        try {
            log.info("Starting Integration Bus...!");

            //Creating the processor and registering the service
            CheetahMessageProcessor engine = new CheetahMessageProcessor();
            bundleContext.registerService(CarbonMessageProcessor.class, engine, null);
            bundleContext.registerService(TransportListenerManager.class,
                                          InboundEndpointManager.getInstance(), null);
            bundleContext.registerService(Deployer.class, new IFlowDeployer(), null);
            addTypeConverters(CheetahConfigRegistry.getInstance());
            //Registering dispatchers
            DispatcherRegistry.getInstance().registerDispatcher("http", HTTPInboundEPDispatcher.getInstance());

        } catch (Exception ex) {
            String msg = "Error while loading Cheetah";
            log.error(msg, ex);
            throw new RuntimeException(msg, ex);
        }
    }

    public void stop(BundleContext bundleContext) throws Exception {
    }

    private void addTypeConverters(CheetahConfigRegistry cheetahConfigRegistry) {


        cheetahConfigRegistry.getTypeConverterRegistry().addTypeConverter(Document.class, CarbonMessage.class,
                new CarbonMessageToDocumentConverter());
        cheetahConfigRegistry.getTypeConverterRegistry().addTypeConverter(InputStream.class, CarbonMessage.class,
                new CarbonMessageTypeConverter());
        cheetahConfigRegistry.getTypeConverterRegistry().addTypeConverter(DOMSource.class, CarbonMessage.class,
                new CarbonMessageTypeConverter());
        cheetahConfigRegistry.getTypeConverterRegistry().addTypeConverter(SAXSource.class, CarbonMessage.class,
                new CarbonMessageTypeConverter());
        cheetahConfigRegistry.getTypeConverterRegistry().addTypeConverter(StAXSource.class, CarbonMessage.class,
                new CarbonMessageTypeConverter());
        cheetahConfigRegistry.getTypeConverterRegistry().addTypeConverter(StreamSource.class, CarbonMessage.class,
                new CarbonMessageTypeConverter());
        cheetahConfigRegistry.getTypeConverterRegistry().addTypeConverter(String.class, CarbonMessage.class,
                new CarbonMessageTypeConverter());
        cheetahConfigRegistry.getTypeConverterRegistry().addTypeConverter(CarbonMessage.class, String.class,
                (TypeConverter) new CarbonMessageReverseTypeConverter());
        cheetahConfigRegistry.getTypeConverterRegistry().addTypeConverter("JSON","XML",
                new XMLtoJSONTypeConverter());

    }

}
