package com.lojosoft.cloud.metrics.memvisualizer.persistence;

import com.lojosoft.cloud.metrics.memvisualizer.persistence.XlsReader;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RunWith(SpringRunner.class)
public class XlsReaderTest {

    File f;
    @Before
    public void setup() {
        f = new File(getClass().getClassLoader().getResource("pcf-mem-usage.xlsx").getFile());
    }

    @Test
    public void readXls_general() {
        try {
            List<List<String>> actual = XlsReader.readXls(f,"Foundation-1");
        } catch (IOException e) {
            Assert.fail("IOException calling readXls");
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getWorksheetNames() {
        List<String> worksheetNames  = null;
        try {
            worksheetNames = XlsReader.getWorksheetNames(f);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        Assert.assertEquals("Foundation-1",worksheetNames.get(0));
    }

    @Test
    public void readXls_validContents() throws IOException, InvalidFormatException {

        List<List<String>> actual = XlsReader.readXls(f,"Foundation-1");
        Assert.assertEquals(18,actual.size());
        int i=0;
        Set<String> paths = new HashSet<>();

        for (List<String> row: actual) {

            Assert.assertEquals(3, row.size());

            if (i>0) {
                Assert.assertTrue(row.get(0).contains("/"));
                Assert.assertNotNull(Integer.valueOf(row.get(1)));
                Assert.assertNotNull(Integer.valueOf(row.get(2)));
                if (paths.contains(row.get(0))) {
                    Assert.fail("Found duplicates of the same path ");
                }
                else {
                    paths.add(row.get(0));
                }
            }
            i++;
        }
    }
}