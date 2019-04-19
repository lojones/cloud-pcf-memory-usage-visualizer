package com.lojosoft.cloud.metrics.memvisualizer.service;

import com.lojosoft.cloud.metrics.memvisualizer.dataformat.lojo.Foundation;
import com.lojosoft.cloud.metrics.memvisualizer.dataformat.zoomablecirclepacking.ZCPTree;
import com.lojosoft.cloud.metrics.memvisualizer.persistence.XlsReader;
import com.lojosoft.cloud.metrics.memvisualizer.transform.LojoToZCP;
import com.lojosoft.cloud.metrics.memvisualizer.transform.RehydrateToPojo;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

@Service
public class DataService {

    Logger logger = LoggerFactory.getLogger(DataService.class);
    File memUsageXlsFile;
    String filename = "pcf-mem-usage.xlsx";

    Map<String,Foundation> foundations = new HashMap<>();
    Map<String,ZCPTree> zcpTrees = new HashMap<>();

    public DataService()  {

        logger.info("Initializing data...");
        URL url = getClass().getClassLoader().getResource(filename);
        if (url == null) {
            throw new RuntimeException("Could not find file "+filename);
        }
        memUsageXlsFile = new File(url.getFile());
        List<String> worksheetNames = null;
        try {
            worksheetNames = XlsReader.getWorksheetNames(memUsageXlsFile);
        } catch (Exception e) {
            logger.error("Error trying to read file {} - {}, fatal so quitting", filename, e.getMessage());
            throw new RuntimeException(e);
        }
        worksheetNames.forEach(worksheetName -> {
            try {
                getFoundation(worksheetName);
                getZCPTree(worksheetName);
            } catch (Exception e) {
                logger.error("Error trying to read worksheet {} from {}, skipping - {}", worksheetName,filename,e.getMessage());
            }

        });

        logger.info("..Done initializing data!");
    }

    public List<String> getFoundationNames() {
        return new ArrayList<>(foundations.keySet());
    }



    public ZCPTree getZCPTree(String foundationName) throws IOException, InvalidFormatException {
        ZCPTree zcpTree = zcpTrees.get(foundationName);
        if (zcpTree == null) {

            Foundation foundation = getFoundation(foundationName);
            zcpTree = LojoToZCP.transform(foundation);
            zcpTrees.put(foundationName, zcpTree);
        }

        return zcpTree;
    }

    public Foundation getFoundation(String foundationName) throws IOException, InvalidFormatException {
        Foundation foundation = foundations.get(foundationName);
        if (foundation == null) {
            List<List<String>> rawData = XlsReader.readXls(memUsageXlsFile,foundationName);
            foundation = RehydrateToPojo.rehydrate(rawData);
            foundations.put(foundationName,foundation);
        }
        return foundation;
    }
}
