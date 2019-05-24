import {WorkoutEx} from './workoutEx';
import {WorkoutExerciseDtoIn} from './workoutExerciseDtoIn';

export class CreateWorkout {
  constructor(
    public name: string,
    public description: string,
    public difficulty: string,
    public calorieConsumption: number,
    public workoutExerciseDtoIn: WorkoutExerciseDtoIn[],
    public creatorId: number
  ) {}
}
