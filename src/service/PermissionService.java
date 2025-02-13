package service;

import model.User;
import model.Permission;

public class PermissionService {
    
    public boolean hasPermission(User user, Permission permission) {
        return user.getRole().getPermissions().contains(permission);
    }
}
