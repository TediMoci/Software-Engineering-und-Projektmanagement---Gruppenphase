export class TrainingScheduleDto {
  constructor(
    public id: number,
    public version: number,
    public name: string,
    public description: string,
    public difficulty: number,
    public intervalLength: number,
    public creatorId: number
  ) {}
}
