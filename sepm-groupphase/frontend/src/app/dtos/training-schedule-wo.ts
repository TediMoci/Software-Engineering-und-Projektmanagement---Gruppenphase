export class TrainingScheduleWo {
  constructor (
    public id: number,
    public version: number,
    public name: string,
    public description: string,
    public difficulty: string,
    public calorieConsumption,
    public rating: number,
    public day: number
  ) {}
}
