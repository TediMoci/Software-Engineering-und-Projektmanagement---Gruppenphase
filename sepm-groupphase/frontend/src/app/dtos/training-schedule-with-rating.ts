import {TrainingScheduleWorkoutDtoIn} from './trainingScheduleWorkoutDtoIn';

export class TrainingScheduleWithRating {
  constructor(
    public id: number,
    public version: number,
    public name: string,
    public description: string,
    public difficulty: number,
    public intervalLength: number,
    public trainingScheduleWorkouts: TrainingScheduleWorkoutDtoIn[],
    public creatorId: number,
    public isPrivate: boolean,
    public rating: number
  ) {}
}
