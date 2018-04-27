package com.github.ccaspanello.ael.api;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by ccaspanello on 4/19/18.
 */
public interface RunnerService extends Serializable {

  String getName();
  Result run(String ktr, Map<String, String> parameters);
}
