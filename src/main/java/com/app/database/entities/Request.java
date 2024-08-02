package com.app.database.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Request {

    private Long id;

    private UserInfo userInfo;

    private String request;

    private String response;

    public Request(UserInfo userInfo, String request, String response) {
        this.userInfo = userInfo;
        this.request = request;
        this.response = response;
    }
}
