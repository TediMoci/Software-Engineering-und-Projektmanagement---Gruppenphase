import { TestBed } from '@angular/core/testing';

import { EditWorkoutService } from './edit-workout.service';

describe('EditWorkoutService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EditWorkoutService = TestBed.get(EditWorkoutService);
    expect(service).toBeTruthy();
  });
});
