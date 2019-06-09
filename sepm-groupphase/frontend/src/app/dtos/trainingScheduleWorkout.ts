export class TrainingScheduleWorkout {
  constructor(
    public id: number,
    public version: number,
    public name: string,
    public description: string,
    public difficulty: number,
    public calorieConsumption: number,
    public day: number,
    public creatorId: number
  ) {}
}
