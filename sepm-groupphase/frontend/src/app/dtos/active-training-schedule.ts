export class ActiveTrainingSchedule {
  constructor(
    public dudeId: number,
    public trainingScheduleId: number,
    public trainingScheduleVersion: number,
    public startDate: number,
    public intervalRepetitions: number,
  ){}
}
