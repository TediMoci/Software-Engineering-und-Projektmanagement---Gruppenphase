import { TestBed } from '@angular/core/testing';

import { GetByIDService } from './get-by-id.service';

describe('GetByIDService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: GetByIDService = TestBed.get(GetByIDService);
    expect(service).toBeTruthy();
  });
});
