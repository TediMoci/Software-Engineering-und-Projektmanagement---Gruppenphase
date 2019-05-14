package at.ac.tuwien.sepm.groupphase.backend.validators.actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.FitnessProvider;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.service.actors.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FitnessProviderValidator {
    private String name_not_unique = "Name is already taken!";
    private String name_is_null = "Username must be set!";
    private String name_is_blank = "Username must not be blank!";
    private String name_lenght_invalid = "Username must be min 1 characters and max 50 characters";
    private String password_too_short = "Your password is too short. The minimum length is 8.";
    private String password_is_null = "Password must be given!";
    private String address_is_null = "Address must be set!";
    private String address_is_blank = "Address must not be blank!";
    private String email_is_null = "Email address must be set!";
    private String email_is_blank = "Email address must not be blank!";
    private IUserService userService;

    @Autowired
    public FitnessProviderValidator(IUserService userService){
        this.userService = userService;
    }

    public void validateFitnessProvider(FitnessProvider fitnessProvider)throws ValidationException {
        validateNameUnique(fitnessProvider.getName());
        //Name Validation
        validateName(fitnessProvider.getName());
        //Password Validation
        if (fitnessProvider.getPassword()==null || fitnessProvider.getPassword().isBlank()) {throw new ValidationException(password_is_null);}
        if (fitnessProvider.getPassword().length()<8) {throw new ValidationException(password_too_short);}

        //Address Validation
        if(fitnessProvider.getAddress() == null){throw new ValidationException( address_is_null);}
        if(fitnessProvider.getAddress().isBlank()){throw new ValidationException( address_is_blank);}
        //Email Validation
        if(fitnessProvider.getEmail() == null){throw new ValidationException( email_is_null);}
        if(fitnessProvider.getEmail().isBlank()){throw new ValidationException( email_is_blank);}
    }

    public void validateNameAndPassword (String name, String password) throws ValidationException{
        //Name Validation
        validateName(name);
        //Password Validation
        if(password == null){throw new  ValidationException(password_is_null);}
        if(password.length() < 8){throw new ValidationException(password_too_short);}
    }

    public void validateName(String name) throws ValidationException {
        if(name == null){throw new ValidationException( name_is_null);}
        if(name.isBlank()){throw new ValidationException( name_is_blank);}
        if(name.length()< 1 || name.length() > 50){ throw new ValidationException(name_lenght_invalid);}
    }

    /**
     *
     * @param name
     * @throws ValidationException
     */
    public void validateNameUnique(String name) throws ValidationException {
        int taken = userService.nameTaken(name);
        if (taken == 0 || taken == 1) {
            throw new ValidationException(name_not_unique);
        }
    }

    public FitnessProvider validateUpdate(FitnessProvider oldFP, FitnessProvider newFP) throws ValidationException {

        if (newFP.getName() != null && !(newFP.getName().isBlank()) && !(newFP.getName().equals(oldFP.getName()))) {
            validateNameUnique(newFP.getName());
            oldFP.setName(newFP.getName());
        }

        if (newFP.getPassword()!=null && !(newFP.getPassword().isBlank()) && newFP.getPassword().length()>=8){
            oldFP.setPassword(newFP.getPassword());
        }

        if (newFP.getDescription()!=null && !(newFP.getDescription().isBlank())){
            oldFP.setDescription(newFP.getDescription());
        }

        if (newFP.getAddress()!=null && !(newFP.getAddress().isBlank())){
            oldFP.setAddress(newFP.getAddress());
        }

        if (newFP.getEmail() != null && !(newFP.getEmail().isBlank())) {
            oldFP.setEmail(newFP.getEmail());
        }

        if (newFP.getPhoneNumber() != null && !(newFP.getPhoneNumber().isBlank()) && newFP.getPhoneNumber().length()<=30) {
            oldFP.setPhoneNumber(newFP.getPhoneNumber());
        }

        if (newFP.getWebsite() != null && !(newFP.getWebsite().isBlank()) && newFP.getWebsite().length()<=100) {
            oldFP.setWebsite(newFP.getWebsite());
        }
        return oldFP;
    }

}


