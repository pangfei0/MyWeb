package juli.infrastructure.persist;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * 用于构建动态查询规范
 */
public class DynamicSpecification {

    public static <T> Specification<T> buildSpecification(final SearchFilter... filter) {
        return buildSpecification(Arrays.asList(filter));
    }

    public static <T> Specification<T> buildSpecification(final String fieldName, final SearchFilter.Operator operator, final Object value) {
        List<SearchFilter> searchFilters = new ArrayList<SearchFilter>();
        SearchFilter filter = new SearchFilter(fieldName, operator, value);
        searchFilters.add(filter);
        return buildSpecification(searchFilters);
    }

    /**
     * Map中的Key形如：filedName(type)_operator
     *
     * @param searchParams
     */
    public static <T> Specification<T> buildSpecification(final Map<String, Object> searchParams) {
        List<SearchFilter> filters = SearchFilter.parse(searchParams);
        Specification<T> spec = buildSpecification(filters);
        return spec;
    }

    public static <T> Specification<T> buildSpecification(final Collection<SearchFilter> filters) {
        return new Specification<T>() {

            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                if (filters != null && filters.size() > 0) {

                    List<Predicate> predicates = new ArrayList<>();
                    for (SearchFilter filter : filters) {
                        // nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
                        String[] names = StringUtils.split(filter.getFieldName(), ".");
                        Path expression = root.get(names[0]);
                        for (int i = 1; i < names.length; i++) {
                            expression = expression.get(names[i]);
                        }

                        // logic operator
                        switch (filter.getOperator()) {
                            case EQ:
                                predicates.add(builder.equal(expression, filter.getValue()));
                                break;
                            case LIKE:
                                predicates.add(builder.like(expression, "%" + filter.getValue() + "%"));
                                break;
                            case GT:
                                predicates.add(builder.greaterThan(expression, (Comparable) filter.getValue()));
                                break;
                            case LT:
                                predicates.add(builder.lessThan(expression, (Comparable) filter.getValue()));
                                break;
                            case GTE:
                                predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.getValue()));
                                break;
                            case LTE:
                                predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.getValue()));
                                break;
                            case IN:
                                predicates.add(expression.in(filter.getValue()));
                        }
                    }

                    // 将所有条件用 and 联合起来
                    if (!predicates.isEmpty()) {
                        return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                    }
                }

                return builder.conjunction();
            }
        };
    }
}