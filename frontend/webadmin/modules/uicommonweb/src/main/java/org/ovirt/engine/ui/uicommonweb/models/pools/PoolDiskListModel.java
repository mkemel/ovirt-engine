package org.ovirt.engine.ui.uicommonweb.models.pools;

import org.ovirt.engine.core.common.businessentities.VM;
import org.ovirt.engine.core.common.businessentities.vm_pools;
import org.ovirt.engine.core.common.queries.GetAllDisksByVmIdParameters;
import org.ovirt.engine.core.common.queries.VdcQueryType;
import org.ovirt.engine.ui.frontend.AsyncQuery;
import org.ovirt.engine.ui.frontend.INewAsyncCallback;
import org.ovirt.engine.ui.uicommonweb.dataprovider.AsyncDataProvider;
import org.ovirt.engine.ui.uicommonweb.models.SearchableListModel;
import org.ovirt.engine.ui.uicompat.ConstantsManager;

@SuppressWarnings("unused")
public class PoolDiskListModel extends SearchableListModel
{
    public PoolDiskListModel()
    {
        setTitle(ConstantsManager.getInstance().getConstants().virtualDisksTitle());
        setHashName("virtual_disks"); //$NON-NLS-1$
    }

    @Override
    protected void OnEntityChanged()
    {
        super.OnEntityChanged();

        vm_pools pool = (vm_pools) getEntity();
        if (pool != null)
        {
            AsyncQuery _asyncQuery = new AsyncQuery();
            _asyncQuery.setModel(this);
            _asyncQuery.asyncCallback = new INewAsyncCallback() {
                @Override
                public void OnSuccess(Object model, Object result)
                {
                    VM vm = (VM) result;
                    if (vm != null)
                    {
                        PoolDiskListModel poolDiskListModel = (PoolDiskListModel) model;
                        poolDiskListModel.SyncSearch(VdcQueryType.GetAllDisksByVmId,
                                new GetAllDisksByVmIdParameters(vm.getId()));
                    }
                }
            };
            AsyncDataProvider.GetAnyVm(_asyncQuery, pool.getvm_pool_name());
        }
    }

    @Override
    protected String getListName() {
        return "PoolDiskListModel"; //$NON-NLS-1$
    }
}
