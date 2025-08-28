package co.com.crediya.api.rest.loanapplication;

import co.com.crediya.api.rest.user.dto.CreateApplicantRequestDto;
import co.com.crediya.api.rest.user.dto.UserResponseDto;
import co.com.crediya.api.rest.user.mapper.UserRequestMapper;
import co.com.crediya.api.rest.user.mapper.UserResponseMapper;
import co.com.crediya.api.utils.ObjectValidator;
import co.com.crediya.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserUseCase userUseCase;
    private final UserRequestMapper requestMapper;
    private final UserResponseMapper responseMapper;
    private final ObjectValidator validator;

    public Mono<UserResponseDto> createApplicant(CreateApplicantRequestDto dto) {
        return validator.validate(dto)
                .map(requestMapper::toDomain)
                .flatMap(userUseCase::createApplicant)
                .map(responseMapper::toDto);
    }
}