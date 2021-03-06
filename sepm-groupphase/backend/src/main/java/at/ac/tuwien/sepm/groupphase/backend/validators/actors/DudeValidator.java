package at.ac.tuwien.sepm.groupphase.backend.validators.actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
import at.ac.tuwien.sepm.groupphase.backend.enumerations.Sex;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.service.actors.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.DateTimeException;
import java.time.LocalDate;

@Component
public class DudeValidator {

    private String name_is_null = "Username must be set!";
    private String name_is_blank = "Username must not be blank!";

    private IUserService userService;

    @Autowired
    public DudeValidator(IUserService userService){
        this.userService = userService;
    }

    /**
     * @param dude
     * @throws ValidationException
     */
    public void validateDude(Dude dude) throws ValidationException {

        String email_is_null = "Email adress must be set!";
        String email_is_blank = "Email adress must not be blank!";
        String sex_is_null = "Sex must be set!";
        String invalid_sex = "Sex must be Male, Female or Other!";
        String selfAssessment_is_null = "Self assessment must not be null!";
        String invalid_selfAssessment = "Self assessment must be 1 (Beginner), 2 (Advanced) or 3 (Pro)!";
        String birthday_is_null = "Birthday must not be null!";
        String invalid_birthday = "Invalid birthday!";
        String born_in_the_future = "You can not be born after the current day.";
        String height_is_null = "Height must not be null!";
        String invalid_height = "Unnatural height. Check your entry!";
        String weight_is_null = "Weight must not be null!";
        String invalid_weight = "Unnatural weight. Check your entry!";
        String password_too_short = "Your password is too short. The minimum length is 8.";
        String password_is_null = "Passwort must be given!";

        if (dude.getName() == null) {
            throw new ValidationException(name_is_null);
        }
        if (dude.getName().isBlank()) {
            throw new ValidationException(name_is_blank);
        }
        if (dude.getPassword()==null || dude.getPassword().isBlank()) {
            throw new ValidationException(password_is_null);
        }
        if (dude.getPassword().length() < 8){
            throw new ValidationException(password_too_short);
        }
        if (dude.getEmail() == null) {
            throw new ValidationException(email_is_null);
        }
        if (dude.getEmail().isBlank()) {
            throw new ValidationException(email_is_blank);
        }
        if (dude.getSex() == null) {
            throw new ValidationException(sex_is_null);
        }
        if (!(dude.getSex() == Sex.Female || dude.getSex() == Sex.Male || dude.getSex() == Sex.Other)) {
            throw new ValidationException(invalid_sex);
        }
        if (dude.getSelfAssessment() == null) {
            throw new ValidationException(selfAssessment_is_null);
        }
        if (!(dude.getSelfAssessment() == 1 || dude.getSelfAssessment() == 2 || dude.getSelfAssessment() == 3)) {
            throw new ValidationException(invalid_selfAssessment);
        }
        try {
            if (dude.getBirthday() == null) {
                throw new ValidationException(birthday_is_null);
            }
        } catch (DateTimeException e) {
            throw new ValidationException(invalid_birthday);
        }
        if (dude.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException(born_in_the_future);
        }
        if (dude.getHeight() == null) {
            throw new ValidationException(height_is_null);
        }
        if ((dude.getHeight() < 50) || (dude.getHeight() > 300)) {
            throw new ValidationException(invalid_height);
        }
        if (dude.getWeight() == null) {
            throw new ValidationException(weight_is_null);
        }
        if ((dude.getWeight() < 1.0) || (dude.getWeight() > 700.0)) {
            throw new ValidationException(invalid_weight);
        }
    }

    /**
     * @param name
     * @throws ValidationException
     */
    public void validateName(String name) throws ValidationException {
        if (name == null) {
            throw new ValidationException(name_is_null);
        }
        if (name.isBlank()) {
            throw new ValidationException(name_is_blank);
        }
    }

    /**
     *
     * @param name
     * @throws ValidationException
     */
    public void validateNameUnique(String name) throws ValidationException {
        int taken = userService.nameTaken(name);
        if (taken == 0 || taken == 1) {
            throw new ValidationException("Name is already taken!");
        }
    }

}
