package by.it_academy.task.dao.specification;

import by.it_academy.task.core.ProjectRef;
import by.it_academy.task.core.TaskStatus;
import by.it_academy.task.core.UserRef;
import by.it_academy.task.dao.entity.TaskEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskSpecifications {

    public static Specification<TaskEntity> hasStatus(List<TaskStatus> list) {
        return (root, query, criteriaBuilder) ->
                root.get("status").in(list);
    }

    public static Specification<TaskEntity> hasImplementer(List<UserRef> list) {
        return (root, query, criteriaBuilder) ->
                root.get("implementer").in(list);
    }

    public static Specification<TaskEntity> hasProject(List<ProjectRef> list) {
        return (root, query, criteriaBuilder) ->
                root.get("project").in(list);
    }
}
