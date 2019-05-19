import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnWorkoutsComponent } from './own-workouts.component';

describe('OwnWorkoutsComponent', () => {
  let component: OwnWorkoutsComponent;
  let fixture: ComponentFixture<OwnWorkoutsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OwnWorkoutsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OwnWorkoutsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
