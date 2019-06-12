import { TestBed } from '@angular/core/testing';

import { TrainingScheduleWorkoutsService } from './training-schedule-workouts.service';

describe('TrainingScheduleWorkoutsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TrainingScheduleWorkoutsService = TestBed.get(TrainingScheduleWorkoutsService);
    expect(service).toBeTruthy();
  });
});
