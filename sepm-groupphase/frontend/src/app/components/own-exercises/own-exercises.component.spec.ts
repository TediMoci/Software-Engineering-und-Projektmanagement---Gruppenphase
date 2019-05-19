import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnExercisesComponent } from './own-exercises.component';

describe('OwnExercisesComponent', () => {
  let component: OwnExercisesComponent;
  let fixture: ComponentFixture<OwnExercisesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OwnExercisesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OwnExercisesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
