import { TestBed } from '@angular/core/testing';

import { CreateTrainingScheduleService } from './create-training-schedule.service';

describe('CreateTrainingScheduleService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CreateTrainingScheduleService = TestBed.get(CreateTrainingScheduleService);
    expect(service).toBeTruthy();
  });
});
