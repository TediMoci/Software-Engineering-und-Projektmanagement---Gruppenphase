package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.actors;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors.FitnessProviderDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider.FitnessProviderBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-06-02T22:09:01+0200",
    comments = "version: 1.3.0.Beta2, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
public class IFitnessProviderMapperImpl implements IFitnessProviderMapper {

    @Override
    public FitnessProvider fitnessProviderDtoToFitnessProvider(FitnessProviderDto fitnessProviderDto) {
        if ( fitnessProviderDto == null ) {
            return null;
        }

        FitnessProviderBuilder fitnessProvider = FitnessProvider.builder();

        fitnessProvider.id( fitnessProviderDto.getId() );
        fitnessProvider.name( fitnessProviderDto.getName() );
        fitnessProvider.password( fitnessProviderDto.getPassword() );
        fitnessProvider.address( fitnessProviderDto.getAddress() );
        fitnessProvider.description( fitnessProviderDto.getDescription() );
        fitnessProvider.email( fitnessProviderDto.getEmail() );
        fitnessProvider.phoneNumber( fitnessProviderDto.getPhoneNumber() );
        fitnessProvider.website( fitnessProviderDto.getWebsite() );

        return fitnessProvider.build();
    }

    @Override
    public FitnessProviderDto fitnessProviderToFitnessProviderDto(FitnessProvider fitnessProvider) {
        if ( fitnessProvider == null ) {
            return null;
        }

        FitnessProviderDto fitnessProviderDto = new FitnessProviderDto();

        fitnessProviderDto.setId( fitnessProvider.getId() );
        fitnessProviderDto.setName( fitnessProvider.getName() );
        fitnessProviderDto.setPassword( fitnessProvider.getPassword() );
        fitnessProviderDto.setAddress( fitnessProvider.getAddress() );
        fitnessProviderDto.setDescription( fitnessProvider.getDescription() );
        fitnessProviderDto.setEmail( fitnessProvider.getEmail() );
        fitnessProviderDto.setPhoneNumber( fitnessProvider.getPhoneNumber() );
        fitnessProviderDto.setWebsite( fitnessProvider.getWebsite() );

        return fitnessProviderDto;
    }
}
