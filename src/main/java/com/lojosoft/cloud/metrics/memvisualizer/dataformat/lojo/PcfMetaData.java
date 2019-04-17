package com.lojosoft.cloud.metrics.memvisualizer.dataformat.lojo;

import java.util.List;

public interface PcfMetaData {
    public String getPath();
    public Integer getMemAllocationMB();
    public Integer getMemActualConsumptionMB();
    public List<PcfMetaData> getMeAndAllChildrenAsList();
    public PcfMetaData getParent();

}
