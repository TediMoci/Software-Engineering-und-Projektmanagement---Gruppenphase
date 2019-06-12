import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditTrainingscheduleComponent } from './edit-trainingschedule.component';

describe('EditTrainingscheduleComponent', () => {
  let component: EditTrainingscheduleComponent;
  let fixture: ComponentFixture<EditTrainingscheduleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditTrainingscheduleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditTrainingscheduleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
