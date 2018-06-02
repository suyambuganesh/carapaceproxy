/*
 Licensed to Diennea S.r.l. under one
 or more contributor license agreements. See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership. Diennea S.r.l. licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.

 */
package nettyhttpproxy.api;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import java.util.Properties;
import nettyhttpproxy.TestEndpointMapper;
import nettyhttpproxy.client.ConnectionsManagerStats;
import nettyhttpproxy.server.HttpProxyServer;
import nettyhttpproxy.server.StaticContentsManager;
import nettyhttpproxy.server.config.ActionConfiguration;
import nettyhttpproxy.server.config.BackendConfiguration;
import nettyhttpproxy.server.config.RouteConfiguration;
import nettyhttpproxy.server.config.URIRequestMatcher;
import nettyhttpproxy.server.mapper.StandardEndpointMapper;
import nettyhttpproxy.utils.RawHttpClient;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

/**
 *
 * @author enrico.olivelli
 */
public class StartAPIServerTest {

    @Test
    public void test() throws Exception {

        try (HttpProxyServer server = new HttpProxyServer("localhost", 0,
                new TestEndpointMapper("localhost", 0));) {
            Properties prop = new Properties();
            prop.setProperty("http.admin.enabled", "true");
            prop.setProperty("http.admin.port", "8761");
            prop.setProperty("http.admin.host", "localhost");
            server.configure(prop);
            server.start();
            server.startAdminInterface();
            
            try (RawHttpClient client = new RawHttpClient("localhost", 8761)) {
                RawHttpClient.HttpResponse resp = client.get("/api/cache/flush");
                String s = resp.toString();
                System.out.println("s:" + s);
                assertTrue(s.endsWith("ok"));
            }

        }
    }
}