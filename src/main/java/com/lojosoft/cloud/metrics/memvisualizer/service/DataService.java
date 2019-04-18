package com.lojosoft.cloud.metrics.memvisualizer.service;

import com.lojosoft.cloud.metrics.memvisualizer.dataformat.lojo.Foundation;
import com.lojosoft.cloud.metrics.memvisualizer.dataformat.zoomablecirclepacking.ZCPTree;
import com.lojosoft.cloud.metrics.memvisualizer.persistence.XlsReader;
import com.lojosoft.cloud.metrics.memvisualizer.transform.LojoToZCP;
import com.lojosoft.cloud.metrics.memvisualizer.transform.RehydrateToPojo;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Service
public class DataService {

    File memUsageXlsFile;
    String filename = "pcf-mem-usage.xlsx";

    public DataService()  {

        URL url = getClass().getClassLoader().getResource(filename);
        if (url == null) {
            throw new RuntimeException("Could find file "+filename);
        }
        memUsageXlsFile = new File(url.getFile());
    }

    public ZCPTree getZCPTree() throws IOException, InvalidFormatException {
        List<List<String>> rawData = XlsReader.readXls(memUsageXlsFile);
        Foundation foundation = RehydrateToPojo.rehydrate(rawData);
        ZCPTree zcpTree = LojoToZCP.transform(foundation);
        return zcpTree;
    }
}
