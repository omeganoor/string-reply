package com.beta.replyservice;

public enum ReplyCode {
  reverse(0),
  encode(1);

  private final int value;

  ReplyCode(int value) {
    this.value = value;
  }
}
