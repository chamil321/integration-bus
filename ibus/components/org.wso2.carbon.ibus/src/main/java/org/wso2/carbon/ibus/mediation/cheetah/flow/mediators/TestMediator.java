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
 */

package org.wso2.carbon.ibus.mediation.cheetah.flow.mediators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.ibus.mediation.cheetah.flow.Mediator;
import org.wso2.carbon.ibus.mediation.cheetah.flow.contentAware.ConversionManager;
import org.wso2.carbon.ibus.mediation.cheetah.flow.contentAware.MIMEType;
import org.wso2.carbon.messaging.CarbonCallback;
import org.wso2.carbon.messaging.CarbonMessage;

import java.io.InputStream;
import java.util.Scanner;

public class TestMediator implements Mediator {

    private static Logger log = LoggerFactory.getLogger(TestMediator.class);

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setNext(Mediator nextMediator) {

    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public boolean next(CarbonMessage carbonMessage, CarbonCallback carbonCallback) throws Exception {
        return false;
    }

    @Override
    public boolean receive(CarbonMessage carbonMessage, CarbonCallback carbonCallback) throws Exception {
        String contentType = carbonMessage.getHeader("Content-Type");
        log.trace("Content type of the carbon message body: {}", contentType);

        InputStream xmlStream = ConversionManager.getInstance().convertTo(carbonMessage, contentType, MIMEType.XML);

        Scanner in = new Scanner(xmlStream);
        String xmlString = "";

        while(in.hasNext()) {
            xmlString += in.nextLine();
        }

        log.trace(xmlString);

        return false;
    }

    @Override
    public void setConfigs(String configs) {

    }
}
