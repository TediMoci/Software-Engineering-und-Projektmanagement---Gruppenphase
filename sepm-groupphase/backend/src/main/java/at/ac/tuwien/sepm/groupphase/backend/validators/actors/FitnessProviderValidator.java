package at.ac.tuwien.sepm.groupphase.backend.validators.actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class FitnessProviderValidator {
    private String name_is_null = "Username must be set!";
    private String name_is_blank = "Username must not be blank!";
    private String address_is_null = "Address must be set!";
    private String address_is_blank = "Address must not be blank!";
    private String email_is_null = "Email address must be set!";
    private String email_is_blank = "Email address must not be blank!";

    public void validateFitnessProvider(FitnessProvider fitnessProvider)throws ValidationException {
        //Name Validation
        if(fitnessProvider.getName() == null){throw new ValidationException( name_is_null);}
        if(fitnessProvider.getName().isBlank()){throw new ValidationException( name_is_blank);}
        //Address Validation
        if(fitnessProvider.getAddress() == null){throw new ValidationException( address_is_null);}
        if(fitnessProvider.getAddress().isBlank()){throw new ValidationException( address_is_blank);}
        //Email Validation
        if(fitnessProvider.getEmail() == null){throw new ValidationException( email_is_null);}
        if(fitnessProvider.getEmail().isBlank()){throw new ValidationException( email_is_blank);}

    }

}


