import {WorkoutExerciseDtoIn} from './workoutExerciseDtoIn';

export class GetWorkout {
  constructor(
    public id: number,
    public version: number,
    public name: string,
    public description: string,
    public difficulty: number,
    public calorieConsumption: number,
    public rating: number,
    public workoutExercises: WorkoutExerciseDtoIn[],
    public creatorId: number
  ) {}
}
