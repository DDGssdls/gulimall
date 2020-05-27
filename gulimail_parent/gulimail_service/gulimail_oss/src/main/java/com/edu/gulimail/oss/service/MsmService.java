package com.edu.gulimail.oss.service;

import java.util.Map;

public interface MsmService {
    boolean sendCodeByAly(Map<String, String> param, String phoneNum);
}
