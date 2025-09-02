package co.com.crediya.r2dbc.filter;

import org.springframework.data.relational.core.query.Criteria;

import java.util.Collection;

public class CriteriaBuilder {
    private Criteria criteria = Criteria.empty();

    public static CriteriaBuilder create() {
        return new CriteriaBuilder();
    }

    public CriteriaBuilder add(String column, Object value, FilterOperator operator) {
        if (value == null) return this;

        switch (operator) {
            case EQUALS -> criteria = criteria.and(column).is(value);
            case GREATER_THAN -> criteria = criteria.and(column).greaterThan(value);
            case GREATER_THAN_OR_EQUALS -> criteria = criteria.and(column).greaterThanOrEquals(value);
            case LESS_THAN -> criteria = criteria.and(column).lessThan(value);
            case LESS_THAN_OR_EQUALS -> criteria = criteria.and(column).lessThanOrEquals(value);
            case LIKE -> criteria = criteria.and(column).like("%" + value + "%");
            case IN -> {
                if (value instanceof Collection<?> collection) {
                    criteria = criteria.and(column).in(collection);
                } else {
                    criteria = criteria.and(column).is(value);
                }
            }
        }
        return this;
    }

    public Criteria build() {
        return criteria;
    }
}
