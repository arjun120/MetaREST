package com.arjun.metarest.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EntityMetaData {
    @JsonProperty
    private Integer id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String dataSchema;

    public EntityMetaData() {
        super();
    }

    public EntityMetaData(Integer id, String name, String dataSchema) {
        super();
        this.id = id;
        this.name = name;
        this.dataSchema = dataSchema;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataSchema() {
        return dataSchema;
    }

    public void setDataSchema(String dataSchema) {
        this.dataSchema = dataSchema;
    }

    @Override
    public String toString() {
        return "EntityMetaData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dataSchema='" + dataSchema + 
                '}';
    }
}
