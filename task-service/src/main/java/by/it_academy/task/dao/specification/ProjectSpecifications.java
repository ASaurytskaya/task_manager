package by.it_academy.task.dao.specification;

import by.it_academy.task.core.ProjectStatus;
import by.it_academy.task.dao.entity.ProjectEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProjectSpecifications {

    public static Specification<ProjectEntity> hasStatus(ProjectStatus status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<ProjectEntity> hasUserInStaff(UUID uuid) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isMember(new ProjectEntity.Staff(uuid), root.get("staff"));
    }
}
