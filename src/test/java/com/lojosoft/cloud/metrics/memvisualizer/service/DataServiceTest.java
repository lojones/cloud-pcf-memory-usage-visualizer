package com.lojosoft.cloud.metrics.memvisualizer.service;

import com.lojosoft.cloud.metrics.memvisualizer.dataformat.lojo.Foundation;
import com.lojosoft.cloud.metrics.memvisualizer.dataformat.zoomablecirclepacking.ZCPTree;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class DataServiceTest {

    DataService dataService = new DataService();
    @Test
    public void getZCPTree() {
        try {
            ZCPTree tree = dataService.getZCPTree("Foundation-1");
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

    @Test
    public void getFoundationNames() {
        List<String> names = dataService.getFoundationNames();
        Assert.assertEquals(2, names.size());
        Assert.assertEquals("Foundation-1", names.get(0));
        Assert.assertEquals("Foundation-2", names.get(1));
    }

    @Test
    public void getFoundation() throws IOException, InvalidFormatException {
        Foundation foundation = dataService.getFoundation("Foundation-2");
        Assert.assertEquals("Foundation",foundation.getFoundationName());
        Assert.assertEquals(1,foundation.getOrgs().size());
        Assert.assertEquals(2,foundation.getOrgs().get(0).getSpaces().get(0).getApps().size());
    }
}