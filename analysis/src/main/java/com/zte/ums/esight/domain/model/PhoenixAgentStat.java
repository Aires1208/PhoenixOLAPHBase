package com.zte.ums.esight.domain.model;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 3/5/17.
 */
public class PhoenixAgentStat {
    private String agentId;
    private long startTime;
    private long collectTime;
    private String macAddress;
    private double cpuUsage;
    private double memUsage;
    private double ioRead;
    private double ioWrite;
    private double dlSpeed;
    private double ulSpeed;
    private String cpuShares;
    private String cpuPeriod;
    private String cpuQuota;
    private String cpuSet;
    private List<ESQueryResult> esQueryResults = new ArrayList<>();

    public PhoenixAgentStat(String agentId, long startTime, long collectTime, String macAddress, double cpuUsage, double memUsage, double ioRead, double ioWrite, double dlSpeed, double ulSpeed, String cpuShares, String cpuPeriod, String cpuQuota, String cpuSet, List<ESQueryResult> esQueryResults) {
        this.agentId = agentId;
        this.startTime = startTime;
        this.collectTime = collectTime;
        this.macAddress = macAddress;
        this.cpuUsage = cpuUsage;
        this.memUsage = memUsage;
        this.ioRead = ioRead;
        this.ioWrite = ioWrite;
        this.dlSpeed = dlSpeed;
        this.ulSpeed = ulSpeed;
        this.cpuShares = cpuShares;
        this.cpuPeriod = cpuPeriod;
        this.cpuQuota = cpuQuota;
        this.cpuSet = cpuSet;
        this.esQueryResults = esQueryResults;
    }

    public void addResult(ESQueryResult esQueryResult) {
        esQueryResults.add(esQueryResult);
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getCollectTime() {
        return collectTime;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public double getMemUsage() {
        return memUsage;
    }

    public double getIoRead() {
        return ioRead;
    }

    public double getIoWrite() {
        return ioWrite;
    }

    public double getDlSpeed() {
        return dlSpeed;
    }

    public double getUlSpeed() {
        return ulSpeed;
    }

    public String getCpuShares() {
        return cpuShares;
    }

    public String getCpuPeriod() {
        return cpuPeriod;
    }

    public String getCpuQuota() {
        return cpuQuota;
    }

    public String getCpuSet() {
        return cpuSet;
    }

    public List<ESQueryResult> getEsQueryResults() {
        return esQueryResults;
    }

    public static Builder Buidler() {
        return new Builder();
    }

    public static class Builder{
        private String agentId;
        private long startTime;
        private long collectTime;
        private String macAddress;
        private double cpuUsage;
        private double memUsage;
        private double ioRead;
        private double ioWrite;
        private double dlSpeed;
        private double ulSpeed;
        private String cpuShares;
        private String cpuPeriod;
        private String cpuQuota;
        private String cpuSet;
        private List<ESQueryResult> esQueryResults = new ArrayList<>();

        public Builder AgentId(String agentId) {
            this.agentId = agentId;
            return this;
        }

        public Builder StartTime(long startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder CollectTime(long collectTime) {
            this.collectTime = collectTime;
            return this;
        }

        public Builder MacAddress(String macAddress) {
            this.macAddress = macAddress;
            return this;
        }

        public Builder CpuUsage(double cpuUsage) {
            this.cpuUsage = cpuUsage;
            return this;
        }

        public Builder MemUsage(double memUsage) {
            this.memUsage = memUsage;
            return this;
        }

        public Builder IoRead(double ioRead) {
            this.ioRead = ioRead;
            return this;
        }

        public Builder IoWrite(double ioWrite) {
            this.ioWrite = ioWrite;
            return this;
        }

        public Builder DlSpeed(double dlSpeed) {
            this.dlSpeed = dlSpeed;
            return this;
        }

        public Builder UlSpeed(double ulSpeed) {
            this.ulSpeed = ulSpeed;
            return this;
        }

        public Builder CpuShares(String cpuShares) {
            this.cpuShares = cpuShares;
            return this;
        }

        public Builder CpuPeriod(String cpuPeriod) {
            this.cpuPeriod = cpuPeriod;
            return this;
        }

        public Builder CpuQuota(String cpuQuota) {
            this.cpuQuota = cpuQuota;
            return this;
        }

        public Builder CpuSet(String cpuSet) {
            this.cpuSet = cpuSet;
            return this;
        }

        public PhoenixAgentStat build() {
            return new PhoenixAgentStat(agentId, startTime, collectTime, macAddress, cpuUsage, memUsage, ioRead, ioWrite, dlSpeed, ulSpeed, cpuShares, cpuPeriod, cpuQuota, cpuSet, esQueryResults);
        }
    }

    public ESQueryResult getResult(String name) {
        for (ESQueryResult esQueryResult : esQueryResults) {
            if (esQueryResult.getName().equals(name)) {
                return esQueryResult;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "PhoenixAgentStat{" +
                "agentId='" + agentId + '\'' +
                ", startTime=" + startTime +
                ", collectTime=" + collectTime +
                ", macAddress='" + macAddress + '\'' +
                ", cpuUsage=" + cpuUsage +
                ", memUsage=" + memUsage +
                ", ioRead=" + ioRead +
                ", ioWrite=" + ioWrite +
                ", dlSpeed=" + dlSpeed +
                ", ulSpeed=" + ulSpeed +
                ", cpuShares='" + cpuShares + '\'' +
                ", cpuPeriod='" + cpuPeriod + '\'' +
                ", cpuQuota='" + cpuQuota + '\'' +
                ", cpuSet='" + cpuSet + '\'' +
                ", esQueryResults=" + esQueryResults +
                '}';
    }
}
