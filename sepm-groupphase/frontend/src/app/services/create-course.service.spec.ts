import { TestBed } from '@angular/core/testing';

import { CreateCourseService } from './create-course.service';

describe('CreateCourseService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CreateCourseService = TestBed.get(CreateCourseService);
    expect(service).toBeTruthy();
  });
});
