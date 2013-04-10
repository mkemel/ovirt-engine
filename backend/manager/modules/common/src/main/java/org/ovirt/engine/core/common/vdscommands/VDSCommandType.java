package org.ovirt.engine.core.common.vdscommands;

public enum VDSCommandType {
    AddVds("org.ovirt.engine.core.vdsbroker"),
    RemoveVds("org.ovirt.engine.core.vdsbroker"),
    ActivateVds("org.ovirt.engine.core.vdsbroker"),
    FenceVds("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    GetStats("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    CreateVm("org.ovirt.engine.core.vdsbroker"),
    DestroyVm("org.ovirt.engine.core.vdsbroker"),
    Pause("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    Hibernate("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    Resume("org.ovirt.engine.core.vdsbroker"),
    Migrate("org.ovirt.engine.core.vdsbroker"),
    MigrateStatus("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    CancelMigrate("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    ChangeDisk("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    ChangeFloppy("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    VmLogon("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    VmLogoff("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    VmMonitorCommand("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    SetVmTicket("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    SetVdsStatus("org.ovirt.engine.core.vdsbroker"),
    FailedToRunVm("org.ovirt.engine.core.vdsbroker"),
    SetVmStatus("org.ovirt.engine.core.vdsbroker"),
    IsVmDuringInitiating("org.ovirt.engine.core.vdsbroker"),
    StartSpice("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    RunVmHyperChannelCommand("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    ShutdownVds("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    UpdateVdsVMsCleared("org.ovirt.engine.core.vdsbroker"),
    AddNetwork("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    RemoveNetwork("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    EditNetwork("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    SetSafeNetworkConfig("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    ConnectStorageServer("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    DisconnectStorageServer("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    GetStorageConnectionsList("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    ConnectStoragePool("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    DisconnectStoragePool("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    DiscoverSendTargets("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    RefreshStoragePool("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    CreateStoragePool("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    UpgradeStoragePool("org.ovirt.engine.core.vdsbroker.irsbroker"),
    ReconstructMaster("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    SpmStart("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    SpmStop("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    SpmStatus("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    FenceSpmStorage("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    HSMGetAllTasksStatuses("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    HSMGetTaskStatus("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    HSMGetAllTasksInfo("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    HSMGetTaskInfo("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    HSMStopTask("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    HSMClearTask("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    HSMRevertTask("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    UpdateVmDynamicData("org.ovirt.engine.core.vdsbroker"),
    CollectVdsNetworkData("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    GetVmsInfo("org.ovirt.engine.core.vdsbroker.irsbroker"),
    HSMGetStorageDomainInfo("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    HSMGetStorageDomainsList("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    CreateImage("org.ovirt.engine.core.vdsbroker.irsbroker"),
    DestroyImage("org.ovirt.engine.core.vdsbroker.irsbroker"),
    GetImageInfo("org.ovirt.engine.core.vdsbroker.irsbroker"),
    DoesImageExist("org.ovirt.engine.core.vdsbroker.irsbroker"),
    CopyImage("org.ovirt.engine.core.vdsbroker.irsbroker"),
    CreateSnapshot("org.ovirt.engine.core.vdsbroker.irsbroker"),
    MergeSnapshots("org.ovirt.engine.core.vdsbroker.irsbroker"),
    SetImageDescription("org.ovirt.engine.core.vdsbroker.irsbroker"),
    IsoPrefix("org.ovirt.engine.core.vdsbroker.irsbroker"),
    IsoDirectory("org.ovirt.engine.core.vdsbroker.irsbroker"),
    ResetIrs("org.ovirt.engine.core.vdsbroker.irsbroker"),
    GetIsoList("org.ovirt.engine.core.vdsbroker.irsbroker"),
    HsmGetIsoList("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    GetFloppyList("org.ovirt.engine.core.vdsbroker.irsbroker"),
    ExportCandidate("org.ovirt.engine.core.vdsbroker.irsbroker"),
    CreateStorageDomain("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    ValidateStorageDomain("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    ActivateStorageDomain("org.ovirt.engine.core.vdsbroker.irsbroker"),
    DeactivateStorageDomain("org.ovirt.engine.core.vdsbroker.irsbroker"),
    DetachStorageDomain("org.ovirt.engine.core.vdsbroker.irsbroker"),
    AttachStorageDomain("org.ovirt.engine.core.vdsbroker.irsbroker"),
    FormatStorageDomain("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    SetStorageDomainDescription("org.ovirt.engine.core.vdsbroker.irsbroker"),
    GetStorageDomainStats("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    SetStoragePoolDescription("org.ovirt.engine.core.vdsbroker.irsbroker"),
    GetStoragePoolInfo("org.ovirt.engine.core.vdsbroker.irsbroker"),
    DestroyStoragePool("org.ovirt.engine.core.vdsbroker.irsbroker"),
    DeleteImageGroup("org.ovirt.engine.core.vdsbroker.irsbroker"),
    MoveImageGroup("org.ovirt.engine.core.vdsbroker.irsbroker"),
    CloneImageGroupStructure("org.ovirt.engine.core.vdsbroker.irsbroker"),
    SyncImageGroupData("org.ovirt.engine.core.vdsbroker.irsbroker"),
    VmReplicateDiskStart("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    VmReplicateDiskFinish("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    GetImageDomainsList("org.ovirt.engine.core.vdsbroker.irsbroker"),
    GetImagesList("org.ovirt.engine.core.vdsbroker.irsbroker"),
    GetVolumesList("org.ovirt.engine.core.vdsbroker.irsbroker"),
    CreateVG("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    GetVGList("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    GetVGInfo("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    GetDeviceList("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    GetDeviceInfo("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    GetDevicesVisibility("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    SPMGetAllTasksStatuses("org.ovirt.engine.core.vdsbroker.irsbroker"),
    SPMGetTaskStatus("org.ovirt.engine.core.vdsbroker.irsbroker"),
    SPMGetAllTasksInfo("org.ovirt.engine.core.vdsbroker.irsbroker"),
    SPMStopTask("org.ovirt.engine.core.vdsbroker.irsbroker"),
    SPMClearTask("org.ovirt.engine.core.vdsbroker.irsbroker"),
    SPMRevertTask("org.ovirt.engine.core.vdsbroker.irsbroker"),
    UpdateVM("org.ovirt.engine.core.vdsbroker.irsbroker"),
    RemoveVM("org.ovirt.engine.core.vdsbroker.irsbroker"),
    SpmStopOnIrs("org.ovirt.engine.core.vdsbroker.irsbroker"),
    ResetISOPath("org.ovirt.engine.core.vdsbroker.irsbroker"),
    MarkPoolInReconstructMode("org.ovirt.engine.core.vdsbroker.irsbroker"),
    ExtendStorageDomain("org.ovirt.engine.core.vdsbroker.irsbroker"),
    HotPlugDisk("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    HotUnPlugDisk("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    HotPlugNic("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    HotUnplugNic("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    Snapshot("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    UpdateVmInterface("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    GetHardwareInfo("org.ovirt.engine.core.vdsbroker.vdsbroker"),
    // Gluster VDS commands
    CreateGlusterVolume("org.ovirt.engine.core.vdsbroker.gluster"),
    SetGlusterVolumeOption("org.ovirt.engine.core.vdsbroker.gluster"),
    ResetGlusterVolumeOptions("org.ovirt.engine.core.vdsbroker.gluster"),
    StartGlusterVolume("org.ovirt.engine.core.vdsbroker.gluster"),
    StopGlusterVolume("org.ovirt.engine.core.vdsbroker.gluster"),
    DeleteGlusterVolume("org.ovirt.engine.core.vdsbroker.gluster"),
    GetGlusterVolumeOptionsInfo("org.ovirt.engine.core.vdsbroker.gluster"),
    GlusterVolumeRemoveBricks("org.ovirt.engine.core.vdsbroker.gluster"),
    StartRebalanceGlusterVolume("org.ovirt.engine.core.vdsbroker.gluster"),
    AddBricksToGlusterVolume("org.ovirt.engine.core.vdsbroker.gluster"),
    ReplaceGlusterVolumeBrick("org.ovirt.engine.core.vdsbroker.gluster"),
    RemoveGlusterServer("org.ovirt.engine.core.vdsbroker.gluster"),
    AddGlusterServer("org.ovirt.engine.core.vdsbroker.gluster"),
    GlusterServersList("org.ovirt.engine.core.vdsbroker.gluster"),
    StartGlusterVolumeProfile("org.ovirt.engine.core.vdsbroker.gluster"),
    StopGlusterVolumeProfile("org.ovirt.engine.core.vdsbroker.gluster"),
    GetGlusterVolumeAdvancedDetails("org.ovirt.engine.core.vdsbroker.gluster"),
    GlusterVolumesList("org.ovirt.engine.core.vdsbroker.gluster"),
    GetGlusterVolumeProfileInfo("org.ovirt.engine.core.vdsbroker.gluster"),
    EnableGlusterHook("org.ovirt.engine.core.vdsbroker.gluster"),
    DisableGlusterHook("org.ovirt.engine.core.vdsbroker.gluster"),
    ;

    String packageName;

    private VDSCommandType(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public int getValue() {
        return this.ordinal();
    }

    public static VDSCommandType forValue(int value) {
        return values()[value];
    }
}
