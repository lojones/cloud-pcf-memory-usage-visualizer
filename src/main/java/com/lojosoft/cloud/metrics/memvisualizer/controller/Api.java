package com.lojosoft.cloud.metrics.memvisualizer.controller;

import com.lojosoft.cloud.metrics.memvisualizer.dataformat.zoomablecirclepacking.ZCPTree;
import com.lojosoft.cloud.metrics.memvisualizer.service.DataService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RequestMapping("/api")
@RestController
public class Api {

    @Autowired
    private DataService dataService;

    @RequestMapping("/zcp")
    public ZCPTree getZoomableCirclePackingData() throws IOException, InvalidFormatException {
        return dataService.getZCPTree();
    }

}
