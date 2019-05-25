export class WorkoutExerciseDtoIn {
  constructor(
    public exerciseId: number,
    public exerciseVersion: number,
    public exDuration: number,
    public repetitions: number,
    public sets: number
  ) {}
}
