import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditFitnessProviderProfileComponent } from './edit-fitness-provider-profile.component';

describe('EditFitnessProviderProfileComponent', () => {
  let component: EditFitnessProviderProfileComponent;
  let fixture: ComponentFixture<EditFitnessProviderProfileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditFitnessProviderProfileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditFitnessProviderProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
