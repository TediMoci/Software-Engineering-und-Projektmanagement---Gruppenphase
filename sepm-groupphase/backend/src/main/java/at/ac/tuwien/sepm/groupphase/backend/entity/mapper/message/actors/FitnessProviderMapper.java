package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.FitnessProviderDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import at.ac.tuwien.sepm.groupphase.backend.service.actors.IFitnessProviderService;
import org.mapstruct.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component
public class FitnessProviderMapper implements IFitnessProviderMapper {

    @Override
    public FitnessProvider fitnessProviderDtoToFitnessProvider(FitnessProviderDto fitnessProviderDto){

        FitnessProvider.FitnessProviderBuilder builder = new FitnessProvider.FitnessProviderBuilder();
        builder.id(fitnessProviderDto.getId());
        builder.password(fitnessProviderDto.getPassword());
        builder.name(fitnessProviderDto.getName());
        builder.address(fitnessProviderDto.getAddress());
        builder.description(fitnessProviderDto.getDescription());
        builder.email(fitnessProviderDto.getEmail());
        builder.phoneNumber(fitnessProviderDto.getPhoneNumber());
        builder.website(fitnessProviderDto.getWebsite());
        builder.imagePath(fitnessProviderDto.getImagePath());

        return builder.build();
    }
    @Override
    public FitnessProviderDto fitnessProviderToFitnessProviderDto(FitnessProvider fitnessProvider){

        FitnessProviderDto.FitnessProviderDtoBuilder builder = new FitnessProviderDto.FitnessProviderDtoBuilder();
        builder.id(fitnessProvider.getId());
        builder.name(fitnessProvider.getName());
        builder.password(fitnessProvider.getPassword());
        builder.address(fitnessProvider.getAddress());
        builder.description(fitnessProvider.getDescription());
        builder.email(fitnessProvider.getEmail());
        builder.phoneNumber(fitnessProvider.getPhoneNumber());
        builder.website(fitnessProvider.getWebsite());
        builder.imagePath(fitnessProvider.getImagePath());

        return builder.build();
    }
}
