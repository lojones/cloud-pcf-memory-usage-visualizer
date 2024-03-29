package com.lojosoft.cloud.metrics.memvisualizer.dataformat.lojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Foundation implements PcfMetaData {
    @ToString.Include private String foundationName;
    private List<Org> orgs;
    @ToString.Include private String path;
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
