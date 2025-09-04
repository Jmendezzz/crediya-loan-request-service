package co.com.crediya.model.common.queries;

public record PagedQuery<T>(T filters, PageQuery page) {

}