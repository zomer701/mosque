package com.live.mosque.converter;

import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.Map;

public class MapTypeConverter implements AttributeConverter<Map> {

    @Override
    public AttributeValue transformFrom(Map map) {
        Map<String, AttributeValue> attributeValueMap = new HashMap<>();
        for (Object key: map.keySet()) {
            attributeValueMap.put((String) key, AttributeValue.fromS((String) map.get(key)));
        }

        return AttributeValue.fromM(attributeValueMap);

    }

    @Override
    public Map transformTo(AttributeValue attributeValue) {
        Map<String, AttributeValue> m = attributeValue.m();
        Map<String, String> result = new HashMap<>();
        for (String key: m.keySet()) {
            result.put(key, m.get(key).s());
        }

        return result;
    }

    @Override
    public EnhancedType<Map> type() {
        return EnhancedType.of(Map.class);
    }

    @Override
    public AttributeValueType attributeValueType() {
        return AttributeValueType.M;
    }
}
