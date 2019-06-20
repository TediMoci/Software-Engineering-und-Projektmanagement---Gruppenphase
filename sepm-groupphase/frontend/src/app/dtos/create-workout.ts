import {WorkoutExerciseDtoIn} from './workoutExerciseDtoIn';

export class CreateWorkout {
  constructor(
    public name: string,
    public description: string,
    public difficulty: number,
    public calorieConsumption: number,
    public workoutExercises: WorkoutExerciseDtoIn[],
    public creatorId: number,
    public isPrivate: boolean
  ) {}
}
