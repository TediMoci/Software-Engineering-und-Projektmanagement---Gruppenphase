export class ExerciseDone {
  constructor (
    public activeTrainingScheduleId: number,
    public dudeId: number,
    public trainingScheduleId: number,
    public trainingScheduleVersion: number,
    public exerciseId: number,
    public exerciseVersion: number,
    public workoutId: number,
    public workoutVersion: number,
    public day: number,
    public done: boolean
  ) {}
}
