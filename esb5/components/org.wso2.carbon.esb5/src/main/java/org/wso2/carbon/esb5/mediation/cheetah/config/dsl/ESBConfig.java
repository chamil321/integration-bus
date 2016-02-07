/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.esb5.mediation.cheetah.config.dsl;

import org.wso2.carbon.esb5.mediation.cheetah.config.ESBConfigHolder;
import org.wso2.carbon.esb5.mediation.cheetah.flow.sequence.builder.SequenceBuilder;
import org.wso2.carbon.esb5.mediation.cheetah.inbound.builder.InboundEPBuilder;
import org.wso2.carbon.esb5.mediation.cheetah.outbound.builder.OutboundEndpointBuilder;

/**
 * ESB Configuration Builder
 */
public class ESBConfig extends ESBConfigHolder {


    public ESBConfig(String name) {
        super(name);
    }

    /* For Inbound */
    public InboundEPBuilder inboundEndpoint(String name) {
        return InboundEPBuilder.inboundEndpoint(name, this);
    }

    /* For Outbound */
    public OutboundEndpointBuilder outboundEndpoint(String name) {
        return OutboundEndpointBuilder.outboundEndpoint(name, this);
    }

    /* For Sequence */
    public SequenceBuilder sequence(String name) {
        return SequenceBuilder.sequence(name, this);
    }




}
