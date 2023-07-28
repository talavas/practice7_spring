package shpp.level4.constants;

public enum Permissions {
    TASK_READ(1),
    TASK_WRITE(2);
    public long getPermissionId() {
        return permissionId;
    }
    private final long permissionId;
    Permissions(long permissionId) {
        this.permissionId = permissionId;
    }
}
