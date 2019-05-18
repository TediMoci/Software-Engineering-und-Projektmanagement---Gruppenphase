import { TestBed } from '@angular/core/testing';

import { CreateExerciseService } from './create-exercise.service';

describe('CreateExerciseService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CreateExerciseService = TestBed.get(CreateExerciseService);
    expect(service).toBeTruthy();
  });
});
