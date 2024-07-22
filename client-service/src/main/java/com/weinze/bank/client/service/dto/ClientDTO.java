package com.weinze.bank.client.service.dto;

import com.weinze.bank.client.domain.enums.Gender;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

public class ClientDTO implements Serializable {

    private Long id;

    @NotNull
    private Long identification;

    @NotNull
    private String name;

    @NotNull
    private Gender gender;

    @NotNull
    private Integer age;

    @NotNull
    private String address;

    @NotNull
    private Long phone;

    @NotNull
    private Long clientId;

    @NotNull
    private String password;

    @NotNull
    private Boolean enabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdentification() {
        return identification;
    }

    public void setIdentification(Long identification) {
        this.identification = identification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
                "id=" + id +
                ", identification=" + identification +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", phone=" + phone +
                ", clientId=" + clientId +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
