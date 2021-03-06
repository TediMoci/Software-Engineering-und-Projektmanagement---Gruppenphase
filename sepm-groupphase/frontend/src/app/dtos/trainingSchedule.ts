import {TrainingScheduleWorkoutDtoIn} from './trainingScheduleWorkoutDtoIn';

export class TrainingSchedule {
  constructor(
    public id: number,
    public version: number,
    public name: string,
    public description: string,
    public difficulty: number,
    public intervalLength: number,
    public trainingScheduleWorkouts: TrainingScheduleWorkoutDtoIn[],
    public creatorId: number,
    public isPrivate: boolean
  ) {}
}
