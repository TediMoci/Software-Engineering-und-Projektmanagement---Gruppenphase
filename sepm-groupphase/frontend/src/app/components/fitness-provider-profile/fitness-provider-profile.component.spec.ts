import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FitnessProviderProfileComponent } from './fitness-provider-profile.component';

describe('FitnessProviderProfileComponent', () => {
  let component: FitnessProviderProfileComponent;
  let fixture: ComponentFixture<FitnessProviderProfileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FitnessProviderProfileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FitnessProviderProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
