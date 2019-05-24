export class CreateWorkout {
  constructor(
    public name: string,
    public description: string,
    public difficulty: string,
    public calorieConsumption: number,
    public creatorId: number
  ) {}
}
