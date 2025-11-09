package com.alex.user.mapper;

import com.alex.user.entity.UserEntity;
import com.alex.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity toEntity(User user);

    User toDomain(UserEntity entity);
}