import { TestBed } from '@angular/core/testing';

import { OwnExercisesService } from './own-exercises.service';

describe('OwnExercisesService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: OwnExercisesService = TestBed.get(OwnExercisesService);
    expect(service).toBeTruthy();
  });
});
