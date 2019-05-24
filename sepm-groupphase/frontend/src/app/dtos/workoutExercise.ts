export class WorkoutExercise {
  constructor(
    public id: number,
    public version: number,
    public name: string,
    public description: string,
    public equipment:  string,
    public muscleGroup: string,
    public category: string,
    public difficulty_level:  string, // difficulty
    public repetitions: number,
    public sets: number,
    public exDuration: number,
    public creatorId: number // name of creator
  ) {}
}
