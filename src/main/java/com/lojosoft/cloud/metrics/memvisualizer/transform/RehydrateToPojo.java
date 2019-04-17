package com.lojosoft.cloud.metrics.memvisualizer.transform;

import com.lojosoft.cloud.metrics.memvisualizer.dataformat.lojo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class RehydrateToPojo {

    static Logger logger = LoggerFactory.getLogger(RehydrateToPojo.class);

    public static Foundation rehydrate(List<List<String>> rows) {

        List<Org> orgs = new ArrayList<>();

        Foundation foundation = new Foundation("Foundation", orgs, "/", null);

        for (List<String> row : rows) {
            String path = row.get(0);
            String memActual = row.get(1);
            String memAlloc = row.get(2);

            logger.info("Parsing {}",path);

            String[] pathItems = path.split("/");

            if (pathItems.length == 5) {
                logger.info("This is a full path, parsing...");

                String orgName = pathItems[1];
                Org org = foundation.findOrgByName(orgName);
                if (org == null) {
                    org = new Org(orgName,new ArrayList<Space>(),"/"+orgName,foundation);
                    foundation.getOrgs().add(org);
                    logger.info("Added org {}",org);
                }
                String spaceName = pathItems[2];
                Space space = org.findSpaceByName(spaceName);
                if(space == null) {
                    space = new Space(spaceName,new ArrayList<App>(),org.getPath()+"/"+spaceName,org);
                    org.getSpaces().add(space);
                }
                String appName = pathItems[3];
                App app = space.findAppByName(appName);
                if(app == null) {
                    app = new App(appName,new ArrayList<Instance>(),space.getPath()+"/"+appName,space);
                    space.getApps().add(app);
                }
                String instanceName = pathItems[4];
                Instance instance = app.findInstanceByName(instanceName);
                if(instance == null) {
                    instance = new Instance(instanceName,app.getPath()+"/"+instanceName,Integer.parseInt(memAlloc),Integer.parseInt(memActual),app);
                    app.getInstances().add(instance);
                }
               else {
                   logger.info("...not a full path, skipping");
                }

            }

        }
        return foundation;
    }
}
