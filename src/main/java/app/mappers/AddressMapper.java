package app.mappers;

import app.domain.model.PostalCode;
import app.domain.shared.Address;
import app.mappers.dto.AddressDTO;

public class AddressMapper {

    public AddressDTO toDTO(Address model)
    {
        return new AddressDTO(
                model.getStreet(),
                model.getDoorNumber(),
                model.getPostalCode().getNumber(),
                model.getCity(),
                model.getCountry()
        );
    }


    public Address toModel(AddressDTO dto)
    {
        return new Address(
                dto.getStreet(),
                dto.getDoorNumber(),
                new PostalCode(dto.getPostalCode()),
                dto.getCity(),
                dto.getCountry());
    }


}
