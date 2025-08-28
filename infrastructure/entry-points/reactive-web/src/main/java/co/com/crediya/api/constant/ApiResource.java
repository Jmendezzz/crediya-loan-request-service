package co.com.crediya.api.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiResource {
    LOAN_APPLICATIONS("/loan-applications");

    final String resource;
}
