package com.lojosoft.cloud.metrics.memvisualizer.dataformat.lojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class App implements PcfMetaData {
    private String appName;
    @EqualsAndHashCode.Exclude private List<Instance> instances;
    @EqualsAndHashCode.Exclude private String path;
    @EqualsAndHashCode.Exclude private PcfMetaData parent;

    public Integer getMemAllocationMB() {
        int mem=0;
        for (Instance instance: instances) {
            mem+=instance.getMemAllocationMB();
        }
        return mem;
    }
    public Integer getMemActualConsumptionMB() {
        int mem=0;
        for (Instance instance: instances){
            mem+=instance.getMemActualConsumptionMB();
        }
        return mem;
    }

    public List<PcfMetaData> getMeAndAllChildrenAsList() {
        List<PcfMetaData> all = new ArrayList<>();
        all.add(this);
        for (Instance instance: instances) {
            all.addAll(instance.getMeAndAllChildrenAsList());
        }
        return all;
    }
    public Instance findInstanceByName(String instanceName) {
        for (Instance instance : instances){
            if (instance.getInstanceName().equals(instanceName)){
                return instance;
            }
        }
        return null;
    }
}
