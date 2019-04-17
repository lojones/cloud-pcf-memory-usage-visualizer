package com.lojosoft.cloud.metrics.memvisualizer.dataformat.lojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Foundation implements PcfMetaData {
    private String foundationName;
    private List<Org> orgs;
    private String path;
    private PcfMetaData parent;

     public Integer getMemAllocationMB() {
        int mem=0;
        for (Org org: orgs) {
            mem+=org.getMemAllocationMB();
        }
        return mem;
    }
    public Integer getMemActualConsumptionMB() {
        int mem=0;
        for (Org org: orgs){
            mem+=org.getMemActualConsumptionMB();
        }
        return mem;
    }

    public int treesize() {
        return getMeAndAllChildrenAsList().size();
    }

    public List<PcfMetaData> getMeAndAllChildrenAsList() {
        List<PcfMetaData> all = new ArrayList<>();
        all.add(this);
        for (Org org: orgs) {
            all.addAll(org.getMeAndAllChildrenAsList());
        }
        return all;
    }

    public Org findOrgByName(String orgName) {
         for (Org org:orgs){
             if (org.getOrgName().equals(orgName)){
                 return org;
             }
         }
         return null;
    }



}
