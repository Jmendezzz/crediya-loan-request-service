package co.com.crediya.webclient.user.mappers;

import co.com.crediya.model.user.Role;
import co.com.crediya.model.user.User;
import co.com.crediya.webclient.user.dtos.RoleResponseDto;
import co.com.crediya.webclient.user.dtos.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {

    User toDomain(UserResponseDto dto);

    Role toDomain(RoleResponseDto dto);
}
