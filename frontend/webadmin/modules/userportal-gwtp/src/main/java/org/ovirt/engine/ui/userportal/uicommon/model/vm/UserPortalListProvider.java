package org.ovirt.engine.ui.userportal.uicommon.model.vm;

import org.ovirt.engine.core.common.businessentities.VmType;
import org.ovirt.engine.ui.common.auth.CurrentUser;
import org.ovirt.engine.ui.common.presenter.AbstractModelBoundPopupPresenterWidget;
import org.ovirt.engine.ui.common.presenter.popup.RemoveConfirmationPopupPresenterWidget;
import org.ovirt.engine.ui.uicommonweb.UICommand;
import org.ovirt.engine.ui.uicommonweb.models.ConfirmationModel;
import org.ovirt.engine.ui.uicommonweb.models.Model;
import org.ovirt.engine.ui.uicommonweb.models.userportal.UserPortalItemModel;
import org.ovirt.engine.ui.uicommonweb.models.userportal.UserPortalListModel;
import org.ovirt.engine.ui.uicommonweb.models.vms.UnitVmModel;
import org.ovirt.engine.ui.userportal.gin.ClientGinjector;
import org.ovirt.engine.ui.userportal.section.main.presenter.popup.vm.VmChangeCDPopupPresenterWidget;
import org.ovirt.engine.ui.userportal.section.main.presenter.popup.vm.VmDesktopNewPopupPresenterWidget;
import org.ovirt.engine.ui.userportal.section.main.presenter.popup.vm.VmMakeTemplatePopupPresenterWidget;
import org.ovirt.engine.ui.userportal.section.main.presenter.popup.vm.VmRunOncePopupPresenterWidget;
import org.ovirt.engine.ui.userportal.section.main.presenter.popup.vm.VmServerNewPopupPresenterWidget;
import org.ovirt.engine.ui.userportal.uicommon.model.UserPortalDataBoundModelProvider;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class UserPortalListProvider extends UserPortalDataBoundModelProvider<UserPortalItemModel, UserPortalListModel> {

    private final Provider<VmDesktopNewPopupPresenterWidget> newDesktopVmPopupProvider;
    private final Provider<VmServerNewPopupPresenterWidget> newServerVmPopupProvider;
    private final Provider<VmRunOncePopupPresenterWidget> runOncePopupProvider;
    private final Provider<VmChangeCDPopupPresenterWidget> changeCDPopupProvider;
    private final Provider<VmMakeTemplatePopupPresenterWidget> makeTemplatePopupProvider;
    private final Provider<RemoveConfirmationPopupPresenterWidget> removeConfirmPopupProvider;

    @Inject
    public UserPortalListProvider(ClientGinjector ginjector,
            Provider<VmDesktopNewPopupPresenterWidget> newDesktopVmPopupProvider,
            Provider<VmServerNewPopupPresenterWidget> newServerVmPopupProvider,
            Provider<VmRunOncePopupPresenterWidget> runOncePopupProvider,
            Provider<VmChangeCDPopupPresenterWidget> changeCDPopupProvider,
            Provider<VmMakeTemplatePopupPresenterWidget> makeTemplatePopupProvider,
            Provider<RemoveConfirmationPopupPresenterWidget> removeConfirmPopupProvider,
            CurrentUser user) {
        super(ginjector, user);
        this.newDesktopVmPopupProvider = newDesktopVmPopupProvider;
        this.newServerVmPopupProvider = newServerVmPopupProvider;
        this.runOncePopupProvider = runOncePopupProvider;
        this.changeCDPopupProvider = changeCDPopupProvider;
        this.makeTemplatePopupProvider = makeTemplatePopupProvider;
        this.removeConfirmPopupProvider = removeConfirmPopupProvider;
    }

    @Override
    protected UserPortalListModel createModel() {
        return new UserPortalListModel();
    }

    @Override
    public String[] getWindowPropertyNames() {
        return new String[] { "VmModel", "RunOnceModel", "AttachCdModel" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    @Override
    public Model getWindowModel(UserPortalListModel source, String propertyName) {
        if ("VmModel".equals(propertyName)) { //$NON-NLS-1$
            return source.getVmModel();
        } else if ("RunOnceModel".equals(propertyName)) { //$NON-NLS-1$
            return source.getRunOnceModel();
        } else if ("AttachCdModel".equals(propertyName)) { //$NON-NLS-1$
            return source.getAttachCdModel();
        } else {
            return null;
        }
    }

    @Override
    public void clearWindowModel(UserPortalListModel source, String propertyName) {
        if ("VmModel".equals(propertyName)) { //$NON-NLS-1$
            source.setVmModel(null);
        } else if ("RunOnceModel".equals(propertyName)) { //$NON-NLS-1$
            source.setRunOnceModel(null);
        } else if ("AttachCdModel".equals(propertyName)) { //$NON-NLS-1$
            source.setAttachCdModel(null);
        }
    }

    @Override
    public String[] getConfirmWindowPropertyNames() {
        return new String[] { "ConfirmationModel" }; //$NON-NLS-1$
    }

    @Override
    public Model getConfirmWindowModel(UserPortalListModel source, String propertyName) {
        return source.getConfirmationModel();
    }

    @Override
    public void clearConfirmWindowModel(UserPortalListModel source, String propertyName) {
        source.setConfirmationModel(null);
    }

    @Override
    public AbstractModelBoundPopupPresenterWidget<? extends Model, ?> getModelPopup(UserPortalListModel source,
            UICommand lastExecutedCommand, Model windowModel) {
        if (lastExecutedCommand == getModel().getNewTemplateCommand()) {
            return makeTemplatePopupProvider.get();
        } else if (lastExecutedCommand == getModel().getRunOnceCommand()) {
            return runOncePopupProvider.get();
        } else if (lastExecutedCommand == getModel().getChangeCdCommand()) {
            return changeCDPopupProvider.get();
        } else if (lastExecutedCommand == getModel().getNewDesktopCommand()) {
            return newDesktopVmPopupProvider.get();
        } else if (lastExecutedCommand == getModel().getNewServerCommand()) {
            return newServerVmPopupProvider.get();
        } else if (lastExecutedCommand == getModel().getEditCommand()) {
            UnitVmModel vm = getModel().getVmModel();
            if (vm.getVmType().equals(VmType.Desktop)) {
                return newDesktopVmPopupProvider.get();
            } else {
                return newServerVmPopupProvider.get();
            }
        } else {
            return super.getModelPopup(source, lastExecutedCommand, windowModel);
        }
    }

    @Override
    public AbstractModelBoundPopupPresenterWidget<? extends ConfirmationModel, ?> getConfirmModelPopup(UserPortalListModel source,
            UICommand lastExecutedCommand) {
        if (lastExecutedCommand == getModel().getRemoveCommand()) {
            return removeConfirmPopupProvider.get();
        } else {
            return super.getConfirmModelPopup(source, lastExecutedCommand);
        }
    }

}
