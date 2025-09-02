package co.com.crediya.model.common.exceptions;

public record PagedQuery<T>(T filters, PageQuery page) {

}