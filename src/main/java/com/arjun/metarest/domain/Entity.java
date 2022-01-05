package com.arjun.metarest.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Entity {

    @JsonProperty
    private Integer id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String dataschema;
    @JsonProperty
    private String data;

    public Entity() {
        super();
    }

    public Entity(String name, String dataschema, String data) {
        super();
        this.name = name;
        this.dataschema = dataschema;
        this.data = data; 
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
        return dataschema;
    }

    public void setDataSchema(String dataschema) {
        this.dataschema = dataschema;
    }

    public String  getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dataschema='" + dataschema + '\'' +
                ", data=" + data +
                '}';
    }
}
