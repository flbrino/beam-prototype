package com.github.ccaspanello.commands;


import java.util.List;

import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.apache.karaf.shell.api.console.CommandLine;
import org.apache.karaf.shell.api.console.Completer;
import org.apache.karaf.shell.api.console.Session;
import org.apache.karaf.shell.support.completers.StringsCompleter;

/**
 * <p>
 * My completer.
 * </p>
 */

@Service
public class RunnerCompleter implements Completer {

  public int complete(Session session, CommandLine commandLine, List<String> candidates) {

    StringsCompleter delegate = new StringsCompleter();
    delegate.getStrings().add("spark");
    delegate.getStrings().add("flink");
    delegate.getStrings().add("spark-runner");
    delegate.getStrings().add("flink-runner");
    delegate.getStrings().add("direct-runner");
    return delegate.complete(session, commandLine, candidates);
  }
}