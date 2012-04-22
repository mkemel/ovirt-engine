package org.ovirt.engine.ui.webadmin.section.main.view.popup.host.panels;

import org.ovirt.engine.ui.uicommonweb.models.hosts.network.NetworkInterfaceModel;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable.ColumnFormatter;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class NicPanel extends NetworkItemPanel {

    public NicPanel(NetworkInterfaceModel item, NetworkPanelsStyle style) {
        this(item, style, true);
    }

    public NicPanel(NetworkInterfaceModel item, NetworkPanelsStyle style, Boolean draggable) {
        super(item, style, draggable);
        getElement().addClassName(style.nicPanel());
    }

    @Override
    protected Widget getContents() {
        Grid rowPanel = new Grid(1, 5);
        rowPanel.setCellSpacing(3);
        rowPanel.setWidth("100%"); //$NON-NLS-1$
        rowPanel.setHeight("100%"); //$NON-NLS-1$

        ColumnFormatter columnFormatter = rowPanel.getColumnFormatter();
        columnFormatter.setWidth(0, "5px"); //$NON-NLS-1$
        columnFormatter.setWidth(1, "10px"); //$NON-NLS-1$
        columnFormatter.setWidth(2, "30px"); //$NON-NLS-1$
        columnFormatter.setWidth(3, "100%"); //$NON-NLS-1$
        columnFormatter.setWidth(4, "30px"); //$NON-NLS-1$

        Label titleLabel = new Label(item.getName());
        titleLabel.setHeight("100%"); //$NON-NLS-1$
        Image nicImage = new Image(resources.nicIcon());

        rowPanel.setWidget(0, 0, dragImage);
        ImageResource statusImage = getStatusImage();
        if (statusImage != null) {
            rowPanel.setWidget(0, 1, new Image(statusImage));
        }
        rowPanel.setWidget(0, 2, nicImage);
        rowPanel.setWidget(0, 3, titleLabel);
        rowPanel.setWidget(0, 4, actionButton);
        return rowPanel;
    }

    @Override
    protected void onAction() {
        item.edit();
    }

    @Override
    protected void onMouseOut() {
        super.onMouseOut();
        actionButton.setVisible(false);
    }

    @Override
    protected void onMouseOver() {
        super.onMouseOver();
        NetworkInterfaceModel nic = (NetworkInterfaceModel) item;
        if (!nic.isBonded() && nic.getItems().size() > 0) {
            actionButton.setVisible(true);
        }
    }

    private ImageResource getStatusImage() {
        switch (((NetworkInterfaceModel) item).getStatus()) {
        case Up:
            return resources.nicUp();
        case Down:
            return resources.nicDown();
        case None:
            return resources.questionMarkImage();
        default:
            return resources.questionMarkImage();
        }
    }
}
