import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditTrainingScheduleWorkoutsComponent } from './edit-training-schedule-workouts.component';

describe('EditTrainingScheduleWorkoutsComponent', () => {
  let component: EditTrainingScheduleWorkoutsComponent;
  let fixture: ComponentFixture<EditTrainingScheduleWorkoutsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditTrainingScheduleWorkoutsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditTrainingScheduleWorkoutsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
