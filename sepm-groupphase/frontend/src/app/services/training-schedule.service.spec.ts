import { TestBed } from '@angular/core/testing';

import { TrainingScheduleService } from './training-schedule.service';

describe('TrainingScheduleService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TrainingScheduleService = TestBed.get(TrainingScheduleService);
    expect(service).toBeTruthy();
  });
});
