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
@ToString(onlyExplicitlyIncluded = true)
public class Space implements PcfMetaData {
    @ToString.Include private String spaceName;
    private List<App> Apps;
    @ToString.Include private String path;
    private PcfMetaData parent;

    public Integer getMemAllocationMB() {
        int mem=0;
        for (App app: Apps) {
            mem+=app.getMemAllocationMB();
        }
        return mem;
    }
    public Integer getMemActualConsumptionMB() {
        int mem=0;
        for (App app: Apps){
            mem+=app.getMemActualConsumptionMB();
        }
        return mem;
    }

    public List<PcfMetaData> getMeAndAllChildrenAsList() {
        List<PcfMetaData> all = new ArrayList<>();
        all.add(this);
        for (App app: Apps) {
            all.addAll(app.getMeAndAllChildrenAsList());
        }
        return all;
    }
    public App findAppByName(String appName) {
        for (App app : Apps){
            if (app.getAppName().equals(appName)){
                return app;
            }
        }
        return null;
    }
}
