package shpp.level4.constants;
public enum TaskStatuses {
    PLANNED(1),
    WORK_IN_PROGRESS(2),
    POSTPONED(3),
    NOTIFIED(4),
    SIGNED(5),
    DONE(6),
    CANCELLED(7);

    private final long statusId;

    TaskStatuses(long statusId) {
       this.statusId = statusId;
    }

    public TaskStatuses fromCode(String status){
        for (TaskStatuses taskStatus : TaskStatuses.values()) {
            if (taskStatus.name().equalsIgnoreCase(status)) {
                return taskStatus;
            }
        }
        return null;
    }
}
