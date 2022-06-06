package com.example.consensus;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class RoleRequest {
    private Long user_id;
    private Long role_id;
}
