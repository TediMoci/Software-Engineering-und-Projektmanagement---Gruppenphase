package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.FitnessProviderDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = FitnessProvider.class)
public interface IFitnessProviderMapper {

    /**
     * @param fitnessProviderDto to be mapped to an entity
     * @return FitnessProvider-entity mapped from given FitnessProvider-DTO
     */
    FitnessProvider fitnessProviderDtoToFitnessProvider(FitnessProviderDto fitnessProviderDto);

    /**
     * @param fitnessProvider to be mapped to a DTO
     * @return FitnessProvider-DTO mapped from given FitnessProvider-entity
     */
    FitnessProviderDto fitnessProviderToFitnessProviderDto(FitnessProvider fitnessProvider);
}
