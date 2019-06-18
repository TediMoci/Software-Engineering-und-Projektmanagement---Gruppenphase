export class CreateTrainingScheduleRandom {
  constructor(
    public name: string,
    public description: string,
    public difficulty: number,
    public creatorId: number,
    public intervalLength: number,
    public duration: number,
    public minTarget: number,
    public maxTarget: number,
    public lowerDifficulty: boolean
  ) {}
}
