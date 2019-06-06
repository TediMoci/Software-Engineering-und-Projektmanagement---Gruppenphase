export class TrainingScheduleWorkoutDtoIn {
  constructor(
    public workoutId: number,
    public workoutVersion: number,
    public day: number
  ) {}
}
