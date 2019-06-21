export class WorkoutWithRating {
  constructor(
    public id: number,
    public version: number,
    public name: string,
    public description: string,
    public difficulty: number,
    public calorieConsumption: number,
    public creatorId: number,
    public isPrivate: boolean,
    public rating: number
  ) {}
}
