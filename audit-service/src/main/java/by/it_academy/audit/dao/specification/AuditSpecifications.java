package by.it_academy.audit.dao.specification;

import by.it_academy.audit.dao.entity.AuditEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class AuditSpecifications {

    public static Specification<AuditEntity> hasUser(UUID userId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("userId"), userId);
    }

    public static Specification<AuditEntity> wasFrom(LocalDate date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.<LocalDate>get("dtCreate"), date);
    }

    public static Specification<AuditEntity> wasTo(LocalDate date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.<LocalDate>get("dtCreate"), date);
    }
}
