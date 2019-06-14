export class CreateTraingsPlanRandom {
  constructor(
    public minTarget: number,
    public maxTarget: number,
    public interval: number,
    public repetitions: number,
    public onlyMyDifficulty: boolean
  ) {}
}
