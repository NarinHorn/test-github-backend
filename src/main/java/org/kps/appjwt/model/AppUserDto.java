package org.kps.appjwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDto {
    private Integer id;
    private String email;
    private List<String> roles;
}
