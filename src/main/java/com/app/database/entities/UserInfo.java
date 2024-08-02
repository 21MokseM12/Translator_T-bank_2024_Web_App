package com.app.database.entities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private Long id;

    private String ipAddress;

    public UserInfo(String ipAddress) {this.ipAddress = ipAddress;}
}
