package com.facebook.mobiletech.debug;

import com.facebook.react.uimanager.ReactShadowNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReactShadowNodeUtil {
  // Luatnd: Add debug fn
  public static ArrayList<Map<String,String>> getParentHierachyDebugInfo(ReactShadowNode nodeToUpdate) {
    ArrayList<Map<String,String>> stack = new ArrayList<Map<String,String>>();
    ReactShadowNode node = nodeToUpdate;
    int count = 0;
    boolean hasParent = true;
    while (hasParent) {
      String x = node.getViewClass();
      String x2 = "" + node.getScreenY();
      Map<String,String> info = new HashMap<String, String>(){{
        put("name", x);
        put("y", x2);
//          put("padding", Integer.toString(Arrays.toString(node.mPadding)));
      }};
      stack.add(info);

      hasParent = node.getParent() != null;
      node = node.getParent();
      count++;

      // Break if debug is too large
      if (count > 300) {
        break;
      }
    }

    return stack;
  }

  public static String getParentHierachyDebugInfoString(ReactShadowNode nodeToUpdate) {
    ArrayList<Map<String,String>> debugStack = getParentHierachyDebugInfo(nodeToUpdate);
    String str = "";

    for (Map<String,String> el : debugStack) {
      // str += " > name(y: y, padding: [])"
      str += " > "
        + el.getOrDefault("name", "")
        + "(y: " + el.getOrDefault("y", "")
        + ", padding: " + el.getOrDefault("padding", "")
        + ")";
    }

    return str;
  }
}
