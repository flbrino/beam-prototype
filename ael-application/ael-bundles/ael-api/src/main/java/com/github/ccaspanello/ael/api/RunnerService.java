package com.github.ccaspanello.ael.api;

import java.util.Map;

/**
 * Created by ccaspanello on 4/19/18.
 */
public interface RunnerService {

  String getName();
  Result run(String ktr, Map<String, String> parameters);
}
