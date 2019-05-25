export class CreateExercise {
  constructor(
    public name: string,
    public equipment: string,
    public category: string,
    public description:  string,
    public muscleGroup: string,
    public creatorId: number
  ) {}
}
