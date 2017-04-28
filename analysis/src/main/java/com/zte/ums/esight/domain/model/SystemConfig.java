package com.zte.ums.esight.domain.model;

import com.google.common.base.Preconditions;

public class SystemConfig {
    private String kafkaIpAndPort;
    private String kafkaTopic;
    private String hbaseIp;
    private String hbasePort;

    private SystemConfig(String kafkaIpAndPort, String kafkaTopic,
                         String hbaseIp,String hbasePort) {
        this.kafkaIpAndPort = kafkaIpAndPort;
        this.kafkaTopic = kafkaTopic;
        this.hbaseIp = hbaseIp;
        this.hbasePort = hbasePort;
    }

    public String getKafkaIpAndPort() {
        return kafkaIpAndPort;
    }

    public String getKafkaTopic() {
        return kafkaTopic;
    }

    public String getHbaseIp() {
        return hbaseIp;
    }

    public String getHbasePort() {
        return hbasePort;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("KafkaIpAndPort:").append(kafkaIpAndPort).append("\n");
        stringBuilder.append("Topic:").append(kafkaTopic).append("\n");
        stringBuilder.append("HbaseIp:").append(hbaseIp).append("\n");
        stringBuilder.append("HbasePort:").append(hbasePort).append("\n");

        return stringBuilder.toString();
    }

    public static class Builder {
        private String kafkaIpAndPort;
        private String topic;
        private String hbaseIp;
        private String hbasePort;


        public Builder() {

        }

        public Builder KafkaIpAndPort(String ipAndPort) {
            this.kafkaIpAndPort = ipAndPort;
            return this;
        }

        public Builder KafkaTopic(String topic) {
            this.topic = topic;
            return this;
        }

        public Builder HbaseIp(String hbaseIp) {
            this.hbaseIp = hbaseIp;
            return this;
        }

        public Builder HbasePort(String hbasePort) {
            this.hbasePort = hbasePort;
            return this;
        }

        public SystemConfig build() {
//            Preconditions.checkArgument(kafkaIpAndPort != null,"kafkaIpAndPort is null");
//            Preconditions.checkArgument(topic != null,"kafkaTopic is null");
//            Preconditions.checkArgument(hbaseIp != null,"hbaseIp is null");
//            Preconditions.checkArgument(hbasePort != null,"hbasePort is null");

            SystemConfig systemConfig = new SystemConfig(kafkaIpAndPort,topic,hbaseIp,hbasePort);


            return systemConfig;
        }

    }

}
