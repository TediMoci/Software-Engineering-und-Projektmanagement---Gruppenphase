package at.ac.tuwien.sepm.groupphase.backend.endpoint.parameterObjects;


import at.ac.tuwien.sepm.groupphase.backend.enumerations.Category;

public class ExercisePo {

    private String name;
    private String description;
    private String equipment;
    private String muscleGroup;
    private Double rating;
    private Category category;

    public ExercisePo() {
    }

    public ExercisePo(String name, String description, String equipment, String muscleGroup, Double rating, Category category) {
        this.name = name;
        this.description = description;
        this.equipment = equipment;
        this.muscleGroup = muscleGroup;
        this.rating = rating;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ExercisePo{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", equipment='" + equipment + '\'' +
            ", muscleGroup='" + muscleGroup + '\'' +
            ", rating=" + rating +
            ", category=" + category +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExercisePo that = (ExercisePo) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (equipment != null ? !equipment.equals(that.equipment) : that.equipment != null) return false;
        if (muscleGroup != null ? !muscleGroup.equals(that.muscleGroup) : that.muscleGroup != null) return false;
        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;
        return category == that.category;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (equipment != null ? equipment.hashCode() : 0);
        result = 31 * result + (muscleGroup != null ? muscleGroup.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }
}
