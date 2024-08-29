package com.gabut.mantap.mantap.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseFormatter {
  private static Map<String, Object> response = new HashMap<>();
  private static Map<String, Object> meta = new HashMap<>();

  static {
    meta.put("code", 200);
    meta.put("status", "success");
    meta.put("message", null);
    meta.put("description", null);
    meta.put("internal_code", 2000);
    response.put("meta", meta);
    response.put("data", null);
  }

  public static ResponseEntity<Map<String, Object>> success(Object data, String message, String description,
      Integer internalCode, Integer code) {
    if (code != null) {
      meta.put("code", code);
    }
    meta.put("message", message);
    meta.put("description", description);
    meta.put("internal_code", internalCode);
    response.put("meta", meta);
    response.put("data", data);

    return new ResponseEntity<>(response, HttpStatus.valueOf((Integer) meta.get("code")));
  }

  public static ResponseEntity<Map<String, Object>> error(Object data, String message, String description,
      Integer internalCode, int code) {
    meta.put("status", "error");
    meta.put("code", code);
    meta.put("message", message);
    meta.put("description", description != null ? description : "No additional description provided");
    meta.put("internal_code", internalCode != null ? internalCode : "No additional internal code provided");
    response.put("meta", meta);
    response.put("data", data);

    return new ResponseEntity<>(response, HttpStatus.valueOf(code));
  }

  public static ResponseEntity<Map<String, Object>> errorUserNotHaveRightPermission() {
    meta.put("status", "error");
    meta.put("code", 403);
    meta.put("message", "User doesn't have right permission");
    meta.put("description", "You can't access this resource");
    response.put("meta", meta);
    response.put("data", null);

    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
  }

  public static ResponseEntity<Map<String, Object>> errorServerError(Object data) {
    meta.put("status", "error");
    meta.put("code", 500);
    meta.put("message", "Something went wrong");
    response.put("meta", meta);
    response.put("data", data);

    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  public static String mappingErrorMessageToString(String[] errorMessages) {
    return String.join(", ", errorMessages);
  }
}
