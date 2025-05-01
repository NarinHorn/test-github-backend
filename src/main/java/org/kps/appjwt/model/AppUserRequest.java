package org.kps.appjwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserRequest {
    private String email;
    private String password;
    private List<Integer> roles;
}
