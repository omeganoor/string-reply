package com.beta.replyservice;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {

  public ReplyData convertData(String input){
    if (!input.contains("-")){
      throw new RuntimeException("Invalid input");
    }
    String resultData = "";
    String [] inputs= input.split("-");
    String code = inputs[0];
    String data = inputs[1];
    List<ReplyCode> codes = new ArrayList<>();
    try {
      codes = parseCode(code);
    }catch (RuntimeException e) {
      throw new RuntimeException("Invalid input");
    }
    resultData = convertingData(codes, data);
    return new ReplyData(resultData);
  }

  private String convertingData(List<ReplyCode> codes, String data) {
    for (ReplyCode code : codes){
      data = manipulatingData(code, data);
    }
    return data;
  }

  private String manipulatingData(ReplyCode code, String data) {
    switch (code){
      case reverse:
        return reversingData(data);
      case encode :
        return encodingData(data);
      default:
        return null;
    }
  }

  private String encodingData(String data) {
    return bytesToHex(digest(data.getBytes(UTF_8)));
  }

  private byte[] digest(byte[] input) {
    MessageDigest md;
    try {
      md = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalArgumentException(e);
    }
    byte[] result = md.digest(input);
    return result;
  }

  private String bytesToHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) {
      sb.append(String.format("%02x", b));
    }
    return sb.toString();
  }

  private String reversingData(String data) {
    return  new StringBuilder(data).reverse().toString();
  }

  private List<ReplyCode> parseCode(String code) {
    List<ReplyCode> codes = new ArrayList<>();
    for (char cd : code.toCharArray()){
      switch (cd){
        case '1':
          codes.add(ReplyCode.reverse);
          break;
        case '2' :
          codes.add(ReplyCode.encode);
          break;
        default:
          throw new RuntimeException("Invalid input");
      }
    }
    return codes;
  }

}
