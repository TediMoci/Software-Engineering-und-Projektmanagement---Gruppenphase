import {Workout} from './workout';
export class TrainingScheduleWork {
  constructor(
    public workout: Workout,
    public day: number,
  ) {}
}
