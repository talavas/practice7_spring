package shpp.level4.constants;

public enum Roles {
   ADMIN(1), USER(2);

    public long getRoleId() {
        return roleId;
    }
    private final long roleId;
    Roles(long roleId) {
        this.roleId = roleId;
    }


}
