package org.ovirt.engine.ui.webadmin.section.main.view.popup.host;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ovirt.engine.core.compat.Event;
import org.ovirt.engine.core.compat.EventArgs;
import org.ovirt.engine.core.compat.IEventListener;
import org.ovirt.engine.ui.common.view.popup.AbstractModelBoundPopupView;
import org.ovirt.engine.ui.common.widget.dialog.SimpleDialogPanel;
import org.ovirt.engine.ui.uicommonweb.models.hosts.HostSetupNetworksModel;
import org.ovirt.engine.ui.uicommonweb.models.hosts.network.LogicalNetworkModel;
import org.ovirt.engine.ui.uicommonweb.models.hosts.network.NetworkInterfaceModel;
import org.ovirt.engine.ui.uicommonweb.models.hosts.network.NetworkItemModel;
import org.ovirt.engine.ui.uicommonweb.models.hosts.network.NetworkOperation;
import org.ovirt.engine.ui.uicommonweb.models.hosts.network.OperationCadidateEventArgs;
import org.ovirt.engine.ui.webadmin.ApplicationConstants;
import org.ovirt.engine.ui.webadmin.ApplicationResources;
import org.ovirt.engine.ui.webadmin.gin.ClientGinjectorProvider;
import org.ovirt.engine.ui.webadmin.section.main.presenter.popup.host.HostSetupNetworksPopupPresenterWidget;
import org.ovirt.engine.ui.webadmin.section.main.view.popup.host.panels.NetworkGroup;
import org.ovirt.engine.ui.webadmin.section.main.view.popup.host.panels.NetworkPanel;
import org.ovirt.engine.ui.webadmin.section.main.view.popup.host.panels.NetworkPanelsStyle;
import org.ovirt.engine.ui.webadmin.widget.editor.AnimatedVerticalPanel;
import org.ovirt.engine.ui.webadmin.widget.footer.StatusLabel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.inject.Inject;

public class HostSetupNetworksPopupView extends AbstractModelBoundPopupView<HostSetupNetworksModel> implements HostSetupNetworksPopupPresenterWidget.ViewDef {

    interface ViewUiBinder extends UiBinder<SimpleDialogPanel, HostSetupNetworksPopupView> {
        ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
    }

    private static ApplicationConstants constants = ClientGinjectorProvider.instance().getApplicationConstants();
    private static final String EMPTY_STATUS = constants.dragToMakeChangesSetupNetwork();

    @UiField
    AnimatedVerticalPanel networkList;

    @UiField
    AnimatedVerticalPanel nicList;

    StatusLabel status;

    @UiField
    NetworkPanelsStyle style;

    private boolean rendered = false;

    private HostSetupNetworksModel uicommonModel;

    @Inject
    public HostSetupNetworksPopupView(EventBus eventBus, ApplicationResources resources, ApplicationConstants constants) {
        super(eventBus, resources);
        initWidget(ViewUiBinder.uiBinder.createAndBindUi(this));
        status = new StatusLabel(EMPTY_STATUS);
        status.setStylePrimaryName(style.statusLabel());
        addStatusWidget(status.asWidget());
    }

    @Override
    public void edit(HostSetupNetworksModel uicommonModel) {
        this.uicommonModel = uicommonModel;
        uicommonModel.getNicsChangedEvent().addListener(new IEventListener() {
            @Override
            public void eventRaised(Event ev, Object sender, EventArgs args) {
                // this is called after both networks and nics were retrieved
                HostSetupNetworksModel model = (HostSetupNetworksModel) sender;
                List<LogicalNetworkModel> networks = model.getNetworks();
                List<NetworkInterfaceModel> nics = model.getNics();
                status.setText(EMPTY_STATUS);
                updateNetworks(networks);
                updateNics(nics);
                // mark as rendered
                rendered = true;
            }
        });

        uicommonModel.getOperationCandidateEvent().addListener(new IEventListener() {

            @Override
            public void eventRaised(Event ev, Object sender, EventArgs args) {
                OperationCadidateEventArgs evtArgs = (OperationCadidateEventArgs) args;
                NetworkOperation candidate = evtArgs.getCandidate();
                NetworkItemModel<?> op1 = evtArgs.getOp1();
                NetworkItemModel<?> op2 = evtArgs.getOp2();
                status.setFadeText(candidate != null ? candidate.getMessage(op1, op2) : constants.noValidActionSetupNetwork());
            }
        });

    }

    @Override
    public HostSetupNetworksModel flush() {
        return uicommonModel;
    }

    @Override
    public void focusInput() {
    }

    @Override
    public void setMessage(String message) {
    }

    private void updateNetworks(List<LogicalNetworkModel> allNetworks) {
        networkList.clear();
        Collections.sort(allNetworks);
        List<NetworkPanel> panels = new ArrayList<NetworkPanel>();
        for (LogicalNetworkModel network : allNetworks) {
            if (!network.isAttached()) {
                panels.add(new NetworkPanel(network, style));
            }
        }
        networkList.addAll(panels, !rendered);
    }

    private void updateNics(List<NetworkInterfaceModel> nics) {
        nicList.clear();
        Collections.sort(nics);
        List<NetworkGroup> groups = new ArrayList<NetworkGroup>();
        for (NetworkInterfaceModel nic : nics) {
            groups.add(new NetworkGroup(nic, style));
        }
        nicList.addAll(groups, !rendered);
    }

}
