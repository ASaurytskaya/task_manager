package by.it_academy.report.service;


import by.it_academy.report.core.ReportStatus;
import by.it_academy.report.core.ReportType;
import by.it_academy.report.core.dto.Audit;
import by.it_academy.report.core.dto.Report;
import by.it_academy.report.core.dto.ReportParamAudit;
import by.it_academy.report.dao.api.IReportDao;
import by.it_academy.report.dao.entity.ReportEntity;
import by.it_academy.report.service.api.IReportService;
import by.it_academy.report.service.exception.ReportNotFoundException;
import by.it_academy.report.util.TPage;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ReportService implements IReportService {

    private final IReportDao reportDao;
    private final AuditService auditService;

    private final static String PATH_TO_DIR = "report-service/src/main/java/by/it_academy/report/files";
    private Path foundFile;

    public ReportService(IReportDao reportDao, AuditService auditService) {
        this.reportDao = reportDao;
        this.auditService = auditService;
    }

    @Transactional
    @Override
    public void save(ReportType type, ReportParamAudit param) {
        ReportEntity entity = new ReportEntity();
        entity.setUuid(UUID.randomUUID());
        entity.setDtCreate(LocalDateTime.now());
        entity.setDtUpdate(entity.getDtCreate());
        entity.setType(type);
        entity.setDescription(getDescription(param));
        entity.setUserUuid(param.getUser());
        entity.setFrom(param.getFrom() == null ? null : param.getFrom().atStartOfDay());
        entity.setTo(param.getTo() == null ? null : param.getTo().atStartOfDay());
        entity.setStatus(ReportStatus.PROGRESS);
        reportDao.saveAndFlush(entity);

        List<Audit> list = auditService.getList(param);
        ReportEntity entity2 = updateStatus(entity, ReportStatus.LOADED);

        File file = new File(PATH_TO_DIR + "/" + entity.getUuid() + ".xlsx");


        try(FileOutputStream out = new FileOutputStream(file);
            ByteArrayInputStream in = new ExcelGeneratorService(list).generateExcelFile()) {
                out.write(in.readAllBytes());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        updateStatus(entity2, ReportStatus.DONE);

    }

    @Transactional(readOnly = true)
    @Override
    public TPage<Report> getPage(int page, int size) {

        List<ReportEntity> allEntities = reportDao.findAll();

        TPage<Report> pageResult = new TPage<>();

        int countEntities = allEntities.size();
        int countPages = (int) Math.ceil(countEntities / (double) size );

        if(page > countPages) {
            page = countPages -1;
        }
        pageResult.setNumber(page);
        pageResult.setTotal_pages(countPages);
        pageResult.setSize(size);
        pageResult.setTotal_element(countEntities);
        pageResult.setFirst(page == 0);
        pageResult.setLast(page == countPages - 1 || countPages == 0);
        int count = 0;
        pageResult.setContent(new ArrayList<>());
        for(int i = page * size; i <= (page + 1) * size && i < countEntities; i++) {
            pageResult.addElement(entityToDto(allEntities.get(i)));
            count++;
        }
        pageResult.setNumber_of_elements(count);
        return pageResult;
    }

    @Transactional(readOnly = true)
    @Override
    public ReportStatus getStatus(UUID uuid) {
        ReportEntity entity = reportDao.findById(uuid).orElseThrow(ReportNotFoundException::new);

        return entity.getStatus();
    }

    @Transactional(readOnly = true)
    @Override
    public Report getReport(UUID uuid) {
        ReportEntity entity = reportDao.findById(uuid).orElseThrow(ReportNotFoundException::new);
        return entityToDto(entity);
    }

    @Override
    public Resource getFile(UUID uuid) {

        try( Stream<Path> stream = Files.list(Path.of(PATH_TO_DIR))) {
            stream.forEach(file -> {
                if (file.getFileName().toString().startsWith(uuid.toString())) {
                    foundFile = file;
                }
            });
            if (foundFile != null) {
                return new UrlResource(foundFile.toUri());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Transactional
    private ReportEntity updateStatus(ReportEntity entity, ReportStatus status) {
        reportDao.delete(entity);
        ReportEntity newEntity = new ReportEntity();
        newEntity.setUuid(entity.getUuid());
        newEntity.setDtCreate(entity.getDtCreate());
        newEntity.setDtUpdate(LocalDateTime.now());
        newEntity.setType(entity.getType());
        newEntity.setDescription(entity.getDescription());
        newEntity.setUserUuid(entity.getUserUuid());
        newEntity.setFrom(entity.getFrom());
        newEntity.setTo(entity.getTo());
        newEntity.setStatus(status);
        reportDao.saveAndFlush(newEntity);
        return newEntity;
    }

    private Report entityToDto(ReportEntity entity) {
        Report report = new Report();
        report.setUuid(entity.getUuid());
        report.setDtCreate(entity.getDtCreate());
        report.setDtUpdate(entity.getDtUpdate());
        report.setType(entity.getType());
        report.setDescription(entity.getDescription());
        report.setStatus(entity.getStatus());
        report.setParams(new ReportParamAudit(entity.getUserUuid(),
                entity.getFrom() == null ? null : entity.getFrom().toLocalDate(),
                entity.getTo() == null ? null : entity.getTo().toLocalDate()));
        return report;
    }

    private String getDescription(ReportParamAudit param) {
        StringBuilder builder = new StringBuilder();
        builder.append("Журнал аудита ");

        LocalDate from = param.getFrom();
        if( from != null) {
            builder.append(" c ").append(from);
        }

        LocalDate to = param.getTo();
        if( from != null) {
            builder.append(" по ").append(to);
        }

        if(from == null && to == null) {
            builder.append("за все время");
        }
        return builder.toString();
    }
}
