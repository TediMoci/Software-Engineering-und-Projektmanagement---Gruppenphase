import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditTrainingScheduleComponent } from './edit-training-schedule.component';

describe('EditTrainingScheduleComponent', () => {
  let component: EditTrainingScheduleComponent;
  let fixture: ComponentFixture<EditTrainingScheduleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditTrainingScheduleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditTrainingScheduleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
