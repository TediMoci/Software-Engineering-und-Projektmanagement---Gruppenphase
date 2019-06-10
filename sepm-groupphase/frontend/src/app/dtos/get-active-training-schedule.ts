export class GetActiveTrainingSchedule {
  constructor(
    public id: number,
    public dudeId: number,
    public trainingScheduleId: number,
    public trainingScheduleVersion: number,
    public startDate: any,
    public intervalRepetitions: number,
    public adaptive: boolean
  ){}
}
