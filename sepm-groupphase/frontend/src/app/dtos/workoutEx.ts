import {Exercise} from './exercise';
export class WorkoutEx {
  constructor(
    public exercise: Exercise,
    public repetitions: number,
    public sets: number,
    public exDuration: number,
  ) {}
}
