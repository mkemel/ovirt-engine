package org.ovirt.engine.ui.uicommonweb.models.storage;

import java.util.ArrayList;
import java.util.Collections;

import org.ovirt.engine.core.common.action.MoveOrCopyImageGroupParameters;
import org.ovirt.engine.core.common.action.VdcActionParametersBase;
import org.ovirt.engine.core.common.action.VdcActionType;
import org.ovirt.engine.core.common.businessentities.DiskImage;
import org.ovirt.engine.core.common.businessentities.ImageOperation;
import org.ovirt.engine.core.common.businessentities.Quota;
import org.ovirt.engine.core.common.businessentities.storage_domains;
import org.ovirt.engine.core.common.businessentities.storage_pool;
import org.ovirt.engine.core.compat.Guid;
import org.ovirt.engine.core.compat.NGuid;
import org.ovirt.engine.core.compat.PropertyChangedEventArgs;
import org.ovirt.engine.core.compat.StringHelper;
import org.ovirt.engine.ui.frontend.AsyncQuery;
import org.ovirt.engine.ui.frontend.Frontend;
import org.ovirt.engine.ui.frontend.INewAsyncCallback;
import org.ovirt.engine.ui.uicommonweb.ICommandTarget;
import org.ovirt.engine.ui.uicommonweb.Linq;
import org.ovirt.engine.ui.uicommonweb.UICommand;
import org.ovirt.engine.ui.uicommonweb.dataprovider.AsyncDataProvider;
import org.ovirt.engine.ui.uicommonweb.models.ListModel;
import org.ovirt.engine.ui.uicommonweb.models.vms.DiskModel;
import org.ovirt.engine.ui.uicompat.FrontendMultipleActionAsyncResult;
import org.ovirt.engine.ui.uicompat.IFrontendMultipleActionAsyncCallback;

public abstract class MoveOrCopyDiskModel extends DisksAllocationModel implements ICommandTarget
{
    private ArrayList<DiskModel> allDisks;

    public ArrayList<DiskModel> getAllDisks()
    {
        return allDisks;
    }

    public void setAllDisks(ArrayList<DiskModel> value)
    {
        if (allDisks != value)
        {
            allDisks = value;
            OnPropertyChanged(new PropertyChangedEventArgs("All Disks")); //$NON-NLS-1$
        }
    }

    private ArrayList<storage_domains> storageDomains;

    public ArrayList<storage_domains> getStorageDomains()
    {
        return storageDomains;
    }

    public void setStorageDomains(ArrayList<storage_domains> value)
    {
        if (storageDomains != value)
        {
            storageDomains = value;
            OnPropertyChanged(new PropertyChangedEventArgs("Storage Domains")); //$NON-NLS-1$
        }
    }

    private ArrayList<DiskImage> diskImages;

    public ArrayList<DiskImage> getDiskImages()
    {
        return diskImages;
    }

    public void setDiskImages(ArrayList<DiskImage> value)
    {
        if (diskImages != value)
        {
            diskImages = value;
            OnPropertyChanged(new PropertyChangedEventArgs("Disk Images")); //$NON-NLS-1$
        }
    }

    protected ArrayList<storage_domains> activeStorageDomains;
    protected ArrayList<storage_domains> intersectStorageDomains;
    protected ArrayList<storage_domains> unionStorageDomains;

    public abstract void init(ArrayList<DiskImage> diskImages);

    protected abstract void initStorageDomains();

    protected abstract void postCopyOrMoveInit();

    protected abstract void updateMoveOrCopySingleDiskParameters(ArrayList<VdcActionParametersBase> parameters,
            DiskModel diskModel);

    public MoveOrCopyDiskModel() {
        allDisks = new ArrayList<DiskModel>();
        storageDomains = new ArrayList<storage_domains>();
    }

    protected void onInitDisks() {
        ArrayList<DiskModel> disks = new ArrayList<DiskModel>();
        for (DiskImage disk : getDiskImages())
        {
            disks.add(Linq.DiskImageToModel(disk));
        }
        setDisks(disks);

        initStorageDomains();
    }

    protected void onInitAllDisks(ArrayList<DiskImage> diskImages) {
        for (DiskImage disk : diskImages)
        {
            allDisks.add(Linq.DiskImageToModel(disk));
        }
    }

    protected void onInitStorageDomains(ArrayList<storage_domains> storages) {
        for (storage_domains storage : storages) {
            if (Linq.IsDataActiveStorageDomain(storage)) {
                storageDomains.add(storage);
            }
        }

        Collections.sort(storageDomains, new Linq.StorageDomainByNameComparer());

        if (!storageDomains.isEmpty()) {
            AsyncDataProvider.GetDataCenterById(new AsyncQuery(this, new INewAsyncCallback() {
                @Override
                public void OnSuccess(Object target, Object returnValue) {
                    MoveOrCopyDiskModel model = (MoveOrCopyDiskModel) target;
                    storage_pool dataCenter = (storage_pool) returnValue;

                    model.setQuotaEnforcementType(dataCenter.getQuotaEnforcementType());
                    model.postInitStorageDomains();
                }
            }), storageDomains.get(0).getstorage_pool_id().getValue());
        }
        else {
            postInitStorageDomains();
        }
    }

    protected void postInitStorageDomains() {
        activeStorageDomains = getStorageDomains();

        ArrayList<ArrayList<storage_domains>> allSourceStorages = new ArrayList<ArrayList<storage_domains>>();
        ArrayList<storage_domains> missingStorageDomains = new ArrayList<storage_domains>();

        for (DiskModel disk : getDisks()) {
            // Source storages
            ArrayList<Guid> diskStorageIds = disk.getDiskImage().getstorage_ids();
            ArrayList<storage_domains> diskStorages = Linq.getStorageDomainsByIds(diskStorageIds, activeStorageDomains);
            disk.getSourceStorageDomain().setItems(diskStorages);
            allSourceStorages.add(diskStorages);

            // Destination storages
            ArrayList<storage_domains> destStorageDomains = Linq.Except(activeStorageDomains, diskStorages);
            ArrayList<storage_domains> diskMissingStorageDomains = new ArrayList<storage_domains>();

            // Filter storages with missing template disk
            if (!disk.getDiskImage().getParentId().equals(NGuid.Empty)) {
                diskMissingStorageDomains = getMissingStorages(destStorageDomains, disk);
            }
            missingStorageDomains.addAll(diskMissingStorageDomains);
            destStorageDomains = Linq.Except(destStorageDomains, missingStorageDomains);

            // Filter storages on different datacenters
            if (!diskStorages.isEmpty()) {
                NGuid diskDatacenterId = diskStorages.get(0).getstorage_pool_id();
                destStorageDomains = filterStoragesByDatacenterId(destStorageDomains, diskDatacenterId);
            }

            Collections.sort(destStorageDomains, new Linq.StorageDomainByNameComparer());
            disk.getStorageDomain().setItems(destStorageDomains);
        }

        intersectStorageDomains = Linq.Intersection(allSourceStorages);
        unionStorageDomains = Linq.Union(allSourceStorages);

        ArrayList<storage_domains> destStorageDomains = Linq.Except(activeStorageDomains, intersectStorageDomains);
        destStorageDomains = Linq.Except(destStorageDomains, missingStorageDomains);
        Collections.sort(destStorageDomains, new Linq.StorageDomainByNameComparer());

        getStorageDomain().setItems(destStorageDomains);
        sortDisks();

        postCopyOrMoveInit();
    }

    protected ArrayList<storage_domains> filterStoragesByDatacenterId(ArrayList<storage_domains> storageDomains,
            NGuid diskDatacenterId) {

        ArrayList<storage_domains> storages = new ArrayList<storage_domains>();
        for (storage_domains storage : storageDomains) {
            if (storage.getstorage_pool_id().equals(diskDatacenterId)) {
                storages.add(storage);
            }
        }

        return storages;
    }

    protected ArrayList<storage_domains> getMissingStorages(ArrayList<storage_domains> storageDomains, DiskModel vmdisk) {
        ArrayList<storage_domains> missingStorageDomains = new ArrayList<storage_domains>();
        DiskModel templateDisk = getTemplateDiskByVmDisk(vmdisk);

        if (templateDisk != null) {
            for (storage_domains storageDomain : storageDomains) {
                if (!templateDisk.getDiskImage().getstorage_ids().contains(storageDomain.getId())) {
                    missingStorageDomains.add(storageDomain);
                }
            }
        }

        return missingStorageDomains;
    }

    protected DiskModel getTemplateDiskByVmDisk(DiskModel vmdisk) {
        for (DiskModel disk : getAllDisks()) {
            if (disk.getDiskImage().getImageId().equals(vmdisk.getDiskImage().getParentId())) {
                return disk;
            }
        }

        return null;
    }

    private void OnMove() {
        OnMoveOrCopy(ImageOperation.Move);
    }

    private void OnCopy() {
        OnMoveOrCopy(ImageOperation.Copy);
    }

    protected void OnMoveOrCopy(ImageOperation imageOperation) {
        if (this.getProgress() != null)
        {
            return;
        }

        boolean iSingleStorageDomain = (Boolean) getIsSingleStorageDomain().getEntity();

        ArrayList<VdcActionParametersBase> parameters = new ArrayList<VdcActionParametersBase>();
        for (DiskModel diskModel : getDisks())
        {
            storage_domains destStorageDomain = iSingleStorageDomain ?
                    (storage_domains) getStorageDomain().getSelectedItem() :
                    (storage_domains) diskModel.getStorageDomain().getSelectedItem();

            storage_domains sourceStorageDomain =
                    (storage_domains) diskModel.getSourceStorageDomain().getSelectedItem();

            Guid sourceStorageDomainGuid = sourceStorageDomain != null ? sourceStorageDomain.getId() : Guid.Empty;
            DiskImage disk = diskModel.getDiskImage();
            if (diskModel.getQuota().getSelectedItem() != null) {
                if (iSingleStorageDomain) {
                    disk.setQuotaId(((Quota) getQuota().getSelectedItem()).getId());
                } else {
                    disk.setQuotaId(((Quota) diskModel.getQuota().getSelectedItem()).getId());
                }
            }

            if (iSingleStorageDomain && getDisks().size() == 1) {
                updateMoveOrCopySingleDiskParameters(parameters, diskModel);
            }
            else {
                if (destStorageDomain == null
                        || diskModel.getDiskImage().getstorage_ids().contains(destStorageDomain.getId())) {
                    continue;
                }

                Guid destStorageDomainGuid = destStorageDomain.getId();
                addMoveOrCopyParameters(parameters,
                        sourceStorageDomainGuid,
                        destStorageDomainGuid,
                        disk,
                        imageOperation);
            }
        }

        StartProgress(null);

        Frontend.RunMultipleAction(VdcActionType.MoveOrCopyDisk, parameters,
                new IFrontendMultipleActionAsyncCallback() {
                    @Override
                    public void Executed(FrontendMultipleActionAsyncResult result) {
                        MoveOrCopyDiskModel localModel = (MoveOrCopyDiskModel) result.getState();
                        localModel.StopProgress();
                        ListModel listModel = (ListModel) localModel.getEntity();
                        listModel.setWindow(null);
                    }
                }, this);
    }

    protected void addMoveOrCopyParameters(ArrayList<VdcActionParametersBase> parameters,
            Guid sourceStorageDomainGuid,
            Guid destStorageDomainGuid,
            DiskImage disk,
            ImageOperation imageOperation) {
        MoveOrCopyImageGroupParameters diskParameters =
                new MoveOrCopyImageGroupParameters(disk.getImageId(),
                        sourceStorageDomainGuid,
                        destStorageDomainGuid,
                        imageOperation);
        diskParameters.setQuotaId(disk.getQuotaId());

        parameters.add(diskParameters);
    }

    @Override
    public void ExecuteCommand(UICommand command)
    {
        super.ExecuteCommand(command);

        if (StringHelper.stringsEqual(command.getName(), "OnMove")) //$NON-NLS-1$
        {
            OnMove();
        }
        else if (StringHelper.stringsEqual(command.getName(), "OnCopy")) //$NON-NLS-1$
        {
            OnCopy();
        }
    }
}
