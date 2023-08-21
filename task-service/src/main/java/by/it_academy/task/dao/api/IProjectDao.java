package by.it_academy.task.dao.api;

import by.it_academy.task.dao.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IProjectDao extends JpaRepository<ProjectEntity, UUID> {
}
