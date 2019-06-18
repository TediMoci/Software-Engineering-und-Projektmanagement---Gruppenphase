import {WorkoutExerciseDtoIn} from './workoutExerciseDtoIn';

export class EditWorkout {
  constructor(
    public id: number,
    public version: number,
    public name: string,
    public description: string,
    public difficulty: number,
    public calorieConsumption: number,
    public workoutExercises: WorkoutExerciseDtoIn[],
    public creatorId: number,
    public isPrivate: boolean
  ) {}
}
