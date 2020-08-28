package com.facebook.mobiletech.debug;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Slack {
  // Save last send to avoid send multiple msg in 1 secs
  public static long lastSendSlackTs = 0; // NOTE: You can miss some message when using multiple sendMsg source, please convert this class to non-static usage instead
  public static long THROTLE_IN_SECONDS = 3; // do not send more than 1 msg in 3 seconds
  public static String WEB_HOOK_URL = "https://hooks.slack.com/services/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";

  public static void sendMsg(String msg) {
    try {
      long nowTs = System.currentTimeMillis() / 1000L;
      if (nowTs - lastSendSlackTs <= THROTLE_IN_SECONDS) {
        return;
      }

      OkHttpClient client = new OkHttpClient().newBuilder().build();
      MediaType mediaType = MediaType.parse("application/json");
      String normalizedMsg = msg
        .replace('"', '+')
        .replace('{', '+')
        .replace('}', '+');
      RequestBody body = RequestBody.create(mediaType, "{\"text\": \"" + normalizedMsg + "\"}");
      Request request = new Request.Builder()
        .url(WEB_HOOK_URL)
        .method("POST", body)
        .addHeader("Content-Type", "application/json")
        .build();
      Response response = client.newCall(request).execute();

      lastSendSlackTs = nowTs;
    } catch (Exception e) {

    }
  }
}
