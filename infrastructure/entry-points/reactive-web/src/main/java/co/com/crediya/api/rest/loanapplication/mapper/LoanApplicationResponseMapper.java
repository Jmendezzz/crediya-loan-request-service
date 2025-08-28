package co.com.crediya.api.rest.loanapplication.mapper;

import co.com.crediya.api.rest.user.dto.UserResponseDto;
import co.com.crediya.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanApplicationResponseMapper {
    UserResponseDto toDto(User user);
}
