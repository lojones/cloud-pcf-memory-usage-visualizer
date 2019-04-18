package com.lojosoft.cloud.metrics.memvisualizer.dataformat.lojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@ToString
public class Org implements PcfMetaData {
    private String orgName;
    private List<Space> spaces;
    private String path;
    private PcfMetaData parent;

    public Integer getMemAllocationMB() {
        int mem=0;
        for (Space space: spaces) {
            mem+=space.getMemAllocationMB();
        }
        return mem;
    }
    public Integer getMemActualConsumptionMB() {
        int mem=0;
        for (Space space: spaces){
            mem+=space.getMemActualConsumptionMB();
        }
        return mem;
    }

    public List<PcfMetaData> getMeAndAllChildrenAsList() {
        List<PcfMetaData> all = new ArrayList<>();
        all.add(this);
        for (Space space: spaces) {
            all.addAll(space.getMeAndAllChildrenAsList());
        }
        return all;
    }

    public Space findSpaceByName(String spaceName) {
        for (Space space:spaces){
            if (space.getSpaceName().equals(spaceName)){
                return space;
            }
        }
        return null;
    }
}
