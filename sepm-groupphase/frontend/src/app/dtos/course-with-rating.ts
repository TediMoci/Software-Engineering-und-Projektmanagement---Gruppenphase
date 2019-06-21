export class CourseWithRating {
  constructor(
    public id: number,
    public name: string,
    public description: string,
    public creatorId: number,
    public imagePath: string,
    public rating: number
  ) {}
}
