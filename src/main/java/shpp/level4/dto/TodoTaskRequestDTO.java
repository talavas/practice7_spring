package shpp.level4.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoTaskRequestDTO {
    @Nullable
    private long id;
    private String name;
    @Nullable
    private String status;
}
