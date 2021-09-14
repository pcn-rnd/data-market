package co.kr.pcninc.data.marketcore.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Map;


public class ObjectConverter {

    static public String toJson(Object bean) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        String jsonString = null;
        if (!ObjectUtils.isEmpty(bean)) {
            jsonString = mapper.writeValueAsString(bean);
        }
        return StringUtils.isEmpty(jsonString) ? "{}" : jsonString;
    }

    static public String toPrettyJson(Object bean) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        String jsonString = null;
        if (!ObjectUtils.isEmpty(bean)) {
            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bean);
        }
        return StringUtils.isEmpty(jsonString) ? "{}" : jsonString;
    }

    static public <T> T toObject(String json, Class<T> clazz) throws JsonProcessingException {

        return objectMapper().readValue(json, clazz);
    }

    static public <T> T toObject(String json, TypeReference<T> ref) throws JsonProcessingException {
        return objectMapper().readValue(json, ref);
    }

    static public <T> T toObject(Map<String, Object> map, Class<T> clazz) throws JsonProcessingException {

        return toObject(toJson(map), clazz);
    }

    static public Map<String, Object> toMap(String json) throws JsonProcessingException {

        return objectMapper().readValue(json, new TypeReference<Map<String, Object>>() {});
    }

    static public <T> Map<String, Object> toMap(Object bean) throws JsonProcessingException {

        return toMap(toJson(bean));
    }


    private static ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        mapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        
        return mapper;
    }

    static private boolean ignoreProperty(String name, String... ignoreProperties) {
        for (String propName : ignoreProperties) {
            if (name.equals(propName)) {
                return true;
            }
        }
        return false;
    }
}
