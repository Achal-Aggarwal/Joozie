package com.joozie.core.action;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class FsActionTest {
  @Test
  public void shouldAbleToAddDeleteCommand(){
    FsAction fs = new FsAction();
    assertThat(fs.delete("DELETE_PATH"), is(fs));
    assertThat(fs.getActionXmlString(), is(
      "<fs><delete path='DELETE_PATH'/></fs>"
    ));
  }

  @Test
  public void shouldAbleToAddMkdirCommand(){
    FsAction fs = new FsAction();
    assertThat(fs.mkdir("MKDIR_PATH"), is(fs));
    assertThat(fs.getActionXmlString(), is(
      "<fs><mkdir path='MKDIR_PATH'/></fs>"
    ));
  }

  @Test
  public void shouldAbleToAddMoveCommand(){
    FsAction fs = new FsAction();
    assertThat(fs.move("SRC", "DEST"), is(fs));
    assertThat(fs.getActionXmlString(), is(
      "<fs><move source='SRC' target='DEST'/></fs>"
    ));
  }

  @Test
  public void shouldAbleToAddChmodCommand(){
    FsAction fs = new FsAction();
    assertThat(fs.chmod("PATH", "755", false), is(fs));
    assertThat(fs.getActionXmlString(), is(
      "<fs><chmod path='PATH' permissions='755' dir-files='false'/></fs>"
    ));
  }

  @Test
  public void shouldAbleToAddAnotherCommandInOrder(){
    FsAction fs = new FsAction();
    assertThat(fs.delete("DELETE_PATH"), is(fs));
    assertThat(fs.mkdir("MKDIR_PATH"), is(fs));
    assertThat(fs.getActionXmlString(), is(
      "<fs><delete path='DELETE_PATH'/><mkdir path='MKDIR_PATH'/></fs>"
    ));
  }
}