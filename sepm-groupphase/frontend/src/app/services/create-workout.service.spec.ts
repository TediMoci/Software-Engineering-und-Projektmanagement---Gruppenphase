import { TestBed } from '@angular/core/testing';

import { CreateWorkoutService } from './create-workout.service';

describe('CreateWorkoutService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CreateWorkoutService = TestBed.get(CreateWorkoutService);
    expect(service).toBeTruthy();
  });
});
