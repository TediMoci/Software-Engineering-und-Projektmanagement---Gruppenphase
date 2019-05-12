package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.FitnessProviderDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = FitnessProvider.class)
public interface IFitnessProviderMapper {

    FitnessProvider fitnessProviderDtoToFitnessProvider(FitnessProviderDto fitnessProviderDto);

    FitnessProviderDto fitnessProviderToFitnessProviderDto(FitnessProvider fitnessProvider);
}
