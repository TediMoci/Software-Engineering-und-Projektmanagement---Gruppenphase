package at.ac.tuwien.sepm.groupphase.backend.helperStructure;

import at.ac.tuwien.sepm.groupphase.backend.entity.relationships.WorkoutExercise;

public class WorkoutExerciseDone {

    private WorkoutExercise workoutExercise;
    private boolean done;

    public WorkoutExercise getWorkoutExercise() {
        return workoutExercise;
    }

    public void setWorkoutExercise(WorkoutExercise workoutExercise) {
        this.workoutExercise = workoutExercise;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkoutExerciseDone that = (WorkoutExerciseDone) o;

        if (done != that.done) return false;
        return workoutExercise != null ? workoutExercise.equals(that.workoutExercise) : that.workoutExercise == null;
    }

    @Override
    public int hashCode() {
        int result = workoutExercise != null ? workoutExercise.hashCode() : 0;
        result = 31 * result + (done ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WorkoutExerciseDone{" +
            "waEx=" + workoutExercise +
            ", done=" + done +
            '}';
    }

    public static final class WorkoutExerciseDoneBuilder {
        private WorkoutExercise workoutExercise;
        private Boolean done;

        public WorkoutExerciseDoneBuilder() {
        }

        public WorkoutExerciseDoneBuilder workoutExercise(WorkoutExercise waEx) {
            this.workoutExercise = waEx;
            return this;
        }

        public WorkoutExerciseDoneBuilder done(Boolean done) {
            this.done = done;
            return this;
        }

        public WorkoutExerciseDone build() {
            WorkoutExerciseDone waExH = new WorkoutExerciseDone();
            waExH.setWorkoutExercise(workoutExercise);
            waExH.setDone(done);
            return waExH;
        }
    }

}
