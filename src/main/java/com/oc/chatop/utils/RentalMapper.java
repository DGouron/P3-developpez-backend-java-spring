package com.oc.chatop.utils;

import com.oc.chatop.dtos.RentalRequestDTO;
import com.oc.chatop.dtos.RentalResponseDTO;
import com.oc.chatop.models.Rental;
import com.oc.chatop.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RentalMapper {

        RentalMapper INSTANCE = Mappers.getMapper(RentalMapper.class);

        @Mapping(target = "owner", source = "ownerId", qualifiedByName = "mapOwnerIdToUser")
        Rental toRental(RentalRequestDTO rentalRequestDTO);

    @Mapping(source = "owner.id", target = "ownerId")
    RentalResponseDTO toRentalResponseDTO(Rental rental);

    @Named("mapOwnerIdToUser")
    default User mapOwnerIdToUser(Integer ownerId) {
        if (ownerId == null) {
            return null;
        }
        User user = new User();
        user.setId(ownerId);
        return user;
    }
}