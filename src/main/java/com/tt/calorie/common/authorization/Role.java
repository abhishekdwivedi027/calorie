package com.tt.calorie.common.authorization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Role {
    ADMIN,
    USER;

    public static List<Permission> getPermissions(Role role) {
        List<Permission> permissions = new ArrayList<>();
        if (role == Role.ADMIN) {
            Permission[] adminPermissions = {Permission.CRUD_USER,
                    Permission.CREATE_CALORIE_INTAKE_ENTRY,
                    Permission.READ_CALORIE_INTAKE_ENTRY,
                    Permission.UPDATE_CALORIE_INTAKE_ENTRY,
                    Permission.DELETE_CALORIE_INTAKE_ENTRY};
            permissions.addAll(Arrays.asList(adminPermissions));
        } else if (role == Role.USER) {
            Permission[] userPermissions = {Permission.PATCH_CALORIE_LIMIT_FOR_SELF_ONLY,
                    Permission.CREATE_CALORIE_INTAKE_ENTRY_FOR_SELF_ONLY,
                    Permission.READ_CALORIE_INTAKE_ENTRY_FOR_SELF_ONLY};
            permissions.addAll(Arrays.asList(userPermissions));
        }
        return permissions;
    }
}
