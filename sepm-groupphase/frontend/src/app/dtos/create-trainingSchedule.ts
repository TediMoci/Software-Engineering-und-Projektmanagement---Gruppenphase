import {TrainingScheduleWorkoutDtoIn} from './trainingScheduleWorkoutDtoIn';

export class CreateTrainingSchedule {
  constructor(
    public name: string,
    public description: string,
    public difficulty: number,
    public intervalLength: number,
    public trainingScheduleWorkouts: TrainingScheduleWorkoutDtoIn[],
    public creatorId: number,
    public isPrivate: boolean
  ) {}
}



