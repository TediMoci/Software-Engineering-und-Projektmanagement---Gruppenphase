import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateTrainingScheduleRandomlyComponent } from './create-training-schedule-randomly.component';

describe('CreateTrainingScheduleRandomlyComponent', () => {
  let component: CreateTrainingScheduleRandomlyComponent;
  let fixture: ComponentFixture<CreateTrainingScheduleRandomlyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateTrainingScheduleRandomlyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateTrainingScheduleRandomlyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
