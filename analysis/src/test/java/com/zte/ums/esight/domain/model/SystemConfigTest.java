package com.zte.ums.esight.domain.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SystemConfigTest {

    @Test
    public void should_return_an_systemConfig_when_do_build() {

        SystemConfig systemConfig = new SystemConfig.Builder()
                .KafkaIpAndPort("127.0.0.1:9092")
                .KafkaTopic("KafkaTopic")
                .HbaseIp("127.0.0.1")
                .HbasePort("2181")
                .build();

        assertEquals("127.0.0.1:9092", systemConfig.getKafkaIpAndPort());
        assertEquals("KafkaTopic", systemConfig.getKafkaTopic());
        assertEquals("127.0.0.1", systemConfig.getHbaseIp());
        assertEquals("2181", systemConfig.getHbasePort());
    }

    @Test
    public void should_return_expected_string_when_write_a_systemConfig_object() {

        String expecedString = "KafkaIpAndPort:127.0.0.1:9092\n" +
                "Topic:KafkaTopic\n" +
                "HbaseIp:127.0.0.1\n" +
                "HbasePort:2181\n";

        SystemConfig systemConfig = new SystemConfig.Builder()
                .KafkaIpAndPort("127.0.0.1:9092")
                .KafkaTopic("KafkaTopic")
                .HbaseIp("127.0.0.1")
                .HbasePort("2181")
                .build();

        assertEquals(expecedString, systemConfig.toString());
    }

    @Test
    public void should_return_null_when_KafkaIpAndPort_is_null() throws Exception {

        SystemConfig systemConfig = new SystemConfig.Builder().build();
        assertEquals(systemConfig.getKafkaIpAndPort(),null);
    }

    @Test
    public void should_expect_ipAndPort_when_ipAndPort_is_setted() throws Exception {

        SystemConfig systemConfig = new SystemConfig.Builder()
                .KafkaIpAndPort("127.0.0.1:9092")
                .build();
        assertEquals(systemConfig.getKafkaIpAndPort(),"127.0.0.1:9092");
    }


    @Test
    public void should_return_null_when_hbaseIp_is_null() throws Exception {

        SystemConfig systemConfig = new SystemConfig.Builder().build();
        assertEquals(systemConfig.getHbaseIp(),null);
    }

    @Test
    public void should_expect_ip_when_ip_is_setted() throws Exception {

        SystemConfig systemConfig = new SystemConfig.Builder()
                .HbaseIp("127.0.0.1")
                .build();
        assertEquals(systemConfig.getHbaseIp(),"127.0.0.1");
    }

    @Test
    public void should_return_null_when_hbasePort_is_null() throws Exception {

        SystemConfig systemConfig = new SystemConfig.Builder().build();
        assertEquals(systemConfig.getHbasePort(),null);
    }

    @Test
    public void should_expect_port_when_hbasePort_is_setted() throws Exception {

        SystemConfig systemConfig = new SystemConfig.Builder()
                .HbasePort("2181")
                .build();
        assertEquals(systemConfig.getHbasePort(),"2181");
    }

    @Test
    public void should_return_null_when_topic_is_null() throws Exception {

        SystemConfig systemConfig = new SystemConfig.Builder().build();
        assertEquals(systemConfig.getKafkaTopic(),null);
    }

    @Test
    public void should_expect_topic_when_topic_is_setted() throws Exception {

        SystemConfig systemConfig = new SystemConfig.Builder()
                .KafkaTopic("nfvtrace")
                .build();
        assertEquals(systemConfig.getKafkaTopic(),"nfvtrace");
    }
}