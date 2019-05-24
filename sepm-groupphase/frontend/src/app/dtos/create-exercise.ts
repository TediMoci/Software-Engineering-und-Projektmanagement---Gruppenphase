export class CreateExercise {
  constructor(
    public name: string,
    public description: string,
    public category: string,
    public equipment:  string,
    public muscleGroup: string,
    public creatorId: number
  ) {}
}
