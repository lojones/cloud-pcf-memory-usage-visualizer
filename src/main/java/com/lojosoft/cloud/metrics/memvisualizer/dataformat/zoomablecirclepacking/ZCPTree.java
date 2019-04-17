package com.lojosoft.cloud.metrics.memvisualizer.dataformat.zoomablecirclepacking;

import lombok.Data;

import java.util.List;

@Data
public class ZCPTree {
    private String name;
    private String size;
    private List<ZCPTree> children;
}
