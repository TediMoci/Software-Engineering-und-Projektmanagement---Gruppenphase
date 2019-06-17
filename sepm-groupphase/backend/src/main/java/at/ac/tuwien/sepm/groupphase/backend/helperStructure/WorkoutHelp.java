package at.ac.tuwien.sepm.groupphase.backend.helperStructure;

import at.ac.tuwien.sepm.groupphase.backend.entity.Workout;

import java.util.Objects;

public class WorkoutHelp {

    private Workout workout;
    private Integer day;
    private double caloriesConsumption;

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public double getCaloriesConsumption() {
        return caloriesConsumption;
    }

    public void setCaloriesConsumption(double caloriesConsumption) {
        this.caloriesConsumption = caloriesConsumption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkoutHelp that = (WorkoutHelp) o;
        return Double.compare(that.caloriesConsumption, caloriesConsumption) == 0 &&
            Objects.equals(workout, that.workout) &&
            Objects.equals(day, that.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workout, day, caloriesConsumption);
    }

    @Override
    public String toString() {
        return "WorkoutHelp{" +
            "workout=" + workout +
            ", day=" + day +
            ", caloriesConsumption=" + caloriesConsumption +
            '}';
    }

    public static final class WorkoutHelpBuilder{

        private Workout workout;
        private Integer day;
        private double caloriesConsumption;

        public WorkoutHelpBuilder(){

        }

        public WorkoutHelpBuilder workout(Workout wa) {
            this.workout = wa;
            return this;
        }

        public WorkoutHelpBuilder day(Integer day) {
            this.day = day;
            return this;
        }

        public WorkoutHelpBuilder caloriesConsumption (double caloriesConsumption) {
            this.caloriesConsumption = caloriesConsumption;
            return this;
        }

        public WorkoutHelp build() {
            WorkoutHelp waH = new WorkoutHelp();
            waH.setWorkout(workout);
            waH.setDay(day);
            waH.setCaloriesConsumption(caloriesConsumption);
            return waH;
        }
    }
}
