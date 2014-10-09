/*
* Copyright (c) 2014 Red Hat, Inc.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*   http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.ovirt.engine.api.restapi.resource.openstack;

import static org.easymock.EasyMock.expect;
import static org.ovirt.engine.api.restapi.utils.HexUtils.string2hex;

import org.ovirt.engine.api.model.OpenStackNetwork;
import org.ovirt.engine.api.restapi.resource.AbstractBackendCollectionResourceTest;
import org.ovirt.engine.core.common.businessentities.network.Network;
import org.ovirt.engine.core.common.businessentities.network.ProviderNetwork;
import org.ovirt.engine.core.common.queries.IdQueryParameters;
import org.ovirt.engine.core.common.queries.VdcQueryType;
import org.ovirt.engine.core.compat.Guid;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BackendOpenStackNetworksResourceTest
        extends AbstractBackendCollectionResourceTest<OpenStackNetwork, Network, BackendOpenStackNetworksResource> {
    public BackendOpenStackNetworksResourceTest() {
        super(
            new BackendOpenStackNetworksResource(GUIDS[0].toString()),
            null,
            ""
        );
    }

    @Override
    protected List<OpenStackNetwork> getCollection() {
        return collection.list().getOpenStackNetworks();
    }

    @Override
    protected void setUpQueryExpectations(String query, Object failure) throws Exception {
        setUpEntityQueryExpectations(
            VdcQueryType.GetAllExternalNetworksOnProvider,
            IdQueryParameters.class,
            new String[] { "Id" },
            new Object[] { GUIDS[0] },
            getNetworks(),
            failure
        );
        control.replay();
    }

    private Map<Network, Set<Guid>> getNetworks() {
        Map<Network, Set<Guid>> networks = new HashMap<>();
        for (int i = 0; i < NAMES.length; i++) {
            networks.put(getEntity(i), new HashSet<Guid>());
        }
        return networks;
    }

    @Override
    protected Network getEntity(int index) {
        Network network = control.createMock(Network.class);
        expect(network.getId()).andReturn(GUIDS[index]).anyTimes();
        expect(network.getName()).andReturn(NAMES[index]).anyTimes();
        ProviderNetwork providedBy = new ProviderNetwork();
        providedBy.setProviderId(GUIDS[0]);
        providedBy.setExternalId(string2hex(NAMES[index]));
        expect(network.getProvidedBy()).andReturn(providedBy).anyTimes();
        return network;
    }

    @Override
    protected void verifyModel(OpenStackNetwork model, int index) {
        // The model can't be verified based on the index because the backend returns a map, that the resource
        // translates into a list, and the order of that list may not be the same that the original map.
        verifyLinks(model);
    }
}
