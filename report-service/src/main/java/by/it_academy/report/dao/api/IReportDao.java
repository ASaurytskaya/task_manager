package by.it_academy.report.dao.api;

import by.it_academy.report.dao.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IReportDao extends JpaRepository<ReportEntity, UUID> {
}
