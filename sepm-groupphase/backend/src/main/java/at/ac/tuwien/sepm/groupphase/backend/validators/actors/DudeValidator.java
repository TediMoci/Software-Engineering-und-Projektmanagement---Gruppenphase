package at.ac.tuwien.sepm.groupphase.backend.validators.actors;

import at.ac.tuwien.sepm.groupphase.backend.entity.Dude;
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
    private String email_is_null = "Email adress must be set!";
    private String email_is_blank = "Email adress must not be blank!";
    private String sex_is_null = "Sex must be set!";
    private String invalid_sex = "Sex must be Male (1), Female (2) or Other (3)!";
    private String selfAssessment_is_null = "Self assessment must not be null!";
    private String invalid_selfAssessment = "Self assessment must be 1 (Beginner), 2 (Advanced) or 3 (Pro)!";
    private String birthday_is_null = "Birthday must not be null!";
    private String invalid_birthday = "Invalid birthday!";
    private String born_in_the_future = "You can not be born after the current day.";
    private String height_is_null = "Height must not be null!";
    private String invalid_height = "Unnatural height. Check your entry!";
    private String weight_is_null = "Weight must not be null!";
    private String invalid_weight = "Unnatural weight. Check your entry!";
    private String password_too_short = "Your password is too short. The minimum length is 8.";
    private String password_is_null = "Passwort must be given!";

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

        if (dude.getName() == null) {
            throw new ValidationException(name_is_null);
        }
        if (dude.getName().isBlank()) {
            throw new ValidationException(name_is_blank);
        }

        validateNameUnique(dude.getName());

        if (dude.getEmail() == null) {
            throw new ValidationException(email_is_null);
        }
        if (dude.getEmail().isBlank()) {
            throw new ValidationException(email_is_blank);
        }
        if (dude.getSex() == null) {
            throw new ValidationException(sex_is_null);
        }
        if (!(dude.getSex().equals(1) || dude.getSex().equals(2) || dude.getSex().equals(3))) {
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
     * @param name
     * @param password
     * @throws ValidationException
     */
    public void validateNameAndPassword(String name, String password) throws ValidationException {
        if (name == null) {
            throw new ValidationException(name_is_null);
        }
        if (name.isBlank()) {
            throw new ValidationException(name_is_blank);
        }
        if (password == null) {
            throw new ValidationException(password_is_null);
        }
        if (password.isBlank() || password.length() < 8) {
            throw new ValidationException(password_too_short);
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

    public Dude validateUpdate(Dude oldDude, Dude dude) throws ValidationException {

        if (dude.getName() != null && !(dude.getName().isBlank()) && !(dude.getName().equals(oldDude.getName()))) {
            validateNameUnique(dude.getName());
            oldDude.setName(dude.getName());
        }

        if (dude.getPassword()!=null && !(dude.getPassword().isBlank()) && dude.getPassword().length()>=8){
            oldDude.setPassword(dude.getPassword());
        }

        if (dude.getDescription()!=null && !(dude.getDescription().isBlank())){
            oldDude.setDescription(dude.getDescription());
        }

        if (dude.getEmail() != null && !(dude.getEmail().isBlank())) {
            oldDude.setEmail(dude.getEmail());
        }

        if (dude.getSex() != null && (dude.getSex().equals('F') || dude.getSex().equals('f') || dude.getSex().equals('M') || dude.getSex().equals('m') || dude.getSex().equals('O') || dude.getSex().equals('o'))) {
            oldDude.setSex(dude.getSex());
        }

        if (dude.getStatus()!=null && (dude.getStatus() == 1 || dude.getStatus() == 2 || dude.getStatus() == 3)) {
            oldDude.setStatus(dude.getStatus());
        }

        if (dude.getSelfAssessment() != null && (dude.getSelfAssessment() == 1 || dude.getSelfAssessment() == 2 || dude.getSelfAssessment() == 3)) {
            oldDude.setSelfAssessment(dude.getSelfAssessment());
        }

        if (dude.getBirthday() != null && dude.getBirthday().isBefore(LocalDate.now())){
            oldDude.setBirthday(dude.getBirthday());
        }

        if (dude.getHeight() != null && (dude.getHeight() > 50 || dude.getHeight() < 300)) {
            oldDude.setHeight(dude.getHeight());
        }

        if (dude.getWeight() != null && ((dude.getWeight() < 1.0) || (dude.getWeight() > 700.0))) {
            oldDude.setWeight(dude.getWeight());
        }

        return oldDude;
    }

}
