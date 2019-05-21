export class Exercise {
  constructor(
    public name: string,
    public description: string,
    public equipment:  string,
    public muscleGroup: string,
    public category: string,
    public difficulty_level:  string,
    public creatorId: number
  ) {}
}
