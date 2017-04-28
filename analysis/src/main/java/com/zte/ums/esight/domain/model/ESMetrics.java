package com.zte.ums.esight.domain.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 2/13/17.
 */
public class ESMetrics {
    private Map<String, Object> values = new HashMap<String, Object>();
    private long collectTime;

    public ESMetrics(long collectTime, Map<String, Object> values) {
        this.values = values;
        this.collectTime = collectTime;
    }

    public ESMetrics(Map<String, Object> values) {
        this.values = values;
    }

    public Object getValue(String key) {

//        Optional<Object> optional = Optional.ofNullable(values.get(key));
//        return optional.orElse(new Object());
        return values.get(key);
    }

    public long getCollectTime() {
        return collectTime;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("\n");


        for (Map.Entry<String, Object> entry : values.entrySet()) {
            if (entry.getKey().equals(ESConst.COLLECT_TIME)) {
                String collectTime = getTimeStr(entry.getValue());
                stringBuilder.append("collectTime=").append(collectTime).append("\n");
                stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
            } else {

                stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
            }
        }

        return stringBuilder.toString();
    }

    private String getTimeStr(Object value) {
        if (value instanceof Long) {
            return new Date((Long) value).toString();
        } else if (value instanceof String) {
            return (String) value;
        }

        return String.valueOf(value);
    }
}
