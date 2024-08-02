package com.app.database.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private Long id;

    private String ipAddress;

    public UserInfo(String ipAddress) {this.ipAddress = ipAddress;}
}
