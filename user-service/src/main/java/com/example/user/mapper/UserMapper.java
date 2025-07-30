package com.example.user.mapper;
import com.example.user.model.User;
import com.example.user.requestdto.RegistrationRequest;
import com.example.user.requestdto.UserRequest;
import com.example.user.responsedto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
/**
 * Mapper interface for converting Product DTOs to Entities and vice versa.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    /**
     * Creates a new User from a RegistrationRequest.
     *
     * @param registrationRequest the registration data
     * @return a new User
     */
    User mapToNewUser(RegistrationRequest registrationRequest);
    /**
     * Updates an existing User with data from a UserRequest.
     *
     * @param userRequest the new User data
     * @param user the updated User data
     */
    void mapToUser(UserRequest userRequest, @MappingTarget User user);

    /**
     * Converts a User to a UserResponse.
     *
     * @param user the User data
     * @return the corresponding UserResponse
     */
    UserResponse mapToUserResponse(User user);
}