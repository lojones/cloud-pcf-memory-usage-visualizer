package com.lojosoft.cloud.metrics.memvisualizer.transform;

import com.lojosoft.cloud.metrics.memvisualizer.dataformat.lojo.*;
import com.lojosoft.cloud.metrics.memvisualizer.dataformat.zoomablecirclepacking.ZCPTree;

import java.util.ArrayList;
import java.util.List;

public class LojoToZCP {

    private static List<ZCPTree> zcpTreeListFromInstanceList(List<Instance> instances) {
        List<ZCPTree> trees = new ArrayList<>();

        for (Instance instance: instances) {
            trees.add(zcpTreeFromInstance(instance));
        }

        return trees;
    }

    private static ZCPTree zcpTreeFromInstance(Instance instance) {
        ZCPTree zcpTreeInstance = new ZCPTree();
        zcpTreeInstance.setName(instance.getInstanceName());
        zcpTreeInstance.setSize(String.valueOf(instance.getMemAllocationMB()));

        return zcpTreeInstance;
    }

    private static ZCPTree zcpTreeFromApp(App app) {
        ZCPTree zcpTreeInstance = new ZCPTree();
        zcpTreeInstance.setName(app.getAppName());
        zcpTreeInstance.setChildren(zcpTreeListFromInstanceList(app.getInstances()));

        return zcpTreeInstance;
    }

    private static List<ZCPTree> zcpTreeListFromAppList(List<App> apps) {
        List<ZCPTree> trees = new ArrayList<>();
        for (App app: apps) {
            trees.add(zcpTreeFromApp(app));
        }
        return trees;
    }

    private static ZCPTree zcpTreeFromSpace(Space space) {
        ZCPTree zcpTreeInstance = new ZCPTree();
        zcpTreeInstance.setName(space.getSpaceName());
        zcpTreeInstance.setChildren(zcpTreeListFromAppList(space.getApps()));

        return zcpTreeInstance;
    }

    private static List<ZCPTree> zcpTreeListFromSpaceList(List<Space> spaces) {
        List<ZCPTree> trees = new ArrayList<>();
        for (Space space: spaces) {
            trees.add(zcpTreeFromSpace(space));
        }
        return trees;
    }

    private static ZCPTree zcpTreeFromOrg(Org org) {
        ZCPTree zcpTreeInstance = new ZCPTree();
        zcpTreeInstance.setName(org.getOrgName());
        zcpTreeInstance.setChildren(zcpTreeListFromSpaceList(org.getSpaces()));

        return zcpTreeInstance;
    }

    private static List<ZCPTree> zcpTreeListFromOrgList(List<Org> orgs) {
        List<ZCPTree> trees = new ArrayList<>();

        for (Org org: orgs) {
            trees.add(zcpTreeFromOrg(org));
        }
        return trees;
    }

    private static ZCPTree zcpTreeFromFoundation(Foundation foundation) {
        ZCPTree zcpTreeInstance = new ZCPTree();
        zcpTreeInstance.setName(foundation.getFoundationName());
        zcpTreeInstance.setChildren(zcpTreeListFromOrgList(foundation.getOrgs()));

        return zcpTreeInstance;
    }

    public static ZCPTree transform(Foundation foundation) {
        ZCPTree zcpTree = zcpTreeFromFoundation(foundation);
        return zcpTree;
    }
}
