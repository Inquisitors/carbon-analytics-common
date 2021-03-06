/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
package org.wso2.carbon.analytics.idp.client.external.factories;

import feign.Client;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.wso2.carbon.analytics.idp.client.core.exception.IdPClientException;
import org.wso2.carbon.analytics.idp.client.external.impl.SCIM2ServiceStub;
import org.wso2.carbon.analytics.idp.client.external.util.SPSSLSocketFactory;

/**
 * SCIM2 service stub factory.
 */
public class SCIM2ServiceStubFactory {
    public static SCIM2ServiceStub getSCIMServiceStub(String idpBaseUrl, String username, String password,
                                                      String idpCertAlias) throws IdPClientException {
        return Feign.builder()
                .requestInterceptor(new BasicAuthRequestInterceptor(username, password))
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .client(new Client.Default(SPSSLSocketFactory.getSSLSocketFactory(idpCertAlias),
                        (hostname, sslSession) -> true))
                .target(SCIM2ServiceStub.class, idpBaseUrl);

    }
}
