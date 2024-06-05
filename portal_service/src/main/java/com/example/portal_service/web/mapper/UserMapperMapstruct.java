package com.example.portal_service.web.mapper;

import com.example.portal_service.model.user.User;
import com.example.portal_service.web.dto.user.UserDto;
import com.example.portal_service.web.dto.user.UserUpdateDto;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class UserMapperMapstruct {
    @Autowired
    protected Updater updater;

    public User updateUser(User entity, UserUpdateDto dtoForUpdate) {
        return updater.updateUser(entity, dtoForUpdate);
    }

    public UserRepresentation updateUserRepresentation(UserRepresentation userRepresentation,
                                                       UserUpdateDto dtoForUpdate
    ) {
        return updater.updateUserRepresentation(userRepresentation, dtoForUpdate);
    }

    public abstract UserDto toDto(User user);


    @Mapper(
            componentModel = "spring",
            unmappedSourcePolicy = ReportingPolicy.ERROR,
            unmappedTargetPolicy = ReportingPolicy.IGNORE,
            typeConversionPolicy = ReportingPolicy.WARN,
            collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    interface Updater {
        User updateUser(@MappingTarget User entity, UserUpdateDto dtoForUpdate);
        UserRepresentation updateUserRepresentation(
                @MappingTarget UserRepresentation userRepresentation,
                UserUpdateDto dtoForUpdate
                );
    }
}
