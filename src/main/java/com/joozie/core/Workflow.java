package com.joozie.core;

import com.joozie.core.node.EndNode;
import com.joozie.core.node.KillNode;
import com.joozie.core.node.StartNode;

public class Workflow extends Node implements Configurable {

    private Configuration configuration;
    private EndNode endNode;
    private KillNode killNode;
    private StartNode startNode;
    private NodeList nodeList;

    public Workflow(){
        super();
        init();
    }

    public Workflow(String workflowName){
        super(workflowName);
        init();
    }

    private void init(){
        endNode = new EndNode(getName().toUpperCase() + "-END");
        killNode = new KillNode(getName().toUpperCase() + "-ERROR", "An error occurred in node : ");

        nodeList = new NodeList(endNode, killNode);
        startNode = new StartNode(endNode.getName());
    }

    @Override
    public Workflow usingConfig(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }

    public Workflow firstDo(Node action) {
        startNode = new StartNode(action.getName());
        nodeList.firstDo(action);

        return this;
    }

    public Workflow thenDo(Node action) {
        nodeList.thenDo(action);

        return this;
    }

    public String getEndNodeName() {
        return endNode.getName();
    }

    public String getKillNodeName() {
        return killNode.getName();
    }

    public String getKillNodeMessage() {
        return killNode.getMessage();
    }

    @Override
    public String build() {
        StringBuilder result = new StringBuilder();

        result.append("<workflow-app name='" + getName() + "' xmlns='uri:oozie:workflow:0.1'>");

        String globalConfig = configuration == null ? "" : configuration.build();

        if (!globalConfig.equals("")){
            result.append("<global>" + globalConfig + "</global>");
        }

        result.append(startNode.build());

        result.append(nodeList.build());

        result.append(endNode.build());
        result.append(killNode.build());

        result.append("</workflow-app>");

        return result.toString();
    }
}
