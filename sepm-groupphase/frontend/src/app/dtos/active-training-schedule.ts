export class ActiveTrainingSchedule {
  constructor(
    public dudeId: number,
    public trainingScheduleId: number,
    public trainingScheduleVersion: number,
    public intervalRepetitions: number,
    public adaptive: boolean
  ){}
}
