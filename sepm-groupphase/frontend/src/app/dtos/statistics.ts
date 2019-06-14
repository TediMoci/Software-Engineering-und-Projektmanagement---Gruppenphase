import {TrainingScheduleDto} from './trainingSchedule-dto';

export class Statistics {
  constructor(
    public id: number,
    public dudeId: number,
    public trainingScheduleId: number,
    public trainingScheduleVersion: number,
    public totalHours: number,
    public totalDays: number,
    public totalCalorieConsumption: number,
    public totalIntervalRepetitions: number,
    public strengthPercent: number,
    public endurancePercent: number,
    public otherPercent: number,
    public trainingScheduleDto: TrainingScheduleDto
  ) {}
}
