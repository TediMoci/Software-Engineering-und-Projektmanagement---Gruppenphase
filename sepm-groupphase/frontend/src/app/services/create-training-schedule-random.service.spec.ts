import { TestBed } from '@angular/core/testing';

import { CreateTrainingScheduleRandomService } from './create-training-schedule-random.service';

describe('CreateTrainingScheduleRandomService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CreateTrainingScheduleRandomService = TestBed.get(CreateTrainingScheduleRandomService);
    expect(service).toBeTruthy();
  });
});
