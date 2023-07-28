package shpp.level4.constants;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@Setter
@NoArgsConstructor
public class TaskStatusHandler {
    private TaskStatuses fromStatus;
    private Set<TaskStatuses> allowedToStatuses;
    private TaskStatusHandler nextHandler;
    public TaskStatusHandler(TaskStatuses from){
        this.fromStatus = from;
        this.allowedToStatuses = new HashSet<>();
    }
    public void addAllowedStatus(TaskStatuses to){
        if(allowedToStatuses == null){
            allowedToStatuses = new HashSet<>();
        }
        allowedToStatuses.add(to);
    }
    public boolean isHandleTransition(TaskStatuses from, TaskStatuses to) {
        if (fromStatus.equals(from) && fromStatus.equals(to)){
            return true;
        }
        if(fromStatus.equals(from)
                && !allowedToStatuses.isEmpty()
                && allowedToStatuses.contains(to)
        ){
            return true;
        }

        if(nextHandler != null){
            return nextHandler.isHandleTransition(from, to);
        }

        return false;
    }
}
