export class Workout {
  constructor(
    public id: number,
    public name: string,
    public description: string,
    public difficulty: string,
    public calorieConsumption: number,
    public creatorId: number
  ) {}
}
