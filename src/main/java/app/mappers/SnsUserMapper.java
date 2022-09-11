package app.mappers;

import app.domain.model.CitizenCard;
import app.domain.model.PhoneNumber;
import app.domain.model.SnsNumber;
import app.domain.model.SnsUser;
import app.mappers.dto.SnsUserDTO;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.ArrayList;
import java.util.List;

public class SnsUserMapper {

    private AddressMapper addressMapper;

    public SnsUserMapper()
    {
        this.addressMapper = new AddressMapper();
    }

    /**
     * Maps from model entity to DTO
     * @param model the model to map/convert
     * @return the SnsUserDTO
     */
    public SnsUserDTO toDTO(SnsUser model)
    {
        return new SnsUserDTO(
                model.getName(),
                model.getEmail().getEmail(),
                model.getGender(),
                model.getBirthdate(),
                model.getSnsNumber().getNumber(),
                this.addressMapper.toDTO(model.getAddress()),
                model.getPhoneNumber().getNumber(),
                model.getCitizenCard().getNumber());
    }

    /**
     * Generates a list container with SnsUserDTO objects
     *
     * @param snsUserList  list of SnsUser instances
     * @return  list of SnsUserDTO objects
     */
    public List<SnsUserDTO> toDTO(List<SnsUser> snsUserList)
    {
        List<SnsUserDTO> snsUsersDTO = new ArrayList<>();

        for (SnsUser user : snsUserList)
        {
            snsUsersDTO.add(this.toDTO(user));
        }

        return snsUsersDTO;
    }

    /**
     * Maps from DTO to model entity
     * @param dto the DTO to map/convert
     * @return the SNS User
     */
    public SnsUser toModel(SnsUserDTO dto)
    {
        return new SnsUser(
                dto.getEmail(),
                dto.getName(),
                new Email(dto.getEmail()),
                dto.getGender(),
                dto.getBirthdate(),
                new SnsNumber(dto.getSnsNumber()),
                this.addressMapper.toModel(dto.getAddress()),
                new PhoneNumber(dto.getPhoneNumber()),
                new CitizenCard(dto.getCitizenCardNumber()));
    }



}
