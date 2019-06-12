export class Exercise {
  constructor(
    public id: number,
    public version: number,
    public name: string,
    public description: string,
    public equipment:  string,
    public muscleGroup: string,
    public category: string,
    public creatorId: number,
    public imagePath: string
  ) {}
}
