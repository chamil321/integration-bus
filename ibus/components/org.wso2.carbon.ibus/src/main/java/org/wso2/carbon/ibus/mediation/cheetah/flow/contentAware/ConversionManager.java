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

package org.wso2.carbon.ibus.mediation.cheetah.flow.contentAware;

import org.apache.log4j.Logger;
import org.wso2.carbon.ibus.mediation.cheetah.config.CheetahConfigRegistry;
import org.wso2.carbon.messaging.CarbonMessage;

import java.io.InputStream;

public class ConversionManager {
    private static ConversionManager manager;
    private static final Logger log = Logger.getLogger(ConversionManager.class);

    public static ConversionManager getInstance() {
        if(manager == null)
            manager = new ConversionManager();

        return manager;
    }

    public Object convertTo(CarbonMessage cMsgWrapper, String sourceType, String targetType) {
        TypeConverter converter = CheetahConfigRegistry.getInstance()
                                            .getTypeConverterRegistry().lookup(sourceType, targetType);

        if(converter == null)
            return null;

        InputStream stream = null;

        try {
            stream = converter.convertTo(sourceType, cMsgWrapper, targetType);
        }
        catch (TypeConversionException e) {
            log.error("Error in converting from: " + sourceType +" to: " + targetType);
        }

        return stream;
    }
}
