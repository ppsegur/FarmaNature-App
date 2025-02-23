package com.salesianostriana.dam.farma_app.query;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import jakarta.persistence.criteria.Predicate;

@Log
@AllArgsConstructor
public abstract class GenericSpecificationBuilder<U> {

    private List<SearchCriteria> params;


    public Specification<U> build() {
        if (params.size() == 0) {
            return null;
        }

        log.info("Adding first specification " + params.get(0));
        Specification<U> result = build(params.get(0));
        log.info("Specification: " + result.toString());

        for(int i = 1; i < params.size(); i++) {
            log.info("Adding new specification " + params.get(i));
            result = result.and(build(params.get(i)));
            log.info(result.toString());
            log.info("Specification: " + result.toString());
        }

        log.info("Final Specification: " + result.toString());


        return result;
    }

    private Specification<U> build(SearchCriteria criteria) {
        return new Specification<U>() {
            @Override
            public Predicate toPredicate(Root<U> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                //log.info("Building specification: " + criteria.toString());
                if (criteria.operation().equalsIgnoreCase(">")) {
                    return (Predicate) builder.greaterThanOrEqualTo(
                            root.<String> get(criteria.key()), criteria.value().toString());
                }
                else if (criteria.operation().equalsIgnoreCase("<")) {
                    return (Predicate) builder.lessThanOrEqualTo(
                            root.<String> get(criteria.key()), criteria.value().toString());
                }
                else if (criteria.operation().equalsIgnoreCase(":")) {
                    if (root.get(criteria.key()).getJavaType() == String.class) {
                        return (Predicate) builder.like(
                                root.<String>get(criteria.key()), "%" + criteria.value() + "%");
                    } else {
                        return (Predicate) builder.equal(root.get(criteria.key()), criteria.value());
                    }
                }
                return null;
            }
        };
    }

}