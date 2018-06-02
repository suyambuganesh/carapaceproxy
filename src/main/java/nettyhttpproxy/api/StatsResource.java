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

import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import nettyhttpproxy.client.ConnectionsManagerStats;
import nettyhttpproxy.server.HttpProxyServer;

/**
 * Access to proxy cache
 *
 * @author enrico.olivelli
 */
@Path("/stats")
@Produces("application/json")
public class StatsResource {

    @javax.ws.rs.core.Context
    ServletContext context;

    @Path("/endpoints")
    @GET
    public ConnectionsManagerStats endpointsStats() {
        HttpProxyServer server = (HttpProxyServer) context.getAttribute("server");
        return server.getConnectionsManager().getStats();
    }

}
