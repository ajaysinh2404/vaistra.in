package com.vaistra.master.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AuthenticationResponse {

    String token;
    //  String username;
    String name;

}
