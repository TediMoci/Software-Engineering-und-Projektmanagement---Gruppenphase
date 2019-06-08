import { TestBed } from '@angular/core/testing';

import { FollowerService } from './follower.service';

describe('FollowerService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FollowerService = TestBed.get(FollowerService);
    expect(service).toBeTruthy();
  });
});
