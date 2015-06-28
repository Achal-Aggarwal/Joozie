# Joozie
Oozie workflow generator
```
  public static Workflow sampleDecisionInWorkflow(){
    return new Workflow("Sample-Decision-Workflow")
      .firstDo(new Decision("decision")
          .ifTrue("if-predicate", new FsAction("if-inner-fs-action"))
          .elseIf("elseif-predicate", new FsAction("ifelse-inner-fs-action"))
          .otherwise(new FsAction("otherwise-fs-action"))
      )
      .thenDo(new FsAction("outer-fs-action"));
  }
  /*
  <workflow-app name='Sample-Decision-Workflow'
    xmlns='uri:oozie:workflow:0.1'>
      <start to='decision'/>
      <decision name='decision'>
          <switch>
              <case to='if-inner-fs-action'>if-predicate</case>
              <case to='ifelse-inner-fs-action'>elseif-predicate</case>
              <default to='otherwise-fs-action'/>
          </switch>
      </decision>
      <action name='if-inner-fs-action'>
          <fs></fs>
          <ok to='outer-fs-action'/>
          <error to='Sample-Decision-Workflow-error'/>
      </action>
      <action name='ifelse-inner-fs-action'>
          <fs></fs>
          <ok to='outer-fs-action'/>
          <error to='Sample-Decision-Workflow-error'/>
      </action>
      <action name='otherwise-fs-action'>
          <fs></fs>
          <ok to='outer-fs-action'/>
          <error to='Sample-Decision-Workflow-error'/>
      </action>
      <action name='outer-fs-action'>
          <fs></fs>
          <ok to='Sample-Decision-Workflow-end'/>
          <error to='Sample-Decision-Workflow-error'/>
      </action>
      <end name='Sample-Decision-Workflow-end'/>
      <kill name='Sample-Decision-Workflow-error'>
          <message>An error occurred in node : </message>
      </kill>
  </workflow-app>
  */

  public static Workflow sampleForkInWorkflow(){
    return new Workflow("Sample-Fork-Workflow")
      .firstDo(new Fork("a-fork")
        .doThis(new FsAction("single-node"))
        .doThis(new NodeList()
            .firstDo(new FsAction("node-list-first-fs-action"))
            .thenDo(new FsAction("node-list-second-fs-action"))
        )
        .doThis(new FsAction("another-single-node"))
      )
      .thenDo(new FsAction("outer-fs-action"));
  }/*
  <workflow-app name='Sample-Fork-Workflow'
      xmlns='uri:oozie:workflow:0.1'>
      <start to='a-fork'/>
      <fork name='a-fork'>
          <path start='single-node'/>
          <path start='node-list-first-fs-action'/>
          <path start='another-single-node'/>
      </fork>
      <join name='a-fork-JOIN' to='outer-fs-action'/>
      <action name='single-node'>
          <fs></fs>
          <ok to='a-fork-JOIN'/>
          <error to='Sample-Fork-Workflow-error'/>
      </action>
      <action name='node-list-first-fs-action'>
          <fs></fs>
          <ok to='node-list-second-fs-action'/>
          <error to='Sample-Fork-Workflow-error'/>
      </action>
      <action name='node-list-second-fs-action'>
          <fs></fs>
          <ok to='a-fork-JOIN'/>
          <error to='Sample-Fork-Workflow-error'/>
      </action>
      <action name='another-single-node'>
          <fs></fs>
          <ok to='a-fork-JOIN'/>
          <error to='Sample-Fork-Workflow-error'/>
      </action>
      <action name='outer-fs-action'>
          <fs></fs>
          <ok to='Sample-Fork-Workflow-end'/>
          <error to='Sample-Fork-Workflow-error'/>
      </action>
      <end name='Sample-Fork-Workflow-end'/>
      <kill name='Sample-Fork-Workflow-error'>
          <message>An error occurred in node : </message>
      </kill>
  </workflow-app>
  */

  public static Workflow sampleFsActionInWorkflow(){
    return new Workflow("Sample-FS-Action-Workflow")
      .firstDo(new FsAction()
          .delete("PATH_OF_DIR_OR_FILE_TO_DELETE")
          .mkdir("PATH_OF_DIR_MAKE")
      );
  }
  /*
  <workflow-app name='Sample-FS-Action-Workflow'
    xmlns='uri:oozie:workflow:0.1'>
      <start to='NODE-9'/>
      <action name='NODE-9'>
          <fs>
              <delete path='PATH_OF_DIR_OR_FILE_TO_DELETE'/>
              <mkdir path='PATH_OF_DIR_MAKE'/>
          </fs>
          <ok to='Sample-FS-Action-Workflow-end'/>
          <error to='Sample-FS-Action-Workflow-error'/>
      </action>
      <end name='Sample-FS-Action-Workflow-end'/>
      <kill name='Sample-FS-Action-Workflow-error'>
          <message>An error occurred in node : </message>
      </kill>
  </workflow-app>
  */

  public static Workflow sampleJavaActionInWorkflow(){
    return new Workflow("Sample-Java-Action-Workflow")
      .firstDo(new JavaAction("a-java-action")
          .prepare(new DeleteCommand("PATH_OF_DIR_OR_FILE_TO_DELETE"))
          .prepare(new MkdirCommand("PATH_OF_DIR_MAKE"))
          .usingConfig(new Configuration().property("mapred.job.queue.name", QUEUE_NAME))
          .mainClass("MAIN_CLASS_WITH_PACKAGE_NAME")
          .withJavaOpt("JAVA_OPTION")
          .withJavaOpt("JAVA_OPTION_KEY", "JAVA_OPTION_VALUE")
          .withArg("JAVA_ARG")
          .withArg("JAVA_ARG_KEY", "JAVA_ARG_VALUE")
      );
  }
  /*
  <workflow-app name='Sample-Java-Action-Workflow'
    xmlns='uri:oozie:workflow:0.1'>
    <start to='a-java-action'/>
    <action name='a-java-action'>
        <java>
            <configuration>
                <property>
                    <name>mapred.job.queue.name</name>
                    <value>QUEUE_NAME</value>
                </property>
            </configuration>
            <prepare>
                <delete path='PATH_OF_DIR_OR_FILE_TO_DELETE'/>
                <mkdir path='PATH_OF_DIR_MAKE'/>
            </prepare>
            <main-class>MAIN_CLASS_WITH_PACKAGE_NAME</main-class>
            <java-opts>JAVA_OPTION -DJAVA_OPTION_KEY=JAVA_OPTION_VALUE</java-opts>
            <arg>
                <arg>JAVA_ARG</arg>
                <arg>-JAVA_ARG_KEY</arg>
                <arg>JAVA_ARG_VALUE</arg>
            </arg>
            <file>null</file>
            <archive>null</archive>
        </java>
        <ok to='Sample-Java-Action-Workflow-end'/>
        <error to='Sample-Java-Action-Workflow-error'/>
    </action>
    <end name='Sample-Java-Action-Workflow-end'/>
    <kill name='Sample-Java-Action-Workflow-error'>
        <message>An error occurred in node : </message>
    </kill>
  </workflow-app>
  */

  public static Workflow sampleHiveActionInWorkflow(){
    return new Workflow("Sample-Hive-Action-Workflow")
      .firstDo(new HiveAction("a-hive-action")
          .prepare(new DeleteCommand("PATH_OF_DIR_OR_FILE_TO_DELETE"))
          .prepare(new MkdirCommand("PATH_OF_DIR_MAKE"))
          .usingConfig(new Configuration().property("mapred.job.queue.name", QUEUE_NAME))
          .script("HIVE_SCRIPT_PATH")
          .withParameter(new HashMap<String, String>() {{
            put("HIVE_PARAM_KEY", "HIVE_PARAM_VALUE");
          }})
      );
  }
  /*
  <workflow-app name='Sample-Hive-Action-Workflow'
    xmlns='uri:oozie:workflow:0.1'>
    <start to='a-hive-action'/>
    <action name='a-hive-action'>
        <hive
            xmlns='uri:oozie:hive-action:0.2'>
            <configuration>
                <property>
                    <name>mapred.job.queue.name</name>
                    <value>QUEUE_NAME</value>
                </property>
            </configuration>
            <prepare>
                <delete path='PATH_OF_DIR_OR_FILE_TO_DELETE'/>
                <mkdir path='PATH_OF_DIR_MAKE'/>
            </prepare>
            <script>HIVE_SCRIPT_PATH</script>
            <param>HIVE_PARAM_KEY=HIVE_PARAM_VALUE</param>
            <file>null</file>
            <archive>null</archive>
        </hive>
        <ok to='Sample-Hive-Action-Workflow-end'/>
        <error to='Sample-Hive-Action-Workflow-error'/>
    </action>
    <end name='Sample-Hive-Action-Workflow-end'/>
    <kill name='Sample-Hive-Action-Workflow-error'>
        <message>An error occurred in node : </message>
    </kill>
  </workflow-app>
  */
```
