package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "fitness_provider")
public class FitnessProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_fitness_provider_id")
    @SequenceGenerator(name = "seq_fitness_provider_id", sequenceName = "seq_fitness_provider_id")
    private Long id;

    @Column(nullable = false, length = 40)
    @Size(min = 1, max = 50)
    private String name;

    @Column(nullable = false)
    @Size(min = 8)
    private String password;

    @Column(nullable = false, length = 100)
    @NotBlank @Size(max = 100)
    private String address;

    @Column(nullable = false, length = 1000)
    @Size(max = 1000)
    private String description = "No description given.";

    @Column(nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    private String email;

    @Column(nullable = false, length = 30, name = "phone_number")
    @Size(max = 30)
    private String phoneNumber = "No phone number given.";

    @Column(nullable = false, length = 100)
    @Size(max = 100)
    private String website = "No website given.";


    @ElementCollection
    private List<String> roles;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "fitnessProviders")
    private Set<Dude> dudes;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "fitnessProvider")
    private Set<Course> courses;

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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Set<Dude> getDudes() {
        return dudes;
    }

    public void setDudes(Set<Dude> dudes) {
        this.dudes = dudes;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public static FitnessProviderBuilder builder() {
        return new FitnessProviderBuilder();
    }

    @Override
    public String toString() {
        return "FitnessProvider{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", password='" + password + '\'' +
            ", address='" + address + '\'' +
            ", description='" + description + '\'' +
            ", email='" + email + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", website='" + website + '\'' +
            ", dudes=" + dudes +
            ", courses=" + courses +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FitnessProvider that = (FitnessProvider) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        if (website != null ? !website.equals(that.website) : that.website != null) return false;
        if (dudes != null ? !dudes.equals(that.dudes) : that.dudes != null) return false;
        return courses != null ? courses.equals(that.courses) : that.courses == null;
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
        result = 31 * result + (dudes != null ? dudes.hashCode() : 0);
        result = 31 * result + (courses != null ? courses.hashCode() : 0);
        return result;
    }

    public static final class FitnessProviderBuilder {
        private Long id;
        private String name;
        private String password;
        private String address;
        private String description;
        private String email;
        private String phoneNumber;
        private String website;
        private Set<Dude> dudes;
        private Set<Course> courses;

        public FitnessProviderBuilder() {
        }

        public FitnessProviderBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public FitnessProviderBuilder name(String name) {
            this.name = name;
            return this;
        }

        public FitnessProviderBuilder password(String password){
            this.password =    password;
            return this;
        }

        public FitnessProviderBuilder address(String address) {
            this.address = address;
            return this;
        }

        public FitnessProviderBuilder description(String description) {
            this.description = description;
            return this;
        }

        public FitnessProviderBuilder email(String email) {
            this.email = email;
            return this;
        }

        public FitnessProviderBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public FitnessProviderBuilder website(String website) {
            this.website = website;
            return this;
        }

        public FitnessProviderBuilder dudes(Set<Dude> dudes) {
            this.dudes = dudes;
            return this;
        }

        public FitnessProviderBuilder courses(Set<Course> courses) {
            this.courses = courses;
            return this;
        }

        public FitnessProvider build() {
            FitnessProvider fitnessProvider = new FitnessProvider();
            fitnessProvider.setId(id);
            fitnessProvider.setName(name);
            fitnessProvider.setPassword(password);
            fitnessProvider.setAddress(address);
            fitnessProvider.setDescription(description);
            fitnessProvider.setEmail(email);
            fitnessProvider.setPhoneNumber(phoneNumber);
            fitnessProvider.setWebsite(website);
            fitnessProvider.setDudes(dudes);
            fitnessProvider.setCourses(courses);
            return fitnessProvider;
        }
    }
}
