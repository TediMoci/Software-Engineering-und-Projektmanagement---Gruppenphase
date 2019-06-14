export class CreateTraingsPlanRandom {
  constructor(
    public name: string,
    public description: string,
    public difficulty: number,
    public minTarget: number,
    public maxTarget: number,
    public interval: number,
    public repetitions: number,
    public onlyMyDifficulty: boolean
  ) {}
}
