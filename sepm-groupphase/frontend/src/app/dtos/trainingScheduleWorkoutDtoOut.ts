export class TrainingScheduleWorkoutDtoOut {
  constructor(
    public id: number,
    public version: number,
    public name: string,
    public description: string,
    public difficulty: number,
    public calorieConsumption: number,
    public creatorName: string,
    public day: number
  ) {}
}
