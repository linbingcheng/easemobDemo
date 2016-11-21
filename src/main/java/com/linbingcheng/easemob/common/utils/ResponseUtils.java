package com.linbingcheng.easemob.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linbingcheng.easemob.common.wrapper.ResponseWrapper;

import java.io.IOException;

public class ResponseUtils {
    public static JsonNode ResponseBodyToJsonNode(ResponseWrapper response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(response.getResponseBody());
        JsonNode jsonNode = mapper.readTree(json);
        return jsonNode;
    }
}
