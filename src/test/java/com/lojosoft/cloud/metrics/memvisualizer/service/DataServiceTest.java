package com.lojosoft.cloud.metrics.memvisualizer.service;

import com.lojosoft.cloud.metrics.memvisualizer.dataformat.zoomablecirclepacking.ZCPTree;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class DataServiceTest {

    DataService dataService = new DataService();
    @Test
    public void getZCPTree() {
        try {
            ZCPTree tree = dataService.getZCPTree();
            Assert.assertNotNull(tree);
            Assert.assertEquals("Foundation",tree.getName());
            Assert.assertNotNull(tree.getChildren());
            Assert.assertTrue(tree.getChildren().size() == 1);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
}