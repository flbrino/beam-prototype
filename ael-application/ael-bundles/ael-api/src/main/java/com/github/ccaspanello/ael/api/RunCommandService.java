package com.github.ccaspanello.ael.api;

import java.util.Map;

/**
 * Created by ccaspanello on 4/30/18.
 */
public interface RunCommandService {

  void run(String runner, String ktr, Map<String,String> parameters);
}
