export class WorkoutExerciseDtoOut {
  constructor(
    public id: number,
    public version: number,
    public name: string,
    public description: string,
    public equipment: string,
    public muscleGroup: string,
    public rating: string,
    public category: string,
    public creatorName: string,
    public exDuration: number,
    public repetitions: number,
    public sets: number
  ) {}
}
