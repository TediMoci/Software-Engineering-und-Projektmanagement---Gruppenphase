export class ExerciseWithRating {
  constructor(
    public id: number,
    public version: number,
    public name: string,
    public description: string,
    public equipment:  string,
    public muscleGroup: string,
    public rating: number,
    public category: string,
    public creatorId: number,
    public imagePath: string,
    public isPrivate: boolean
  ) {}
}
