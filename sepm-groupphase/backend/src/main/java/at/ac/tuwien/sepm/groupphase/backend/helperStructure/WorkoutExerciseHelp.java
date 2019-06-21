package at.ac.tuwien.sepm.groupphase.backend.helperStructure;

import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;

public class WorkoutExerciseHelp {

    private WorkoutExercise workoutExercise;
    private Integer day;

    public WorkoutExercise getWorkoutExercise() {
        return workoutExercise;
    }

    public void setWorkoutExercise(WorkoutExercise workoutExercise) {
        this.workoutExercise = workoutExercise;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "WorkoutExerciseHelp{" +
            "workoutExercise=" + workoutExercise +
            ", day=" + day +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkoutExerciseHelp that = (WorkoutExerciseHelp) o;

        if (workoutExercise != null ? !workoutExercise.equals(that.workoutExercise) : that.workoutExercise != null)
            return false;
        return day != null ? day.equals(that.day) : that.day == null;
    }

    @Override
    public int hashCode() {
        int result = workoutExercise != null ? workoutExercise.hashCode() : 0;
        result = 31 * result + (day != null ? day.hashCode() : 0);
        return result;
    }

    public static final class WorkoutExerciseHelpBuilder {
        private WorkoutExercise workoutExercise;
        private Integer day;

        public WorkoutExerciseHelpBuilder() {
        }

        public WorkoutExerciseHelpBuilder workoutExercise(WorkoutExercise waEx) {
            this.workoutExercise = waEx;
            return this;
        }

        public WorkoutExerciseHelpBuilder day(Integer day) {
            this.day = day;
            return this;
        }

        public WorkoutExerciseHelp build() {
            WorkoutExerciseHelp waExH = new WorkoutExerciseHelp();
            waExH.setWorkoutExercise(workoutExercise);
            waExH.setDay(day);
            return waExH;
        }
    }
}
