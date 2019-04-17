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
import java.util.List;


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
            List<List<String>> actual = XlsReader.readXls(f);
        } catch (IOException e) {
            Assert.fail("IOException calling readXls");
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readXls_validContents() throws IOException, InvalidFormatException {

        List<List<String>> actual = XlsReader.readXls(f);
        Assert.assertEquals(18,actual.size());
        int i=0;
        for (List<String> row: actual) {

            Assert.assertEquals(3, row.size());

            if (i>0) {
                Assert.assertTrue(row.get(0).contains("/"));
                Assert.assertNotNull(Integer.valueOf(row.get(1)));
                Assert.assertNotNull(Integer.valueOf(row.get(2)));
            }
            i++;
        }
    }
}