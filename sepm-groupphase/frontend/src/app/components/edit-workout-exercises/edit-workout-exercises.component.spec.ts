import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditWorkoutExercisesComponent } from './edit-workout-exercises.component';

describe('EditWorkoutExercisesComponent', () => {
  let component: EditWorkoutExercisesComponent;
  let fixture: ComponentFixture<EditWorkoutExercisesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditWorkoutExercisesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditWorkoutExercisesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
