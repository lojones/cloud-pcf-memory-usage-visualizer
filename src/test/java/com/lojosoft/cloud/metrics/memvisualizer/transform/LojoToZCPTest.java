package com.lojosoft.cloud.metrics.memvisualizer.transform;

import com.lojosoft.cloud.metrics.memvisualizer.dataformat.lojo.*;
import com.lojosoft.cloud.metrics.memvisualizer.dataformat.zoomablecirclepacking.ZCPTree;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class LojoToZCPTest {

    Foundation foundation;
    ZCPTree zcpTree;

    private Org getOrg(String suffix, Foundation parent) {
        Org org = new Org("ORG-"+suffix,new ArrayList<Space>(),parent.getPath()+"/ORG-"+suffix,parent);
        return org;
    }

    private Space getSpace(String suffix, Org parent) {
        Space space = new Space("SPACE-"+suffix,new ArrayList<App>(),parent.getPath()+"/SPACE-"+suffix,parent);
        return space;
    }

    private App getApp(String suffix, Space parent) {
        App app = new App("APP-"+suffix,new ArrayList<Instance>(),parent.getPath()+"/APP-"+suffix,parent);
        return app;
    }

    private Instance getInstance(String suffix, Integer alloc, Integer consump, App parent) {
        Instance instance = new Instance("INSTANCE-"+suffix,parent.getPath()+"/INSTANCE-"+suffix, alloc, consump, parent);
        return instance;
    }

    private List<ZCPTree> zcpTreeListFromInstanceList(List<Instance> instances) {
        List<ZCPTree> trees = new ArrayList<>();

        for (Instance instance: instances) {
            trees.add(zcpTreeFromInstance(instance));
        }

        return trees;
    }

    private ZCPTree zcpTreeFromInstance(Instance instance) {
        ZCPTree zcpTreeInstance = new ZCPTree();
        zcpTreeInstance.setName(instance.getInstanceName());
        zcpTreeInstance.setSize(String.valueOf(instance.getMemAllocationMB()));

        return zcpTreeInstance;
    }

    private ZCPTree zcpTreeFromApp(App app) {
        ZCPTree zcpTreeInstance = new ZCPTree();
        zcpTreeInstance.setName(app.getAppName());
        zcpTreeInstance.setChildren(zcpTreeListFromInstanceList(app.getInstances()));

        return zcpTreeInstance;
    }

    private List<ZCPTree> zcpTreeListFromAppList(List<App> apps) {
        List<ZCPTree> trees = new ArrayList<>();
        for (App app: apps) {
            trees.add(zcpTreeFromApp(app));
        }
        return trees;
    }

    private ZCPTree zcpTreeFromSpace(Space space) {
        ZCPTree zcpTreeInstance = new ZCPTree();
        zcpTreeInstance.setName(space.getSpaceName());
        zcpTreeInstance.setChildren(zcpTreeListFromAppList(space.getApps()));

        return zcpTreeInstance;
    }

    private List<ZCPTree> zcpTreeListFromSpaceList(List<Space> spaces) {
        List<ZCPTree> trees = new ArrayList<>();
        for (Space space: spaces) {
            trees.add(zcpTreeFromSpace(space));
        }
        return trees;
    }

    private ZCPTree zcpTreeFromOrg(Org org) {
        ZCPTree zcpTreeInstance = new ZCPTree();
        zcpTreeInstance.setName(org.getOrgName());
        zcpTreeInstance.setChildren(zcpTreeListFromSpaceList(org.getSpaces()));

        return zcpTreeInstance;
    }

    private List<ZCPTree> zcpTreeListFromOrgList(List<Org> orgs) {
        List<ZCPTree> trees = new ArrayList<>();

        for (Org org: orgs) {
            trees.add(zcpTreeFromOrg(org));
        }
        return trees;
    }

    private ZCPTree zcpTreeFromFoundation(Foundation foundation) {
        ZCPTree zcpTreeInstance = new ZCPTree();
        zcpTreeInstance.setName(foundation.getFoundationName());
        zcpTreeInstance.setChildren(zcpTreeListFromOrgList(foundation.getOrgs()));

        return zcpTreeInstance;
    }



    @Before
    public void setup() {

        foundation=new Foundation("GTA",new ArrayList<Org>(),"/",null);

        foundation.getOrgs().addAll(Arrays.asList(getOrg("1",foundation),getOrg("2",foundation),getOrg("3",foundation)));

        int x=0;
        x++;
        for (int i=0;i<3;i++){
            Org org = foundation.getOrgs().get(i);

            for (int h=0;h<3;h++) {
                Space space = getSpace(String.valueOf(x),org); x++;
                org.getSpaces().add(space);
                for (int j=0;j<4;j++) {
                    App app = getApp(String.valueOf(x),space);x++;
                    space.getApps().add(app);
                    for (int k=0;k<5;k++) {
                        Instance instance = getInstance(String.valueOf(x),x*100%20,x*100%20/3,app); x++;
                        app.getInstances().add(instance);
                    }

                }
            }
        }

        zcpTree = zcpTreeFromFoundation(foundation);

    }

    @Test
    public void transform() {
        ZCPTree actual = LojoToZCP.transform(foundation);
        ZCPTree expected = zcpTree;
        Assert.assertEquals(expected,actual);
    }
}