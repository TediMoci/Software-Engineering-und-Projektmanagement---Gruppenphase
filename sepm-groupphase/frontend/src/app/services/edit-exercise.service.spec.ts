import { TestBed } from '@angular/core/testing';

import { EditExerciseService } from './edit-exercise.service';

describe('EditExerciseService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EditExerciseService = TestBed.get(EditExerciseService);
    expect(service).toBeTruthy();
  });
});
