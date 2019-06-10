import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FitnessProviderComponent } from './fitness-provider.component';

describe('FitnessProviderComponent', () => {
  let component: FitnessProviderComponent;
  let fixture: ComponentFixture<FitnessProviderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FitnessProviderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FitnessProviderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
