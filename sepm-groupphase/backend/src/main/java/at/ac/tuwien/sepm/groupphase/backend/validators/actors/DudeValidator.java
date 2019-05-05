package at.ac.tuwien.sepm.groupphase.backend.validators.actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;

@Component
public class DudeValidator {

    private String name_is_null = "Username must be set!";
    private String name_is_blank = "Username must not be blank!";
    private String email_is_null = "Email adress must be set!";
    private String email_is_blank = "Email adress must not be blank!";
    private String sex_is_null = "Sex must be set!";
    private String invalid_sex = "Sex must be male (M), female (F) or other (O)!";
    private String selfAssessment_is_null = "Self assessment must not be null!";
    private String invalid_selfAssessment = "Self assessment must be 1 (Beginner), 2 (Advanced) or 3 (Pro)!";
    private String birthday_is_null = "Birthday must not be null!";
    private String invalid_birthday = "Invalid birthday!";
    private String height_is_null = "Height must not be null!";
    private String invalid_height = "Unnatural height. Check your entry!";
    private String weight_is_null = "Weight must not be null!";
    private String invalid_weight = "Unnatural weight. Check your entry!";

    /**
     *
     * @param dude
     * @throws ValidationException
     */
    public void validateDude(Dude dude) throws ValidationException{

        if (dude.getName() == null){
            throw new ValidationException(name_is_null);
        }
        if (dude.getName().isBlank()){
            throw new ValidationException(name_is_blank);
        }
        if (dude.getEmail() == null){
            throw new ValidationException(email_is_null);
        }
        if (dude.getEmail().isBlank()){
            throw new ValidationException(email_is_blank);
        }
        if (dude.getSex() == null){
            throw new ValidationException(sex_is_null);
        }
        if (!(dude.getSex().equals('F') || dude.getSex().equals('f') || dude.getSex().equals('M') || dude.getSex().equals('m') || dude.getSex().equals('O') || dude.getSex().equals('o'))){
            throw new ValidationException(invalid_sex);
        }
        if (dude.getSelfAssessment() == null){
            throw new ValidationException(selfAssessment_is_null);
        }
        if (!(dude.getSelfAssessment() == 1 || dude.getSelfAssessment() == 2 || dude.getSelfAssessment() == 3)){
            throw new ValidationException(invalid_selfAssessment);
        }
        try {
            if (dude.getBirthday() == null){
                throw new ValidationException(birthday_is_null);
            }
        } catch (DateTimeException e){
            throw new ValidationException(invalid_birthday);
        }
        if (dude.getHeight() == null){
            throw new ValidationException(height_is_null);
        }
        if ((dude.getHeight() < 50) || (dude.getHeight() > 300)){
            throw new ValidationException(invalid_height);
        }
        if (dude.getWeight() == null){
            throw new ValidationException(weight_is_null);
        }
        if ((dude.getWeight() < 0.0) || (dude.getWeight() > 700.0)){
            throw new ValidationException(invalid_weight);
        }
    }

}
