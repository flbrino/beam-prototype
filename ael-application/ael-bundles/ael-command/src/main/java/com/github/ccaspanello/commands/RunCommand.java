package com.github.ccaspanello.commands;

import com.github.ccaspanello.ael.api.RunCommandService;
import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.Completion;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;

import java.util.HashMap;
import java.util.Map;

@Command( scope = "ael", name = "run", description = "Runs transformation on AEL" )
@Service
public class RunCommand implements Action {

  @Reference
  private RunCommandService runCommandService;

  @Argument( index = 0, name = "runner", description = "The AEL Engine you want to run on.", required = false,
    multiValued = false )
  @Completion( RunnerCompleter.class )
  String runner = null;

  @Override
  public Object execute() throws Exception {
    String ktr = "";
    Map<String, String> parameters = new HashMap<>();
    if ( runCommandService != null ) {
      runCommandService.run( runner, ktr, parameters );
      return "";
    } else {
      return "Unable to find run command service.";
    }
  }
}
