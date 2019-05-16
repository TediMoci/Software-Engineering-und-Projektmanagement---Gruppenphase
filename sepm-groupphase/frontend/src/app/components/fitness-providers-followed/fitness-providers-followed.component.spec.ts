import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FitnessProvidersFollowedComponent } from './fitness-providers-followed.component';

describe('FitnessProvidersFollowedComponent', () => {
  let component: FitnessProvidersFollowedComponent;
  let fixture: ComponentFixture<FitnessProvidersFollowedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FitnessProvidersFollowedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FitnessProvidersFollowedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
