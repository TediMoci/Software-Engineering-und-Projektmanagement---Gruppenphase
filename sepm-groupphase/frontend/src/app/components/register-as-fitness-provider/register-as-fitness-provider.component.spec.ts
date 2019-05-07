import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterAsFitnessProviderComponent } from './register-as-fitness-provider.component';

describe('RegisterAsFitnessProviderComponent', () => {
  let component: RegisterAsFitnessProviderComponent;
  let fixture: ComponentFixture<RegisterAsFitnessProviderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegisterAsFitnessProviderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterAsFitnessProviderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
