import { TestBed } from '@angular/core/testing';

import { WorkoutExercisesService } from './workout-exercises.service';

describe('WorkoutExercisesService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: WorkoutExercisesService = TestBed.get(WorkoutExercisesService);
    expect(service).toBeTruthy();
  });
});
