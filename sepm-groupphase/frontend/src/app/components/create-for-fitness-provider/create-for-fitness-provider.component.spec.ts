import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateForFitnessProviderComponent } from './create-for-fitness-provider.component';

describe('CreateForFitnessProviderComponent', () => {
  let component: CreateForFitnessProviderComponent;
  let fixture: ComponentFixture<CreateForFitnessProviderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateForFitnessProviderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateForFitnessProviderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
