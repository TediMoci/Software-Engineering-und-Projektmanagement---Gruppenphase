package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.actors;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel(value = "FitnessProviderDto", description = "A dto for fitness provider entries via rest")
public class FitnessProviderDto {

    @ApiModelProperty(readOnly = true, name = "The automatically generated database id")
    private Long id;

    @ApiModelProperty(required = true, name = "Name of Fitness Provider")
    @NotBlank(message = "Name must not be blank")
    @Size(max = 100, message = "Max length for name is 100")
    private String name;

    @ApiModelProperty(required = true, name = "Password of Fitness provider")
    @NotBlank(message = "Password must not be blank")
    private String password;

    @ApiModelProperty(required = true, name = "Address of Fitness Provider")
    @NotBlank(message = "Address must not be blank")
    @Size(max = 200, message = "Max length for address is 200")
    private String address;

    @ApiModelProperty(name = "Self description of Fitness Provider")
    @Size(max = 2000, message = "Max length for description is 2000")
    private String description = "No description given.";

    @ApiModelProperty(required = true, name = "Email address of  Fitness Provider")
    @NotBlank(message = "Email must not be blank")
    @Size(max = 100, message = "Max length for email is 100")
    private String email;

    @ApiModelProperty(name = "Phone number of the Fitness Provider")
    @Size(max = 50, message = "Max length for phoneNumber is 50")
    private String phoneNumber = "No phone number given.";

    @ApiModelProperty(name = "Website of the Fitness Provider")
    @Size(max = 200, message = "Max length for website is 200")
    private String website = "No website given.";

    @ApiModelProperty(name = "Path of profile-picture of Fitness Provider")
    private String imagePath = "/assets/img/kugelfisch2.jpg";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "FitnessProviderDto{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", password='" + password + '\'' +
            ", address='" + address + '\'' +
            ", description='" + description + '\'' +
            ", email='" + email + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", website='" + website + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FitnessProviderDto that = (FitnessProviderDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        return website != null ? website.equals(that.website) : that.website == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (website != null ? website.hashCode() : 0);
        return result;
    }

    public static final class FitnessProviderDtoBuilder {
        private Long id;
        private String name;
        private String password;
        private String address;
        private String description;
        private String email;
        private String phoneNumber;
        private String website;
        private String imagePath;

        public FitnessProviderDtoBuilder(){

        }

        public FitnessProviderDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public FitnessProviderDtoBuilder name(String name) {
            this.name = name;
            return this;
        }
        public FitnessProviderDtoBuilder password(String password){
            this.password = password;
            return this;
        }
        public FitnessProviderDtoBuilder address(String address) {
            this.address = address;
            return this;
        }
        public FitnessProviderDtoBuilder description(String description) {
            this.description = description;
            return this;
        }
        public FitnessProviderDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public FitnessProviderDtoBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public FitnessProviderDtoBuilder website(String website) {
            this.website = website;
            return this;
        }

        public FitnessProviderDtoBuilder imagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public FitnessProviderDto build() {
            FitnessProviderDto fitnessProviderDto = new FitnessProviderDto();
            fitnessProviderDto.setId(id);
            fitnessProviderDto.setName(name);
            fitnessProviderDto.setPassword(password);
            fitnessProviderDto.setAddress(address);
            fitnessProviderDto.setDescription(description);
            fitnessProviderDto.setEmail(email);
            fitnessProviderDto.setPhoneNumber(phoneNumber);
            fitnessProviderDto.setWebsite(website);
            fitnessProviderDto.setImagePath(imagePath);

            return fitnessProviderDto;
        }
    }
}
