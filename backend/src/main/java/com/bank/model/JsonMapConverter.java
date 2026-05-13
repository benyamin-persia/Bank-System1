package com.bank.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Map;

@Converter
public class JsonMapConverter
        implements AttributeConverter<Map<String, Object>, String> {

    private static final TypeReference<Map<String, Object>> MAP_TYPE =
            new TypeReference<>() {}; // Preserve generic map type for Jackson deserialization
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, MAP_TYPE); // Deserialize with generic type info to avoid raw Map warnings
        } catch (Exception e) {
            return null;
        }
    }
}
