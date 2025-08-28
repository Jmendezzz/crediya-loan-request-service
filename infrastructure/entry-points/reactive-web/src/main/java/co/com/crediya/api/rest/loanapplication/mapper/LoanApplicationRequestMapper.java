package co.com.crediya.api.rest.user.mapper;

import co.com.crediya.api.rest.user.dto.CreateApplicantRequestDto;
import co.com.crediya.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRequestMapper {

    @Mapping(source = "identityNumber", target = "password")
    User toDomain(CreateApplicantRequestDto createApplicantRequestDto);
}
